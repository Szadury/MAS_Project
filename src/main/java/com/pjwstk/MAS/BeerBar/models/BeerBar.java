package com.pjwstk.MAS.BeerBar.models;

import javax.persistence.*;

@Entity
@Table(name = "BeerBar")
public class BeerBar {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "Bar_Id", nullable = false)
    private Integer barId;

    @Column(name = "Beer_Id", nullable = false)
    private Integer beerId;

    @Column(name = "isActive", nullable = false)
    private boolean isActive;

    public BeerBar() {
    }
    public BeerBar(Integer barId, Integer beerId, boolean isActive) {
        this.barId = barId;
        this.beerId = beerId;
        this.isActive = isActive;
    }

}
