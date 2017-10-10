package com.rashidi.billing.notifier.service.impl;

import com.rashidi.billing.notifier.dao.CustomerRepository;
import com.rashidi.billing.notifier.exception.NotFoundException;
import com.rashidi.billing.notifier.model.Customer;
import com.rashidi.billing.notifier.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * An implementation for CustomerService.
 *
 * @author Mina Rashidi
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private CustomerRepository customerRepository;


    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> listAllCustomers() {
        logger.debug("listAllCustomers called");
        return customerRepository.findAll();

    }

    @Override
    public Customer getCustomerById(Long id) {
        logger.debug("getCustomerById called");
        return Optional.ofNullable(customerRepository.findOne(id)).orElseThrow(() ->
                new NotFoundException("Customer with id: " + id + "does not exist."));
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        logger.debug("saveCustomer called");
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        logger.debug("deleteProduct called");
        customerRepository.delete(id);
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        logger.debug("getCustomerByEmail called");
        return customerRepository.findByEmail(email);
    }

    @Override
    public List<Customer> getCustomersByLastName(String lastName) {
        logger.debug("getCustomersByLastName called");
        return customerRepository.findByLastName(lastName);
    }

}
