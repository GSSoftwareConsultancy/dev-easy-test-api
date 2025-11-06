/*
 * Copyright 2025
 * Apache License, Version 2.0
 */
package org.deveasy.test.core.cloud.capability;

import org.deveasy.test.core.cloud.Capability;

import java.time.Duration;
import java.util.Optional;

/**
 * Publish/subscribe semantics (e.g., SNS+SQS, Service Bus Topics, GCP Pub/Sub).
 */
public interface PubSub extends Capability {

    /** Ensure a topic exists. Idempotent. */
    void ensureTopic(String name);

    /** Delete a topic if present. */
    void deleteTopic(String name);

    /** Ensure a subscription exists from topic to destination (provider specific). */
    void ensureSubscription(String topic, String subscription);

    /** Publish a textual message to a topic. */
    void publish(String topic, String body);

    /** Receive a message from a subscription if available. */
    Optional<String> receive(String subscription);

    /** Poll until a message is available or timeout expires. */
    Optional<String> receive(String subscription, Duration timeout);
}
