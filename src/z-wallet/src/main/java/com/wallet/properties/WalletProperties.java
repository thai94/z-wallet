package com.wallet.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "wallet")
public class WalletProperties {
    public String baseUrl;
    public String addCashMethod;
    public String subTractCashMethod;
}
