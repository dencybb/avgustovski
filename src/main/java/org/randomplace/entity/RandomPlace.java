package com.randomplace.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "random_place")
public class RandomPlace extends PanacheEntity {

    public String naziv;
    public Long brojStanovnika;
    public String latituda;
    public String longituda;
    public String nadmorskaVisina;

    @Transient
    public byte[] slika;

    public String putanjaDoSlike;
}