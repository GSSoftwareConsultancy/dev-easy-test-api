package org.deveasy.test.feature;

import cucumber.api.java.en.Given;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.deveasy.test.feature.org.deveasy.test.feature.state.ScenarioState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * Steps for managing the Application. Currently supports only SpringBoot Applications.
 */
public class ApplicationStartUpSteps {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationStartUpSteps.class);

    public static final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";

    public static final String ORG_SPRINGFRAMEWORK_BOOT_SPRING_APPLICATION = "org.springframework.boot.SpringApplication";

    public static final String ORG_SPRINGFRAMEWORK_BOOT_SPRING_APPLICATION_METHOD_NAME = "run";

    private final ScenarioState scenarioState;

    public ApplicationStartUpSteps(ScenarioState sharedCucumberStepsData) {
        this.scenarioState = sharedCucumberStepsData;
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