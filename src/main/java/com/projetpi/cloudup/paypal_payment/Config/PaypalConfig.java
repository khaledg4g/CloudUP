package com.projetpi.cloudup.paypal_payment.Config;

import com.paypal.base.rest.APIContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class PaypalConfig {
    @Value("${paypal.client-id}")
    private String clientId;
    @Value("${paypal.client-secret}")
    private String clientSecret;
    @Value("${paypal.mode}")
    private String mode;

    @Bean
    public APIContext apiContext() {
        log.info("Configuring PayPal with client-id: {}", clientId);
        return new APIContext(clientId, clientSecret, mode);
    }
}
