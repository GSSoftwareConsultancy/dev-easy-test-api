/*
 * Copyright 2025
 * Apache License, Version 2.0
 */
package org.deveasy.test.feature.state;

import org.deveasy.test.core.cloud.CloudMode;
import org.deveasy.test.core.cloud.CloudProvider;
import org.deveasy.test.core.cloud.TestCloudConfig;
import org.deveasy.test.core.cloud.spi.CloudAdapter;
import org.deveasy.test.core.cloud.spi.CloudAdapters;

import java.util.Locale;
import java.util.Optional;

/**
 * Holds cloud testing context for a scenario: selected provider/mode and resolved configuration.
 * Provides a lazy-initialized adapter via ServiceLoader where available.
 */
public final class CloudContext {

    private CloudProvider provider = CloudProvider.AWS;
    private CloudMode mode = CloudMode.EMULATOR;
    private String regionOrLocation;
    private String projectOrAccount;

    private TestCloudConfig config; // built lazily
    private CloudAdapter adapter;   // optional, may remain null if not on classpath

    public CloudProvider getProvider() { return provider; }
    public CloudMode getMode() { return mode; }
    public String getRegionOrLocation() { return regionOrLocation; }
    public String getProjectOrAccount() { return projectOrAccount; }

    public void setProvider(CloudProvider provider) { this.provider = provider; this.config = null; }
    public void setMode(CloudMode mode) { this.mode = mode; this.config = null; }
    public void setRegionOrLocation(String value) { this.regionOrLocation = value; this.config = null; }
    public void setProjectOrAccount(String value) { this.projectOrAccount = value; this.config = null; }

    /** Build or return existing immutable config. */
    public TestCloudConfig config() {
        if (config == null) {
            config = TestCloudConfig.builder()
                    .provider(provider)
                    .mode(mode)
                    .regionOrLocation(regionOrLocation)
                    .projectOrAccount(projectOrAccount)
                    .build();
        }
        return config;
    }

    /** Attempt to initialize an adapter using ServiceLoader; returns empty if none is present. */
    public Optional<CloudAdapter> initAdapterIfAvailable() {
        this.adapter = CloudAdapters.tryGet(provider).map(a -> {
            a.initialize(config());
            return a;
        }).orElse(null);
        return Optional.ofNullable(adapter);
    }

    public Optional<CloudAdapter> adapter() { return Optional.ofNullable(adapter); }

    // Utility parsers for step glue
    public static CloudProvider parseProvider(String value) {
        String v = value.trim().toUpperCase(Locale.ROOT);
        if ("GOOGLE".equals(v)) v = "GCP"; // backward alias
        return CloudProvider.valueOf(v);
    }

    public static CloudMode parseMode(String value) {
        return CloudMode.valueOf(value.trim().toUpperCase(Locale.ROOT));
    }
}
