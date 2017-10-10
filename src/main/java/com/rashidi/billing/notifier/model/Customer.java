package com.rashidi.billing.notifier.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * An entity for representing a Customer.
 *
 * @author Mina Rashidi
 */
@Entity
@Table(name = "CUSTOMER")
@SequenceGenerator(name = "S_CUSTOMER", sequenceName = "S_CUSTOMER", allocationSize = 1)
public class Customer implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_CUSTOMER")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "MOBILE")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
