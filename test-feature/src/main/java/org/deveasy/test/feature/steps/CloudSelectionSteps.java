/*
 * Copyright 2025
 * Apache License, Version 2.0
 */
package org.deveasy.test.feature.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.deveasy.test.core.cloud.CloudMode;
import org.deveasy.test.core.cloud.CloudProvider;
import org.deveasy.test.feature.state.CloudContext;
import org.deveasy.test.feature.state.ScenarioState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Step glue to select cloud provider/mode for a scenario and initialize adapters if available.
 */
public class CloudSelectionSteps {

    private static final Logger log = LoggerFactory.getLogger(CloudSelectionSteps.class);

    private final ScenarioState state;

    public CloudSelectionSteps(ScenarioState state) {
        this.state = state;
    }

    @Given("^cloud provider is \"([^\"]*)\"$")
    public void selectProvider(String providerName) {
        CloudProvider provider = CloudContext.parseProvider(providerName);
        state.getCloudContext().setProvider(provider);
        log.info("[CloudTest] Selected provider: {}", provider);
    }

    @And("^cloud mode is \"([^\"]*)\"$")
    public void selectMode(String modeName) {
        CloudMode mode = CloudContext.parseMode(modeName);
        state.getCloudContext().setMode(mode);
        log.info("[CloudTest] Selected mode: {}", mode);
    }

    @And("^cloud region or location is \"([^\"]*)\"$")
    public void setRegionOrLocation(String value) {
        state.getCloudContext().setRegionOrLocation(value);
        log.info("[CloudTest] Region/Location set: {}", value);
    }

    @And("^cloud project or account is \"([^\"]*)\"$")
    public void setProjectOrAccount(String value) {
        state.getCloudContext().setProjectOrAccount(value);
        log.info("[CloudTest] Project/Account set: {}", value);
    }

    @And("^initialize cloud adapter if available$")
    public void initializeAdapterIfAvailable() {
        if (state.getCloudContext().initAdapterIfAvailable().isPresent()) {
            state.getCloudContext().adapter().ifPresent(a ->
                    log.info("[CloudTest] Cloud adapter initialized for provider {}: {}", a.provider(), a.getClass().getName())
            );
        } else {
            log.warn("[CloudTest] No CloudAdapter found on classpath for provider {}. Add a test-cloud-* module to enable capabilities.",
                    state.getCloudContext().getProvider());
        }
    }
}
