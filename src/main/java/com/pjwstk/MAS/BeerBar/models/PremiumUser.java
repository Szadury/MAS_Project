package com.pjwstk.MAS.BeerBar.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PremiumUser")
public class PremiumUser {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @OneToOne
    @JoinColumn(name = "User_Id", nullable = false)
    private UserModel userModel;

    @Column(name = "PhoneNumber", length = 40, nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservationList;


    public PremiumUser() {
    }

    public PremiumUser(UserModel userModel, String phoneNumber) {
        this.userModel = userModel;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
