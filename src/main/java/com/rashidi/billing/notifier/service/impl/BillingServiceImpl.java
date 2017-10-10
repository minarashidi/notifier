package com.rashidi.billing.notifier.service.impl;

import com.rashidi.billing.notifier.dao.BillingRepository;
import com.rashidi.billing.notifier.exception.NotFoundException;
import com.rashidi.billing.notifier.model.Billing;
import com.rashidi.billing.notifier.service.BillingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * An implementation for BillingService.
 *
 * @author Mina rashidi
 */
@Service
@Transactional
public class BillingServiceImpl implements BillingService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private BillingRepository billingRepository;

    @Override
    public Iterable<Billing> listAllBillings() {
        logger.debug("listAllBillings called");
        return billingRepository.findAll();
    }

    @Override
    public Billing getBillingById(Long id) {
        logger.debug("getBillingById called");
        return Optional.ofNullable(billingRepository.findOne(id)).orElseThrow(() ->
                new NotFoundException("Customer with id: " + id + "does not exist."));
    }

    @Override
    public Billing saveBilling(Billing billing) {
        logger.debug("saveBilling called");
        return billingRepository.save(billing);
    }

    @Override
    public void deleteBilling(Long id) {
        logger.debug("deleteBilling called");
        billingRepository.delete(id);
    }

    @Override
    public List<Billing> findByDueDate(Instant date) {
        logger.debug("findByDueDate called");
        return billingRepository.findByDueDate(date);
    }

    @Autowired
    public void setBillingRepository(BillingRepository billingRepository) {
        this.billingRepository = billingRepository;
    }
}
