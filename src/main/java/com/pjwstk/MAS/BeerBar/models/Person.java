package com.pjwstk.MAS.BeerBar.models;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Person {
    @Id
    private long id;
    private String firstName;
    private String lastName;
}
