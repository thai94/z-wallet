package com.wallet.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "callback")
public class CallbackProperties {

    public Map<String, CallbackConfig> service = new HashMap<>();
}
