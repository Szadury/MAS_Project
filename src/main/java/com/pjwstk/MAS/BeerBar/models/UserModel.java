package com.pjwstk.MAS.BeerBar.models;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "userModel")
    private List<BarReview> barReviewList;

    @OneToMany(mappedBy = "userModel")
    private List<BeerReview> beerReviewList;

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

    public NormalUser getNormalUser() throws Exception{
        if(premiumUser != null){
            throw new Exception("Wrong type, current user is Premium");
        }
        return normalUser;
    }

    public void setNormalUser(NormalUser normalUser) {
        if(this.premiumUser != null) {
            this.premiumUser.setUserModel(null);
            this.premiumUser = null;
            this.normalUser = normalUser;
            this.normalUser.setUserModel(this);
        }
        else if(normalUser != null && this.normalUser != normalUser){
            this.normalUser.setUserModel(null);
            this.normalUser = normalUser;
            this.normalUser.setUserModel(this);
        }
    }

    public PremiumUser getPremiumUser() throws Exception{
        if(normalUser != null){
            throw new Exception("Wrong type, current user is Normal");
        }
        return premiumUser;
    }

    public void setPremiumUser(PremiumUser premiumUser) {
        if(this.normalUser != null) {
            this.normalUser.setUserModel(null);
            this.normalUser = null;
            this.premiumUser = premiumUser;
            this.premiumUser.setUserModel(this);
        }
        else if(this.premiumUser != null && this.premiumUser != premiumUser){
            this.premiumUser.setUserModel(null);
            this.premiumUser = premiumUser;
            this.premiumUser.setUserModel(this);
        }
        this.premiumUser = premiumUser;
    }

    public List<BarReview> getBarReviewList() {
        return barReviewList;
    }

    public List<BeerReview> getBeerReviewList() {
        return beerReviewList;
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
