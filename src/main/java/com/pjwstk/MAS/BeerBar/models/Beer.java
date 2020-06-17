package com.pjwstk.MAS.BeerBar.models;

import javax.persistence.*;

@Entity
@Table(name = "Beer")
public class Beer {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Percentage", nullable = false)
    private Double percentage;

    @Column(name = "BeerType", nullable = false)
    private String beerType;

    @Column(name = "Aroma")
    private String aroma;

    @Column(name = "Description")
    private String description;

    public Beer() {
    }

    public Beer(String name,
                Double percentage,
                String beerType,
               String aroma,
               String description) {
        this.name = name;
        this.percentage = percentage;
        this.beerType = beerType;
        this.aroma = aroma;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public String getBeerType() {
        return beerType;
    }

    public void setBeerType(String beerType) {
        this.beerType = beerType;
    }

    public String getAroma() {
        return aroma;
    }

    public void setAroma(String aroma) {
        this.aroma = aroma;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
