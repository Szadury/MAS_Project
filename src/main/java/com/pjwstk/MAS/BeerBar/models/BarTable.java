package com.pjwstk.MAS.BeerBar.models;

import javax.persistence.*;

@Entity
@Table(name = "BarTable")
public class BarTable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "Bar_Id", nullable = false)
    private Integer barId;

    @Column(name = "NumberOfSeats", nullable = false)
    private Integer numberOfSeats;

    public BarTable() {
    }
    public BarTable(Integer barId, Integer numberOfSeats){
        this.barId = barId;
        this.numberOfSeats = numberOfSeats;
    }

}
