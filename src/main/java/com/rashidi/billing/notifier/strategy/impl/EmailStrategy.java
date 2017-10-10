package com.rashidi.billing.notifier.strategy.impl;

import com.rashidi.billing.notifier.model.Billing;
import com.rashidi.billing.notifier.model.Customer;
import com.rashidi.billing.notifier.model.Email;
import com.rashidi.billing.notifier.model.NotificationType;
import com.rashidi.billing.notifier.strategy.NotificationStrategy;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;
import java.util.Map;

@Component
public class EmailStrategy implements NotificationStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailStrategy.class);

    private Configuration freeMarkerConfiguration;
    private JmsTemplate jmsTemplate;

    @Override
    public boolean match(NotificationType type) {
        return type == NotificationType.EMAIL;
    }

    @Override
    public void generate(Billing billing, Customer customer) throws Exception {

        Map<String, Object> model = new HashMap<>();
        model.put("billing", billing);
        model.put("customer", customer);

        Template template = freeMarkerConfiguration.getTemplate("email.ftl");
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        Email email = new Email(customer.getEmail(), content);
        jmsTemplate.convertAndSend("notifier.email", email);
    }

    @Autowired
    public void setFreeMarkerConfiguration(Configuration freeMarkerConfiguration) {
        this.freeMarkerConfiguration = freeMarkerConfiguration;
    }

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
}
