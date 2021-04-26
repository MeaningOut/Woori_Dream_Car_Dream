package com.wooridreamcardream.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Entity
@Table(name="categories")
public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name= "category_name", nullable=false)
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private Collection<Car> carList = new ArrayList<Car>();

    @OneToMany(mappedBy = "category")
    private Collection<Picture> pictureList = new ArrayList<Picture>();
}
