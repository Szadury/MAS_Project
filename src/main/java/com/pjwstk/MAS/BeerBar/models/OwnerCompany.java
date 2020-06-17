package com.pjwstk.MAS.BeerBar.models;

import javax.persistence.*;

@Entity
@Table(name = "CompanyOwner")
public class OwnerCompany extends AbstractOwner implements Owner{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "RegonNumber", length = 15, nullable = false)
    private String regonNumber;

    @Column(name = "Name", length = 40, nullable = false)
    private String name;

    @Column(name = "Email", length = 40, nullable = false)
    private String email;

    @Column(name = "PhoneNumber", length = 14, nullable = false)
    private String phoneNumber;

    @Column(name = "Password", length = 30, nullable = false)
    private String password;

    public OwnerCompany() {
    }

    public OwnerCompany(String regonNumber, String name, String email, String phoneNumber, String password)
    {
        this.regonNumber = regonNumber;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
