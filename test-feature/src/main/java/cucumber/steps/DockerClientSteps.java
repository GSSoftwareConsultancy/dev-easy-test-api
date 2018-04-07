package cucumber.steps;

import org.deveasy.test.feature.state.ScenarioState;

/**
 * Steps for managing Docker Container
 * @see com.spotify.docker.client.DockerClient
 */
public class DockerClientSteps {
    private final ScenarioState scenarioState;

    public DockerClientSteps(ScenarioState scenarioState) {
        this.scenarioState = scenarioState;
    }
}
