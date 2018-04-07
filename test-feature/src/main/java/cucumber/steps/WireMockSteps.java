package cucumber.steps;

import org.deveasy.test.feature.state.ScenarioState;

/**
 * Steps for mocking external service dependencies
 * @see com.github.tomakehurst.wiremock.client.WireMock
 */
public class WireMockSteps {

    private final ScenarioState scenarioState;

    public WireMockSteps(ScenarioState scenarioState) {
        this.scenarioState = scenarioState;
    }

}
