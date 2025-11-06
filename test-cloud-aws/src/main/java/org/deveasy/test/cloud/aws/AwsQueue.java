/*
 * AWS SQS implementation of Queue capability (minimal v0.3).
 */
package org.deveasy.test.cloud.aws;

import org.deveasy.test.core.cloud.CloudMode;
import org.deveasy.test.core.cloud.TestCloudConfig;
import org.deveasy.test.core.cloud.capability.Queue;
import org.deveasy.test.cloud.aws.internal.AwsClients;
import org.deveasy.test.cloud.aws.internal.LocalStackHolder;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public final class AwsQueue implements Queue {

    private final TestCloudConfig cfg;
    private final SqsClient sqs;

    public AwsQueue(TestCloudConfig cfg) {
        this.cfg = cfg;
        if (cfg.mode() == CloudMode.EMULATOR) {
            // Ensure emulator up-front to surface good errors early
            LocalStackHolder.ensureStartedS3(); // same holder starts SQS as well
        }
        this.sqs = AwsClients.sqs(cfg);
    }

    @Override
    public void ensureQueue(String name) {
        try {
            getQueueUrl(name);
        } catch (QueueDoesNotExistException e) {
            sqs.createQueue(CreateQueueRequest.builder().queueName(name).build());
        }
    }

    @Override
    public void deleteQueue(String name) {
        try {
            String url = getQueueUrl(name);
            sqs.deleteQueue(DeleteQueueRequest.builder().queueUrl(url).build());
        } catch (QueueDoesNotExistException ignored) {
        }
    }

    @Override
    public void send(String queue, String body) {
        String url = getOrCreateQueueUrl(queue);
        sqs.sendMessage(SendMessageRequest.builder().queueUrl(url).messageBody(body).build());
    }

    @Override
    public Optional<String> receive(String queue) {
        return receive(queue, Duration.ofSeconds(0));
    }

    @Override
    public Optional<String> receive(String queue, Duration timeout) {
        String url = getOrCreateQueueUrl(queue);
        // Map Duration to SQS long polling (max 20s). We'll loop until timeout expires.
        long deadlineMs = (timeout == null || timeout.isNegative()) ? 0 : timeout.toMillis();
        long start = System.currentTimeMillis();
        while (true) {
            ReceiveMessageRequest req = ReceiveMessageRequest.builder()
                .queueUrl(url)
                .maxNumberOfMessages(1)
                .waitTimeSeconds((int) Math.min(20, Math.max(0, (deadlineMs == 0 ? 0 : (deadlineMs - (System.currentTimeMillis() - start)) / 1000))))
                .visibilityTimeout(10)
                .build();
            ReceiveMessageResponse resp = sqs.receiveMessage(req);
            if (resp.hasMessages() && !resp.messages().isEmpty()) {
                Message m = resp.messages().get(0);
                // delete-on-receive semantics
                sqs.deleteMessage(DeleteMessageRequest.builder().queueUrl(url).receiptHandle(m.receiptHandle()).build());
                return Optional.ofNullable(m.body());
            }
            if (deadlineMs == 0) {
                return Optional.empty();
            }
            if ((System.currentTimeMillis() - start) >= deadlineMs) {
                return Optional.empty();
            }
        }
    }

    private String getOrCreateQueueUrl(String name) {
        try {
            return getQueueUrl(name);
        } catch (QueueDoesNotExistException e) {
            sqs.createQueue(CreateQueueRequest.builder().queueName(name).build());
            return getQueueUrl(name);
        }
    }

    private String getQueueUrl(String name) {
        return sqs.getQueueUrl(GetQueueUrlRequest.builder().queueName(name).build()).queueUrl();
    }
}
