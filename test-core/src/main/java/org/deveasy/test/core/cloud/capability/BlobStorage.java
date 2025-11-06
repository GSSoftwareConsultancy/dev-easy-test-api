/*
 * Copyright 2025
 * Apache License, Version 2.0
 */
package org.deveasy.test.core.cloud.capability;

import org.deveasy.test.core.cloud.Capability;

import java.io.InputStream;
import java.util.List;

/**
 * Provider-agnostic blob storage capability (e.g., S3, Azure Blob, GCS).
 * Implementations should be test-friendly and safe to call in parallel tests.
 */
public interface BlobStorage extends Capability {

    /** Ensure a bucket/container exists. Idempotent. */
    void ensureBucket(String name);

    /** Delete a bucket/container and all of its objects, if supported. */
    void deleteBucket(String name);

    /** Put an object by key using bytes. */
    void putObject(String bucket, String key, byte[] data, String contentType);

    /** Put an object by key using stream. Implementations should close the stream. */
    void putObject(String bucket, String key, InputStream data, String contentType);

    /** Get object contents as bytes, or null if not found. */
    byte[] getObject(String bucket, String key);

    /** Delete an object if present. */
    void deleteObject(String bucket, String key);

    /** List object keys with an optional prefix (empty/"" means all). */
    List<String> listKeys(String bucket, String prefix);

    /** True if object exists. */
    boolean exists(String bucket, String key);
}
