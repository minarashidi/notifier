package com.rashidi.billing.notifier.service;

import com.rashidi.billing.notifier.model.Customer;

import java.util.List;

/**
 * Services for using Customer.
 *
 * @author Mina Rashidi
 */
public interface CustomerService {

    List<Customer> listAllCustomers();

    Customer getCustomerById(Long id);

    Customer saveCustomer(Customer customer);

    void deleteCustomer(Long id);

    Customer getCustomerByEmail(String email);

    List<Customer> getCustomersByLastName(String email);
}
