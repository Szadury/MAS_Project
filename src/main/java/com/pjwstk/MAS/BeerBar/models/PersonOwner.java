package com.pjwstk.MAS.BeerBar.models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class PersonOwner extends Person implements Owner {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "PhoneNumber", nullable = false)
    private String phoneNumber;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "BirthDate", nullable = false)
    private Date birthDate;

    @Column(name="Password", length = 30, nullable = false)
    private String password;

    public PersonOwner() {
    }

    public PersonOwner(String email, String phoneNumber, String name, Date birthDate, String password) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.birthDate = birthDate;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
