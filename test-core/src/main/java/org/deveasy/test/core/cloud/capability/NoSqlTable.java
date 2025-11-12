/*
 * Copyright 2025
 * Apache License, Version 2.0
 */
package org.deveasy.test.core.cloud.capability;

import org.deveasy.test.core.cloud.Capability;

import java.util.List;
import java.util.Map;

/**
 * Provider-agnostic NoSQL table capability (e.g., DynamoDB, Cosmos DB, Firestore/Datastore).
 * The API intentionally focuses on a minimal set of operations commonly needed in tests.
 * Implementations should be idempotent where it makes sense and safe for parallel tests.
 */
public interface NoSqlTable extends Capability {

    // Table management

    /** Ensure a table exists with the given partition (hash) key. Idempotent. */
    void ensureTable(String tableName, String partitionKey);

    /** Ensure a table exists with the given partition (hash) and sort (range) key. Idempotent. */
    void ensureTable(String tableName, String partitionKey, String sortKey);

    /** Delete a table if present. */
    void deleteTable(String tableName);

    // Item operations

    /** Put or overwrite an item (schema-less map). */
    void putItem(String tableName, Map<String, Object> item);

    /** Get an item by partition key value; returns null if not found. */
    Map<String, Object> getItem(String tableName, String partitionKeyValue);

    /** Get an item by partition and sort key values; returns null if not found. */
    Map<String, Object> getItem(String tableName, String partitionKeyValue, String sortKeyValue);

    /** Delete an item by partition key value; no-op if not exists. */
    void deleteItem(String tableName, String partitionKeyValue);

    /** Delete an item by partition and sort key values; no-op if not exists. */
    void deleteItem(String tableName, String partitionKeyValue, String sortKeyValue);

    // Query/scan

    /** Scan all items in a table. Returning order is undefined. */
    List<Map<String, Object>> scan(String tableName);

    /** Query by partition key value. Returning order is undefined. */
    List<Map<String, Object>> query(String tableName, String partitionKeyValue);
}
