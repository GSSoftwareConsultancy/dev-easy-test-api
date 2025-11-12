package org.deveasy.test.feature.cloud;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.deveasy.test.core.cloud.capability.Queue;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Provider-neutral Queue steps backed by the resolved CloudAdapter.
 */
public class QueueSteps {

    private Queue queue;
    private String queueName;

    @Given("a queue named {string}")
    public void aQueueNamed(String name) {
        this.queue = CloudSelectionSteps.adapter().queue();
        this.queueName = name;
        queue.ensureQueue(name);
        ScenarioState.put("queueName", name);
    }

    @When("I send a message {string}")
    public void iSendAMessage(String body) {
        if (queue == null) {
            queue = CloudSelectionSteps.adapter().queue();
            queueName = ScenarioState.get("queueName");
        }
        queue.send(queueName, body);
    }

    @Then("within {int}s I receive a message matching {string}")
    public void withinSecondsIReceiveAMessageMatching(int seconds, String expectedSubstring) {
        if (queue == null) {
            queue = CloudSelectionSteps.adapter().queue();
            queueName = ScenarioState.get("queueName");
        }
        var received = queue.receive(queueName, Duration.ofSeconds(seconds));
        assertTrue(received.isPresent(), "Expected to receive a message within timeout");
        assertTrue(received.get().contains(expectedSubstring),
                () -> "Received payload did not contain expected substring. payload=" + received.get());
    }
}
