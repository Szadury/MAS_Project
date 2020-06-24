package com.pjwstk.MAS.BeerBar.models;

import javax.persistence.*;

@Entity
@Table(name = "NormalUser")
public class NormalUser {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @OneToOne(optional = false)
    @JoinColumn(name = "User_Id")
    private UserModel userModel;

    @Column(name = "PhoneNumber", length = 40)
    private String phoneNumber;


    public NormalUser(int id, UserModel userModel, String phoneNumber) {
        this.id = id;
        this.userModel = userModel;
        this.phoneNumber = phoneNumber;
    }

    public NormalUser() {
    }
}
