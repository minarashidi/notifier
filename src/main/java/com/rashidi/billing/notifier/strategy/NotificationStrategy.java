package com.rashidi.billing.notifier.strategy;

import com.rashidi.billing.notifier.model.Billing;
import com.rashidi.billing.notifier.model.Customer;
import com.rashidi.billing.notifier.model.NotificationType;

public interface NotificationStrategy {

    boolean match(NotificationType type);

    void generate(Billing billing, Customer customer) throws Exception;
}

