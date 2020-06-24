package com.pjwstk.MAS.BeerBar.models;


import javax.persistence.*;

@MappedSuperclass
public class AbstractOwner implements Owner{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Id")
    long id;

    @Column(name = "Email", length = 40, nullable = false)
    private String email;

    @Column(name = "PhoneNumber", length = 40, nullable = false)
    String phoneNumber;

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
