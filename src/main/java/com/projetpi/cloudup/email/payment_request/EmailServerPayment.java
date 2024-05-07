package com.projetpi.cloudup.email.payment_request;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServerPayment {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;


    public void sendMail(
            String to,
            String username,
            EmailTemplateName emailTemplate,
            String paymentUrl,
            String subject )throws MessagingException
    {
        String templateName;
        if(emailTemplate == null)
        {
            templateName="payment_request";
        }
        else
        {
            templateName = emailTemplate.getName();
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()

        );

        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("paymentUrl",paymentUrl);

        Context context = new Context();
        context.setVariables(properties);

        mimeMessageHelper.setFrom("ouerfellisami61@gmail.com");
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setTo(to);

        String template = templateEngine.process(templateName, context);

        mimeMessageHelper.setText(template,true);

        mailSender.send(mimeMessage);
    }

}
