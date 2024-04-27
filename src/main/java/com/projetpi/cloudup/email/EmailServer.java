package com.projetpi.cloudup.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UrlPathHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmailServer {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;


    @Async
    public void sendMail(
            String to,
            String username,
            EmailTemplateName emailTemplate,
            String confirmationUrl,
            String activationCode,
            String subject )throws MessagingException
    {
        String templateName;
        if(emailTemplate == null)
        {
            templateName="Confirm-email";
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
    properties.put("confirmationUrl",confirmationUrl);
    properties.put("activation_code",activationCode);

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
