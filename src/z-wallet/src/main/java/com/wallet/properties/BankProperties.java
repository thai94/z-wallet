package com.wallet.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Data
@Configuration
@ConfigurationProperties(prefix = "bank")
public class BankProperties {

    public HashMap<String, BankConfig> connector = new HashMap<>();
}
