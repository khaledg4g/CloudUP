//package com.projetpi.cloudup.Config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import com.twilio.Twilio;
//
//import jakarta.annotation.PostConstruct;
//@SpringBootApplication
//@EnableConfigurationProperties
//public class OtpApplication {
//
//    @Autowired
//    private TwilioConfig twilioConfig;
//
//    @Value("${twilio.account-sid}")
//    private String accountSid ;
//
//    @Value("${twilio.phoneNumber}")
//    private String phoneNumber ;
//    @PostConstruct
//    public void setup() {
//        Twilio.init(accountSid, phoneNumber);
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(OtpApplication.class, args);
//    }
//
//}
