package com.pjwstk.MAS.BeerBar.models;

import javax.persistence.*;
import java.util.List;

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

    @Column(name = "PhoneNumber", length = 14, nullable = false)
    private String phoneNumber;

    @Column(name = "Password", length = 30, nullable = false)
    private String password;

    @OneToMany(mappedBy = "companyOwner")
    private List<Bar> barList;

    public OwnerCompany() {
    }

    public OwnerCompany(String regonNumber, String name, String email, String phoneNumber, String password)
    {
        this.regonNumber = regonNumber;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
