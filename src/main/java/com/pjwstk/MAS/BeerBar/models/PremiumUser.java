package com.pjwstk.MAS.BeerBar.models;

import javax.persistence.*;

@Entity
@Table(name = "PremiumUser")
public class PremiumUser {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @OneToOne(optional = false)
    @JoinColumn(name = "User_Id")
    private UserModel userModel;

    @Column(name = "PhoneNumber", length = 40, nullable = false)
    private String phoneNumber;

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
