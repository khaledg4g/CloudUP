package com.projetpi.cloudup;

import com.projetpi.cloudup.Config.TwilioConfig;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class CloudUpApplication {
//
//    @Value("${twilio.accountSid}")
//    private String accountSid ;
//
//    @Value("${twilio.phoneNumber}")
//    private String phoneNumber ;
    public static void main(String[] args) {
        SpringApplication.run(CloudUpApplication.class, args);
    }

    @Autowired
    private TwilioConfig twilioConfig;

    @PostConstruct
    public void setup() {
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }

}
