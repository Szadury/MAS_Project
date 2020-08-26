package com.pjwstk.MAS.BeerBar.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Bar")
public class Bar {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CompanyOwner_Id")
    private OwnerCompany companyOwner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PersonOwner_Id")
    private PersonOwner personOwner;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "ContactNumber")
    private String contactNumber;

    @Column(name = "Description")
    private String description;

    @Column(name = "startHour", nullable = false)
    private int startHour;

    @Column(name = "endHour", nullable = false)
    private int endHour;

    @OneToMany(mappedBy = "bar", cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "bar", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BarTable> barTables = new ArrayList<>();

    @OneToMany(mappedBy = "bar", cascade = CascadeType.ALL)
    private List<BeerBar> menuList = new ArrayList<>();

    public Bar() {
    }

    public Bar(OwnerCompany companyOwner,
               PersonOwner personOwner,
               String name,
               String address,
               String contactNumber,
               String description) {
        this.companyOwner = companyOwner;
        this.personOwner = personOwner;
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.description = description;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OwnerCompany getCompanyOwner() {
        return companyOwner;
    }

    public void setCompanyOwner(OwnerCompany companyOwner) {
        this.companyOwner = companyOwner;
    }

    public PersonOwner getPersonOwner() {
        return personOwner;
    }

    public void setPersonOwner(PersonOwner personOwner) {
        this.personOwner = personOwner;
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

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public List<BarTable> getBarTables() {
        return barTables;
    }

    public List<BeerBar> getMenuList() {
        return menuList;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    @Override
    public String toString() {
        return "Bar{" +
                "id=" + id +
                ", companyOwner=" + companyOwner +
                ", personOwner=" + personOwner +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", description='" + description + '\'' +
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                '}';
    }
}
