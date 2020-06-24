package com.pjwstk.MAS.BeerBar.models;

import javax.persistence.*;

@MappedSuperclass
public abstract class Person {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Id")
    protected int id;

    @Column(name = "Name")
    protected String name;

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
}
