/*
 * Copyright 2025
 * Apache License, Version 2.0
 */
package org.deveasy.test.core.cloud.capability;

import org.deveasy.test.core.cloud.Capability;

import java.time.Duration;
import java.util.Optional;

/**
 * Point-to-point queue semantics (e.g., SQS, Azure Queue, Service Bus queue).
 */
public interface Queue extends Capability {

    /** Ensure a queue exists. Idempotent. */
    void ensureQueue(String name);

    /** Delete the queue if exists. */
    void deleteQueue(String name);

    /** Send a textual message. */
    void send(String queue, String body);

    /**
     * Receive a message if available, deleting it from the queue when returned.
     * Implementations should use reasonable defaults for visibility timeouts.
     */
    Optional<String> receive(String queue);

    /**
     * Poll until a message is available or timeout expires.
     */
    Optional<String> receive(String queue, Duration timeout);
}
