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
package cucumber.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.deveasy.test.core.ResourceHelper;
import org.deveasy.test.feature.state.ScenarioState;
import org.junit.Assert;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static org.apache.http.protocol.HTTP.USER_AGENT;


/**
 * Generic steps for managing http requests /responses using GET, POST, PUT and DELETE methods
 *
 * @see HttpClient
 * @author Joseph Aruja GS Software Consultancy Ltd
 */
public class HttpClientSteps {


    public static final String PORT = "port";
    public static final String HTTP_RESPONSE_CODE = "httpResponseCode";
    public static final String RESPONSE_OBJECT = "responseObject";
    public static final String APPLICATION_JSON = "application/json";
    public static final String HTTP_RESPONSE_ENTITY = "httpResponseEntity";
    private static final Logger logger = LoggerFactory.getLogger(HttpClientSteps.class);
    private final String baseUri = "http://localhost:%s/%s";

    private final String basePort;

    private final ScenarioState scenarioState;

    public HttpClientSteps(ScenarioState scenarioState) {
        this.scenarioState = scenarioState;
        this.basePort = (String) scenarioState.keyValuePairs.get(PORT);
    }

    public static String readFile(String path) throws IOException {
        return ResourceHelper.readJson(path);
    }

    @And("^I perform a GET on \"([^\"]*)\"$")
    public void iPerformAGetOnProvidedUrl(String uri) throws Throwable {
        HttpGet request = new HttpGet(String.format(baseUri, basePort, uri));
        logger.info("get-request : " + request);

        HttpClient httpClient = HttpClientBuilder.create().build();
        request.addHeader("User-Agent", USER_AGENT);
        request.addHeader("Content-type", "application/json");
        HttpResponse response = httpClient.execute(request);
        logger.info("get-response : " + response);

        scenarioState.keyValuePairs.put(HTTP_RESPONSE_CODE, response.getStatusLine().getStatusCode());
        scenarioState.keyValuePairs.put(HTTP_RESPONSE_ENTITY, response);
    }

    @And("^I perform a POST on \"([^\"]*)\" with data \"([^\"]*)\"$")
    public void iPerformAPostTWithProvidedJsonData(String uri, String jsonFile) throws Throwable {
        HttpPost request = new HttpPost(String.format(baseUri, basePort, uri));
        logger.info("post-request : " + request);
        HttpClient httpClient = HttpClientBuilder.create().build();
        request.addHeader("User-Agent", USER_AGENT);
        request.addHeader("Content-type", APPLICATION_JSON);
        if (!jsonFile.equals("")) {
            String json = readFile(jsonFile);
            logger.info("post-json : " + json);
            request.setEntity(new StringEntity(json));
        }
        HttpResponse response = httpClient.execute(request);

        scenarioState.keyValuePairs.put(HTTP_RESPONSE_CODE, response.getStatusLine().getStatusCode());
        scenarioState.keyValuePairs.put(HTTP_RESPONSE_ENTITY, response);
    }

    @And("^I perform a PUT on \"([^\"]*)\" with data \"([^\"]*)\"$")
    public void iPerformAPutOnWithProvidedJsonData(String uri, String jsonFile) throws Throwable {
        HttpPut request = new HttpPut(String.format(baseUri, basePort, uri));
        logger.info("put-request : " + request);
        HttpClient httpClient = HttpClientBuilder.create().build();
        request.addHeader("User-Agent", USER_AGENT);
        request.addHeader("Content-type", APPLICATION_JSON);
        if (!jsonFile.equals("")) {
            String json = readFile(jsonFile);
            logger.info("put-json : " + request);
            request.setEntity(new StringEntity(json));
        }
        HttpResponse response = httpClient.execute(request);

        scenarioState.keyValuePairs.put(HTTP_RESPONSE_CODE, response.getStatusLine().getStatusCode());
        scenarioState.keyValuePairs.put(HTTP_RESPONSE_ENTITY, response);
    }

    @Then("^The response should map to \"([^\"]*)\"$")
    public void theResponseShouldMapToProvidedClass(String classMapping) throws Throwable {
        Class marshallClass = Class.forName(classMapping);

        HttpResponse response = (HttpResponse) scenarioState.keyValuePairs.get(HTTP_RESPONSE_ENTITY);
        String strResponse = EntityUtils.toString(response.getEntity());
        System.out.println(strResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        Object responseObject = objectMapper.readValue(strResponse, marshallClass);

        scenarioState.keyValuePairs.put(RESPONSE_OBJECT, responseObject);
    }

    @Then("^The response should map to a list of \"([^\"]*)\"$")
    public void theResponseShouldMapToAListOf(String classMapping) throws Throwable {
        Class marshallClass = Class.forName(classMapping);

        HttpResponse response = (HttpResponse) scenarioState.keyValuePairs.get(HTTP_RESPONSE_ENTITY);
        String strResponse = EntityUtils.toString(response.getEntity());
        System.out.println(strResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        List<Object> responseObject = objectMapper.readValue(strResponse, objectMapper.getTypeFactory().constructCollectionType(List.class, marshallClass));

        scenarioState.keyValuePairs.put(RESPONSE_OBJECT, responseObject);
    }

    @And("^The response code should be \"([^\"]*)\"$")
    public void theResponseCodeShouldBe(String responseCode) throws Throwable {
        String response = scenarioState.keyValuePairs.get(HTTP_RESPONSE_CODE).toString();
        Assert.assertEquals(responseCode, response);
    }

    @Then("^The response should match  to \"([^\"]*)\"$")
    public void theResponseShouldMatchProvidedJson(String expectedJson) throws Throwable {
        HttpResponse response = (HttpResponse) scenarioState.keyValuePairs.get(HTTP_RESPONSE_ENTITY);
        String strResponse = EntityUtils.toString(response.getEntity());
        JSONAssert.assertEquals(readFile(expectedJson), strResponse, Boolean.FALSE);

    }

}
