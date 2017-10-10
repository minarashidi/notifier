package com.rashidi.billing.notifier.service.impl;

import com.rashidi.billing.notifier.model.Email;
import com.rashidi.billing.notifier.service.EmailService;
import com.rashidi.billing.notifier.service.JmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * A Service for consuming Jms.
 *
 * @author Mina rashidi
 */
@Service
public class JmsServiceImpl implements JmsService {

    private EmailService emailService;

    @JmsListener(destination = "notifier.email", containerFactory = "jmsFactory")
    public void consume(Email email) {
        System.out.println("Received <" + email + ">");
        emailService.send(email);
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
}
