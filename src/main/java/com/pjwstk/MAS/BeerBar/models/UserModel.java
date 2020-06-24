package com.pjwstk.MAS.BeerBar.models;

import javax.persistence.*;

@Entity
@Table(name = "UserModel")
public class UserModel extends Person{
    @Column(name = "Nickname", nullable = false)
    protected String username;

    @Column(name = "Email", nullable = false)
    protected String email;

    @Column(name = "Password", nullable = false)
    protected String password;

    @OneToOne(mappedBy = "userModel")
    private NormalUser normalUser;

    @OneToOne(mappedBy = "userModel")
    private PremiumUser premiumUser;

    public UserModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                ", id=" + id +
                ", name='" + name + '\'' +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", normalUser=" + normalUser + '\'' +
                ", premiumUser=" + premiumUser +
                '}';
    }
}
