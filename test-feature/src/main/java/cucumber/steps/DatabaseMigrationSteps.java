package cucumber.steps;

import cucumber.api.java.en.Given;
import org.deveasy.test.feature.state.ScenarioState;
import org.flywaydb.core.Flyway;


/**
 * Steps for managing the Application Database.
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
