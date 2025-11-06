/*
 * Copyright 2025
 * Apache License, Version 2.0
 */
package org.deveasy.test.core.cloud.spi;

import org.deveasy.test.core.cloud.CloudProvider;
import org.deveasy.test.core.cloud.TestCloudConfig;
import org.deveasy.test.core.cloud.capability.BlobStorage;
import org.deveasy.test.core.cloud.capability.NoSqlTable;
import org.deveasy.test.core.cloud.capability.PubSub;
import org.deveasy.test.core.cloud.capability.Queue;

/**
 * Service Provider Interface for cloud adapters. Implementations live in provider-specific modules
 * (e.g., test-cloud-aws, test-cloud-azure, test-cloud-gcp) and are discovered via ServiceLoader.
 */
public interface CloudAdapter {

    /** The cloud provider this adapter supports. */
    CloudProvider provider();

    /** Initialize adapter with the resolved test configuration. */
    void initialize(TestCloudConfig config);

    /** Capability accessors (return null if unsupported by this adapter). */
    BlobStorage blobStorage();
    Queue queue();
    PubSub pubSub();
    NoSqlTable noSqlTable();
}
