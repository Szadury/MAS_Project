package com.pjwstk.MAS.BeerBar.models;


import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractOwner implements Owner{
    @Id
    long id;
    String email;
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
