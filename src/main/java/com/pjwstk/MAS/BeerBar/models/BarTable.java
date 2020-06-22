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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getBarId() {
        return barId;
    }

    public void setBarId(Integer barId) {
        this.barId = barId;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public String toString() {
        return "BarTable{" +
                "id=" + id +
                ", barId=" + barId +
                ", numberOfSeats=" + numberOfSeats +
                '}';
    }
}
