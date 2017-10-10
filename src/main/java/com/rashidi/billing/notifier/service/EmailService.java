package com.rashidi.billing.notifier.service;

import com.rashidi.billing.notifier.model.Email;


public interface EmailService {

    void send(Email email);
}
