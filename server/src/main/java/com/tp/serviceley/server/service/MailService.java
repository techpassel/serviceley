package com.tp.serviceley.server.service;

import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {
    //Constructor based dependency injection
    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;
    //Another way of accessing application.properties file values apart from @Value annotation
    private final Environment env;

    @Async
    public void sendMail(NotificationEmail notificationEmail){

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            String senderEmail = env.getProperty("spring.mail.username");
            String applicationName = env.getProperty("application.name");
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(notificationEmail.getSubject());
            mimeMessageHelper.setFrom(new InternetAddress(senderEmail, applicationName));
            mimeMessageHelper.setTo(notificationEmail.getRecipient());
            mimeMessageHelper.setText(notificationEmail.getBody(), true);

            mailSender.send(mimeMessageHelper.getMimeMessage());
//            log.info("");
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new BackendException("Error occurred while sending email to user", e);
        }
    }
}

