/*
 * Copyright 2025
 * Apache License, Version 2.0
 */
package org.deveasy.test.core.cloud.capability;

import org.deveasy.test.core.cloud.Capability;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Simplified NoSQL Table capability (e.g., DynamoDB, Azure Table, Datastore/Firestore in Datastore mode).
 * The API focuses on the common testing subset to keep provider neutrality.
 */
public interface NoSqlTable extends Capability {

    /** Ensure a table exists with a primary key (hash) and optional sort key. */
    void ensureTable(String tableName, String hashKey, String sortKey);

    /** Delete a table if present. */
    void deleteTable(String tableName);

    /** Put or overwrite an item. */
    void putItem(String tableName, Map<String, Object> item);

    /** Get an item by primary key; returns empty if not found. */
    Optional<Map<String, Object>> getItem(String tableName, Map<String, Object> key);

    /** Delete an item by primary key; no-op if not exists. */
    void deleteItem(String tableName, Map<String, Object> key);

    /**
     * Very simple query by equality on a single attribute; returns up to limit items.
     * Returning order is undefined and provider-specific.
     */
    List<Map<String, Object>> queryEquals(String tableName, String attribute, Object value, int limit);
}
