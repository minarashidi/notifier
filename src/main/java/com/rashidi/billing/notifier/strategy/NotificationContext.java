package com.rashidi.billing.notifier.strategy;

import com.rashidi.billing.notifier.model.Billing;
import com.rashidi.billing.notifier.model.Customer;
import com.rashidi.billing.notifier.model.NotificationType;
import com.rashidi.billing.notifier.strategy.impl.EmailStrategy;
import com.rashidi.billing.notifier.strategy.impl.SmsStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class NotificationContext {

    private Map<NotificationType, NotificationStrategy> strategies = new HashMap<>();

    public void generate(NotificationType type, Billing billing, Customer customer) throws Exception {

        strategies.values().stream().filter(strategy -> strategy.match(type)).collect(Collectors.toList()).get(0)
                .generate(billing, customer);
    }

    @Autowired
    public void setSmsStrategy(SmsStrategy smsStrategy) {
        strategies.put(NotificationType.SMS, smsStrategy);
    }

    @Autowired
    public void setEmailStrategy(EmailStrategy emailStrategy) {
        strategies.put(NotificationType.EMAIL, emailStrategy);
    }
}
