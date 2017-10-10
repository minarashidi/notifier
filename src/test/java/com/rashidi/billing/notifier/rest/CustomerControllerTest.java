package com.rashidi.billing.notifier.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rashidi.billing.notifier.model.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFindAll() {
        ResponseEntity<Customer[]> response = restTemplate.getForEntity("/customers", Customer[].class);
        List<Customer> conditions = Arrays.asList(response.getBody());
        assertThat(conditions).isNotNull();
        assertThat(conditions).hasSize(2);
    }

    @Test
    public void testFindById() {
        ResponseEntity<Customer> response = restTemplate.getForEntity("/customers/1", Customer.class);
        Customer condition = response.getBody();
        assertThat(condition).isNotNull();
        assertThat(condition.getId()).isEqualTo(1);
        assertThat(condition.getFirstName()).isEqualTo("Mina");
        assertThat(condition.getLastName()).isEqualTo("Rashidi");
        assertThat(condition.getEmail()).isEqualTo("mina.rashidi.86@gmail.com");
    }

    @Test
    public void testFindByIdWhenDoesNotExist() {
        ResponseEntity<Customer> response = restTemplate.getForEntity("/customers/10", Customer.class);
        HttpStatus status = response.getStatusCode();
        assertThat(status).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testAddNew() {
        Customer customer = new Customer();
        customer.setFirstName("Ryan");
        customer.setLastName("Johansson");
        customer.setEmail("rjohansson@gmail.com");
        customer.setMobile("7654389");
        URI uri = restTemplate.postForLocation("/customers", customer);
        assertThat(uri).isNotNull();

        String id = uri.getPath().replace("/customers/", "");
        ResponseEntity<Customer> response = restTemplate.getForEntity("/customers/" + id, Customer.class);
        Customer resource = response.getBody();
        assertThat(resource).isNotNull();
        assertThat(resource.getId()).isEqualTo(Long.valueOf(id));
        assertThat(resource.getFirstName()).isEqualTo("Ryan");
        assertThat(resource.getLastName()).isEqualTo("Johansson");
    }

    @Test
    public void testUpdate() {
        Customer customer = new Customer();
        customer.setId(2L);
        customer.setLastName("Hilo");
        customer.setMobile("987654");
        restTemplate.put("/customers/update", customer);

        ResponseEntity<Customer> response = restTemplate.getForEntity("/customers/update/2", Customer.class);
        Customer condition = response.getBody();
        assertThat(condition).isNotNull();
        assertThat(condition.getId()).isEqualTo(2);
        assertThat(condition.getLastName()).isEqualTo("Hilo");
        assertThat(condition.getMobile()).isEqualTo("987654");
    }

    @Test
    public void testUpdateNotFound() throws JsonProcessingException {
        Customer condition = new Customer();
        condition.setId(10L);
        condition.setFirstName("Niki");
        condition.setMobile("876540");
        String url = "/customers/update";

        String request = objectMapper.writeValueAsString(condition);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(request, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testDelete() {
        String url = "/customers/1";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<Customer> findResponse = restTemplate.getForEntity("/customers/1", Customer.class);
        HttpStatus status = findResponse.getStatusCode();
        assertThat(status).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testDeleteNotFound() throws JsonProcessingException {
        String url = "/customers/10";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
