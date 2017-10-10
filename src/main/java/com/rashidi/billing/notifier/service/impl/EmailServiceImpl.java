package com.rashidi.billing.notifier.service.impl;

import com.rashidi.billing.notifier.model.Email;
import com.rashidi.billing.notifier.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * A Service for sending Email.
 *
 * @author Mina rashidi
 */
@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender emailSender;

    public void send(Email email) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getTo());
        message.setSubject("Email Notification");
        message.setText(email.getContent());
        message.setFrom("billingnotificationservices@gmail.com");
        emailSender.send(message);

    }

    @Autowired
    public void setEmailSender(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }
}
