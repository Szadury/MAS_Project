package com.pjwstk.MAS.BeerBar.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "BeerReview")
public class BeerReview extends Review {
    @Column(name = "AromaRate")
    private Integer aromaRate;

    @Column(name = "ColorRate")
    private Integer colorRate;

    @Column(name = "tasteRate", nullable = false)
    private Integer tasteRate;

    public Integer getAromaRate() {
        return aromaRate;
    }

    public void setAromaRate(Integer aromaRate) {
        this.aromaRate = aromaRate;
    }

    public Integer getColorRate() {
        return colorRate;
    }

    public void setColorRate(Integer colorRate) {
        this.colorRate = colorRate;
    }

    public Integer getTasteRate() {
        return tasteRate;
    }

    public void setTasteRate(Integer tasteRate) {
        this.tasteRate = tasteRate;
    }

    @Override
    public String toString() {
        return "BeerReview{" +
                "aromaRate=" + aromaRate +
                ", colorRate=" + colorRate +
                ", tasteRate=" + tasteRate +
                ", id=" + id +
                ", rate=" + rate +
                ", date=" + date +
                ", userModel=" + userModel +
                '}';
    }
}
