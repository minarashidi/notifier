package com.rashidi.billing.notifier.dao;

import com.rashidi.billing.notifier.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * A repository for Billing.
 *
 * @author Mina Rashidi
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select c from Customer c where c.email = :email")
    Customer findByEmail(@Param("email") String email);

    @Query("select c from Customer c where c.lastName = :lastName")
    List<Customer> findByLastName(@Param("lastName") String lastName);

}
