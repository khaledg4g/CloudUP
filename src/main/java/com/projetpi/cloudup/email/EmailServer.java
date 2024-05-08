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

    public void sendEmailNotification(String userEmail, String subject) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(userEmail);
            helper.setSubject("Publication solved Notification");
            helper.setText("Your publication has been solved! Please check your blog for more info.\n **Subject of the publication :\n" + subject );
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public void sendEmailNotif (String userEmail, String content, String subject){
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(userEmail);
            helper.setSubject("Your comment is tagged as a solution");
            helper.setText("Your comment was tagged as a solution. Thank you for helping others! \n **Content of the comment :\n" +
                    content + "\n **This comment was written under the subject of this post:\n" + subject);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendEmailNotificationcol(String userEmail, String subject) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(userEmail);
            helper.setSubject("Collaboration  Notification");
            helper.setText("Your Collaboration is fully booked" + subject );
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
