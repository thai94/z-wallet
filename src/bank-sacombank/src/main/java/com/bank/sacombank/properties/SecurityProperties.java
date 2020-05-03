package com.bank.sacombank.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    public API api = new API();

    @Data
    public class API {
        public String secretKey;
        public String salt;
    }
}
