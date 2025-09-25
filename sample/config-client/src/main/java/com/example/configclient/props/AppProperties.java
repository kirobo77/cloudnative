// config-client/src/main/java/com/example/configclient/props/AppProperties.java
package com.example.configclient.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class AppProperties {

  private String message;
  private String name;
  private String version;
  private Environment environment = new Environment();
  private Database database = new Database();
  private Security security = new Security();

  @Getter @Setter
  public static class Environment {
    private String name;
  }

  @Getter @Setter
  public static class Database {
    private Integer maxConnections;
    private Integer connectionTimeout;
  }

  @Getter @Setter
  public static class Security {
    private Integer jwtExpirationSeconds;
    private String jwtSecret;
  }
}
