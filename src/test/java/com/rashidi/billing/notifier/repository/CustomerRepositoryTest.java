package com.rashidi.billing.notifier.repository;

import com.rashidi.billing.notifier.dao.CustomerRepository;
import com.rashidi.billing.notifier.model.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

//In-memory embedded databases generally work well for tests since they are fast and don’t require any developer
// installation. If, however, you prefer to run tests against a real database you can use the
// @AutoConfigureTestDatabase annotation:

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;


    @Before
    public void setUp() throws Exception {

        customerRepository.deleteAll();

        Customer customer1 = new Customer();
        customer1.setFirstName("Sara");
        customer1.setLastName("Johi");
        customer1.setEmail("sara.johi@gmail.com");
        customer1.setMobile("1234567");

        Customer customer2 = new Customer();
        customer2.setFirstName("Niki");
        customer2.setLastName("Kili");
        customer2.setEmail("niki.kili@gmail.com");
        customer2.setMobile("8765432");

        customerRepository.save(customer1);
        customerRepository.save(customer2);
    }

    @Test
    public void testLoadCustomers() {
        List<Customer> customers = customerRepository.findAll();
        assertEquals("Did not get all customers", 2, customers.size());
    }

    @Test
    public void testFindByLastName() {

        // given
        Customer customer = new Customer();
        customer.setFirstName("Mina");
        customer.setLastName("Rashidi");
        customer.setEmail("mina.rashidi.86@gmail.com");
        customer.setFirstName("9876543");
        entityManager.persist(customer);

        // when
        List<Customer> customers = customerRepository.findByLastName(customer.getLastName());

        // then
        assertEquals("Found wrong number of Rashidi Ups", 1, customers.size());
        assertEquals("Found wrong last name", "Rashidi", customers.get(0).getLastName());
    }


    @Test
    public void testCRUD() throws Exception {

        // Create a new game
        Customer customer = new Customer();
        customer.setFirstName("Mina");
        customer.setLastName("Rashidi");
        customer.setEmail("mina.rashidi.86@gmail.com");
        customer.setFirstName("9876543");
        customerRepository.save(customer);

        // Assert it was created
        List<Customer> customers = customerRepository.findByLastName(customer.getLastName());
        assertEquals("Did not find customer", customer.getLastName(), customers.get(0).getLastName());

        // Edit the lastName
        String newLastName = "Khaki";
        customers.get(0).setLastName(newLastName);
        customerRepository.save(customer);

        // Assert it updated
        List<Customer> updatedCustomer = customerRepository.findByLastName(customer.getLastName());
        assertEquals("Did not update last name", newLastName, updatedCustomer.get(0).getLastName());

        // Delete customer
        customerRepository.delete(updatedCustomer);
        // Assert not found
        List<Customer> emptyCustomer = customerRepository.findByLastName(customer.getLastName());
        assertEquals("Should have returned no customers", 0, emptyCustomer.size());
    }
}
