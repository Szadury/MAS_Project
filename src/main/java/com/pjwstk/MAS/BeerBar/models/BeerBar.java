package com.pjwstk.MAS.BeerBar.models;

import javax.persistence.*;

@Entity
@Table(name = "BeerBar")
public class BeerBar {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "Bar_Id", nullable = false)
    private Bar bar;

    @ManyToOne
    @JoinColumn(name = "Beer_Id", nullable = false)
    private Beer beer;

    @Column(name = "isActive", nullable = false)
    private boolean isActive;

    public BeerBar() {
    }
    public BeerBar(Bar bar, Beer beer, boolean isActive) {
        this.bar = bar;
        this.beer = beer;
        this.isActive = isActive;
    }

}
