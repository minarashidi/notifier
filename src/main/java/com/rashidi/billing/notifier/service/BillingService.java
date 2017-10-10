package com.rashidi.billing.notifier.service;

import com.rashidi.billing.notifier.model.Billing;

import java.time.Instant;
import java.util.List;

/**
 * Services for using Billing.
 *
 * @author Mina Rashidi
 */
public interface BillingService {

    Iterable<Billing> listAllBillings();

    Billing getBillingById(Long id);

    Billing saveBilling(Billing billing);

    void deleteBilling(Long id);

    List<Billing> findByDueDate(Instant date);

}
