package com.pjwstk.MAS.BeerBar.models;

import javax.persistence.*;

@Entity
@Table(name = "Bar")
public class Bar {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "CompanyOwner_Id")
    private Integer companyOwnerId;

    @Column(name = "PersonOwner_Id")
    private Integer personOwnerId;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "ContactNumber")
    private String contactNumber;

    @Column(name = "Description")
    private String description;

    public Bar() {
    }

    public Bar(Integer companyOwnerId,
               Integer personOwnerId,
               String name,
               String address,
               String contactNumber,
               String description) {
        this.companyOwnerId = companyOwnerId;
        this.personOwnerId = personOwnerId;
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.description = description;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCompanyOwnerId() {
        return companyOwnerId;
    }

    public void setCompanyOwnerId(Integer companyOwnerId) {
        this.companyOwnerId = companyOwnerId;
    }

    public Integer getPersonOwnerId() {
        return personOwnerId;
    }

    public void setPersonOwnerId(Integer personOwnerId) {
        this.personOwnerId = personOwnerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
