package com.rashidi.billing.notifier.service;

import com.rashidi.billing.notifier.model.Email;

public interface JmsService {

    void consume(Email email);
}
