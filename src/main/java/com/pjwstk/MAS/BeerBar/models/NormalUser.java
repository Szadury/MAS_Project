package com.pjwstk.MAS.BeerBar.models;

import javax.persistence.*;

@Entity
@Table(name = "NormalUser")
public class NormalUser {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @OneToOne
    @JoinColumn(name = "User_Id", nullable = false)
    private UserModel userModel;

    @Column(name = "PhoneNumber", length = 40)
    private String phoneNumber;

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

    public NormalUser(int id, UserModel userModel, String phoneNumber) {
        this.id = id;
        this.userModel = userModel;
        this.phoneNumber = phoneNumber;
    }

    public NormalUser() {
    }
}
