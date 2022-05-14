package nodejs;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class NodeJSPerfTesting2 extends Simulation {

    HttpProtocolBuilder httpProtocol =
            http
                    // Here is the root for all relative URLs
                    .baseUrl("http://localhost:3000")
                    // Here are the common headers
                    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .doNotTrackHeader("1")
                    .acceptLanguageHeader("en-US,en;q=0.5")
                    .acceptEncodingHeader("gzip, deflate")
                    .userAgentHeader(
                            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0");

    // A scenario is a chain of requests and pauses
    ScenarioBuilder scn =
            scenario("Scenario Name")
                    .exec(http("request_1").post("/").body(StringBody("{\n" +
                            "\t\"items\": [\n" +
                            "\t\t{\n" +
                            "\t\t\t\"name\": \"Test\",\n" +
                            "\t\t\t\"price\": 1,\n" +
                            "\t\t\t\"standardVAT\": true,\n" +
                            "\t\t\t\"qty\": 5\n" +
                            "\t\t},\n" +
                            "\t\t{\n" +
                            "\t\t\t\"name\": \"Another\",\n" +
                            "\t\t\t\"price\": 4.5,\n" +
                            "\t\t\t\"qty\": 1,\n" +
                            "\t\t\t\"standardVAT\": false\n" +
                            "\t\t}\n" +
                            "\t]\n" +
                            "}\n")).header("Content-Type", "application/json"));

    {
        setUp(
                scn.injectOpen(
                        constantUsersPerSec(1000).during(30),
                        constantUsersPerSec(1100).during(30),
                        constantUsersPerSec(1200).during(30),
                        constantUsersPerSec(1300).during(30),
                        constantUsersPerSec(1400).during(30),
                        constantUsersPerSec(1500).during(30),
                        constantUsersPerSec(1600).during(30),
                        constantUsersPerSec(1700).during(30),
                        constantUsersPerSec(1800).during(30),
                        constantUsersPerSec(1900).during(30),
                        constantUsersPerSec(2000).during(30)
                )
        ).protocols(httpProtocol);
    }
}
