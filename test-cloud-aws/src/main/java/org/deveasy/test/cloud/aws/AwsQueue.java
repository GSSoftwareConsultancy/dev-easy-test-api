/*
 * AWS SQS implementation of Queue capability (minimal v0.3).
 */
package org.deveasy.test.cloud.aws;

import org.deveasy.test.core.cloud.TestCloudConfig;
import org.deveasy.test.core.cloud.capability.Queue;
import org.deveasy.test.cloud.aws.internal.AwsClients;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.DeleteQueueRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;
import software.amazon.awssdk.services.sqs.model.QueueDoesNotExistException;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.SqsException;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Minimal SQS-backed Queue implementation with delete-on-receive semantics.
 */
public final class AwsQueue implements Queue {

    private final TestCloudConfig cfg;
    private final SqsClient sqs;

    public AwsQueue(TestCloudConfig cfg) {
        this.cfg = cfg;
        this.sqs = AwsClients.sqs(cfg);
    }

    @Override
    public void ensureQueue(String name) {
        try {
            getQueueUrl(name);
        } catch (SqsException e) {
            // LocalStack may return HTTP 500 for missing queue on GetQueueUrl; treat as not-exist and create
            // QueueDoesNotExistException is a subclass of SqsException, so this covers both cases
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
        String url = getQueueUrl(queue);
        sqs.sendMessage(SendMessageRequest.builder().queueUrl(url).messageBody(body).build());
    }

    @Override
    public Optional<String> receive(String queue) {
        return receive(queue, Duration.ofSeconds(1));
    }

    @Override
    public Optional<String> receive(String queue, Duration timeout) {
        String url = getQueueUrl(queue);
        Instant end = Instant.now().plus(timeout);
        while (Instant.now().isBefore(end)) {
            int wait = (int) Math.max(1, Math.min(20, Duration.between(Instant.now(), end).getSeconds()));
            ReceiveMessageResponse resp = sqs.receiveMessage(ReceiveMessageRequest.builder()
                .queueUrl(url)
                .maxNumberOfMessages(1)
                .waitTimeSeconds(wait)
                .visibilityTimeout(30)
                .build());
            List<Message> msgs = resp.messages();
            if (msgs != null && !msgs.isEmpty()) {
                Message m = msgs.get(0);
                // delete-on-receive semantics
                sqs.deleteMessage(DeleteMessageRequest.builder().queueUrl(url).receiptHandle(m.receiptHandle()).build());
                return Optional.ofNullable(m.body());
            }
        }
        return Optional.empty();
    }

    private String getQueueUrl(String name) {
        GetQueueUrlResponse res = sqs.getQueueUrl(GetQueueUrlRequest.builder().queueName(name).build());
        return res.queueUrl();
    }
}
