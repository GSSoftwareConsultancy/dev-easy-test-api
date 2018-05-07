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
import org.deveasy.test.feature.state.ScenarioState;
import org.flywaydb.core.Flyway;


/**
 * Steps for managing the Application.
 * Currently support Spring, Spring Boot and Drop Wizard Applications
 * @author Joseph Aruja GS Software Consultancy Ltd
 */
public class DatabaseMigrationSteps {


    public static final String DEFAULT_JDBC_MYSQL_URL_WITH_DEFAULT_PORT = "jdbc:mysql://localhost:3306/retail_detail?useSSL=false";
    public static final String DEFAULT_USER = "dev";
    public static final String DEFAULT_PASSWORD = "P4ssw0rd";

    private final ScenarioState scenarioState;

    public DatabaseMigrationSteps(ScenarioState scenarioState) {
        this.scenarioState = scenarioState;
    }

    @Given("^Required tables and data are setup using flyway$")
    public void requiredTablesAndDataAreSetUpUsingFlyWay() throws Throwable {

        configureDbUsingFlyway(DEFAULT_JDBC_MYSQL_URL_WITH_DEFAULT_PORT, DEFAULT_USER, DEFAULT_PASSWORD);
    }


    @Given("^Required tables and data are setup using flyway with URL \"([^\"]*)\" using username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void requiredTablesAndDataAreSetUpUsingFlyWayWithUrlUsernameAndPassword(String dbUrl, String username, String password) throws Throwable {

        configureDbUsingFlyway(dbUrl, username, password);
    }


    private void configureDbUsingFlyway(String jdbcMysqlUrlWithDefaultPort, String user, String password) {
        Flyway flyway = new Flyway();

        flyway.setDataSource(jdbcMysqlUrlWithDefaultPort, user, password);
        //Dont' Migrate any data
        flyway.setLocations();

        flyway.migrate();
    }

}
