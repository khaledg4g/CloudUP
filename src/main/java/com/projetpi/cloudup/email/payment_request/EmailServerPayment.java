package com.projetpi.cloudup.email.payment_request;

import com.projetpi.cloudup.email.payment_request.EmailTemplateName;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
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
            long reservationId,
            String subject,
            String domainUrl) throws MessagingException {
        String templateName = (emailTemplate != null) ? emailTemplate.getName() : "payment_request";
        String paymentUrl = String.format("%s/checkout;reservationId=%d", domainUrl, reservationId);
// http://localhost:4200/Home/students/checkout;reservationId=1reservationId=
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );

        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("paymentUrl", paymentUrl);

        Context context = new Context();
        context.setVariables(properties);

        mimeMessageHelper.setFrom("ouerfellisami61@gmail.com"); // Set this to a relevant email address
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setTo(to);

        String template = templateEngine.process(templateName, context);

        mimeMessageHelper.setText(template, true);

        mailSender.send(mimeMessage);
    }

/*
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
*/
}
