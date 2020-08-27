package com.pjwstk.MAS.BeerBar.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "BarTable")
public class BarTable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "Bar_Id", nullable = false)
    private Bar bar;

    @Column(name = "NumberOfSeats", nullable = false)
    private Integer numberOfSeats;

    @OneToMany(mappedBy = "barTable", fetch = FetchType.EAGER)
    private List<Reservation> reservations;


    public BarTable() {
    }
    public BarTable(Bar bar, Integer numberOfSeats){
        this.bar = bar;
        this.numberOfSeats = numberOfSeats;
    }

    public int getId() {
        return id;
    }

    public Bar getBar() {
        return bar;
    }

    public void setBar(Bar bar) {
        this.bar = bar;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    @Override
    public String toString() {
        return "BarTable{" +
                "id=" + id +
                ", bar=" + bar +
                ", numberOfSeats=" + numberOfSeats +
                '}';
    }
}
