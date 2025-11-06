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
package org.deveasy.test.feature.state;

import java.util.HashMap;

/**
 * Scenario-wide state bag for step glue. Now also carries CloudContext.
 */
public class ScenarioState {

    public HashMap<String, Object> keyValuePairs = new HashMap<>();

    private CloudContext cloudContext = new CloudContext();

    public CloudContext getCloudContext() {
        return cloudContext;
    }

    public void setCloudContext(CloudContext cloudContext) {
        this.cloudContext = cloudContext;
    }
}
