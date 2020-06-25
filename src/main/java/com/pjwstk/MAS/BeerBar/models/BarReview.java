package com.pjwstk.MAS.BeerBar.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "BarReview")
public class BarReview extends Review {
    @Column(name = "ServiceRate")
    private Integer serviceRate;

    @Column(name = "Cleanliness")
    private Integer cleanliness;

    @Column(name = "FoodRate", nullable = false)
    private Integer foodRate;

    public Integer getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(Integer serviceRate) {
        this.serviceRate = serviceRate;
    }

    public Integer getCleanliness() {
        return cleanliness;
    }

    public void setCleanliness(Integer cleanliness) {
        this.cleanliness = cleanliness;
    }

    public Integer getFoodRate() {
        return foodRate;
    }

    public void setFoodRate(Integer foodRate) {
        this.foodRate = foodRate;
    }

    @Override
    public String toString() {
        return "BeerReview{" +
                "serviceRate=" + serviceRate +
                ", cleanliness=" + cleanliness +
                ", foodRate=" + foodRate +
                ", id=" + id +
                ", rate=" + rate +
                ", date=" + date +
                ", userModel=" + userModel +
                '}';
    }
}
