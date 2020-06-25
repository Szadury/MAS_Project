package com.pjwstk.MAS.BeerBar.models;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
public abstract class Review {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Id")
    protected long id;

    @Column(name = "Rate", nullable = false)
    protected Integer rate;

    @Column(name = "Date", nullable = false)
    protected LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "User_Id", nullable = false)
    protected UserModel userModel;

    public Review() {
    }

    public long getId() {
        return id;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }


}
