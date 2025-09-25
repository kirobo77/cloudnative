// config-client/src/main/java/com/example/configclient/support/ConfigChangeListener.java
package com.example.configclient.support;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ConfigChangeListener {

  private static final Logger log = LoggerFactory.getLogger(ConfigChangeListener.class);

  // Config Client에서 /actuator/refresh 후 Environment가 갱신될 때 변경 키들을 제공
  @EventListener
  @Async
  public void onEnvironmentChange(EnvironmentChangeEvent event) {
    Set<String> keys = event.getKeys();
    log.info("EnvironmentChangeEvent received, changed keys={}", keys);
    notifyServices(keys);
  }

  // RefreshScope 대상 빈이 재프록시/재바인딩된 직후에 발생
  @EventListener
  @Async
  public void onRefreshScopeRefreshed(RefreshScopeRefreshedEvent event) {
    log.info("RefreshScopeRefreshedEvent received for scope={}", event.getName());
  }

  private void notifyServices(Set<String> changedKeys) {
    log.info("Notifying services about configuration changes: {}", changedKeys);
  }
}
