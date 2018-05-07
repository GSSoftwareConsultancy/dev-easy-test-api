/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.deveasy.test.feature.steps;

import org.deveasy.test.feature.state.ScenarioState;

/**
 * Steps for managing the Application.
 * Currently support Spring, Spring Boot and Drop Wizard Applications
 * @author Joseph Aruja GS Software Consultancy Ltd
 */
public class AwsKinesisSteps {
    private final ScenarioState scenarioState;

    public AwsKinesisSteps(ScenarioState scenarioState) {
        this.scenarioState = scenarioState;
    }

}
