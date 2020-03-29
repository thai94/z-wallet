package com.wallet.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
public class CallbackConfig {
    public String baseUrl;
    public String callbackMethod;
}
