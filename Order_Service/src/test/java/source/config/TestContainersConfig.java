package source.config;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

@Testcontainers
public abstract class TestContainersConfig {

  @Container
  protected static final PostgreSQLContainer<?> POSTGRES =
          new PostgreSQLContainer<>("postgres:15")
                  .withDatabaseName("testdb")
                  .withUsername("user")
                  .withPassword("pass");

  // WireMock - локальный HTTP сервер для заглушек
  protected static WireMockServer wireMockServer;

  @BeforeAll
  static void startWireMock() {
    wireMockServer = new WireMockServer(WireMockConfiguration.options().dynamicPort());
    wireMockServer.start();
  }

  @AfterAll
  static void stopWireMock() {
    if (wireMockServer != null) wireMockServer.stop();
  }

  @DynamicPropertySource
  static void registerProperties(DynamicPropertyRegistry registry) {
    // Datasource props from Testcontainers
    registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
    registry.add("spring.datasource.username", POSTGRES::getUsername);
    registry.add("spring.datasource.password", POSTGRES::getPassword);

    // Point the app's user.service.url to WireMock
    registry.add("user.service.url", () -> wireMockServer.baseUrl() + "/api/v1/users");
  }
}

