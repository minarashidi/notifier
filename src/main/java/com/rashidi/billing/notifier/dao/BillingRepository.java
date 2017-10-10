package com.rashidi.billing.notifier.dao;

import com.rashidi.billing.notifier.model.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

/**
 * A repository for Billing.
 *
 * @author Mina Rashidi
 */
public interface BillingRepository extends JpaRepository<Billing, Long> {

    @Query("select b from Billing b where b.dueDate <= :date")
    List<Billing> findByDueDate(@Param("date") Instant date);

}
