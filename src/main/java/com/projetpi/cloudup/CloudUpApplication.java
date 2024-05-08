package com.projetpi.cloudup;

import com.projetpi.cloudup.Config.TwilioConfig;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
@EnableScheduling
public class CloudUpApplication {

    @Autowired
    private TwilioConfig twilioConfig;

    @PostConstruct
    public void setup() {
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }

    public static void main(String[] args) {
        SpringApplication.run(CloudUpApplication.class, args);
    }

}
