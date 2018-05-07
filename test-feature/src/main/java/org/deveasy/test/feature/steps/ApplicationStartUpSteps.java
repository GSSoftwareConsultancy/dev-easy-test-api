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

import cucumber.api.java.en.Given;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.deveasy.test.feature.state.ScenarioState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * Steps for managing the Application.
 * Currently Spring, Spring Boot and Drop Wizard Applications
 * @see org.springframework.boot.SpringApplication
 * @see io.dropwizard.Application
 * @author Joseph Aruja GS Software Consultancy Ltd
 */
public class ApplicationStartUpSteps {

    public static final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";

    public static final String ORG_SPRINGFRAMEWORK_BOOT_SPRING_APPLICATION = "org.springframework.boot.SpringApplication";

    public static final String ORG_SPRINGFRAMEWORK_BOOT_SPRING_APPLICATION_METHOD_NAME = "run";

    private static final Logger logger = LoggerFactory.getLogger(ApplicationStartUpSteps.class);

    private final ScenarioState scenarioState;

    public ApplicationStartUpSteps(ScenarioState scenarioState) {
        this.scenarioState = scenarioState;
    }


    @Given("^The Application is running  with \"([^\"]*)\" using profile \"([^\"]*)\" on Port \"([^\"]*)\"$")
    public void applicationIsRunningWithProvidedApplicationClassAndArguments(String applicationClassName, String profile, String port) throws Throwable {
        scenarioState.keyValuePairs.put("port", port);
        startSpringBootApplication(profile, applicationClassName);

    }

    private void startSpringBootApplication(String profile, String applicationClassName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String args[] = new String[0];
        System.setProperty(SPRING_PROFILES_ACTIVE, profile);
        Class<?> applicationClass = ClassUtils.getClass(applicationClassName);
        Class<?> frameworkClassToInvoke = ClassUtils.getClass(ORG_SPRINGFRAMEWORK_BOOT_SPRING_APPLICATION);

        Object run = MethodUtils.invokeExactStaticMethod(frameworkClassToInvoke,
                ORG_SPRINGFRAMEWORK_BOOT_SPRING_APPLICATION_METHOD_NAME, new Object[]{applicationClass},
                args);
        logger.info("org.springframework.boot.SpringApplication is started with config : {}", run.toString());
    }

}