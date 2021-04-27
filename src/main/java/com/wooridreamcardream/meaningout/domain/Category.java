package com.wooridreamcardream.meaningout.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.Ignore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
@Getter
@Entity
@Table(name="categories")
public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name= "category_name", nullable=false)
    private String categoryName;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private Collection<Car> carList = new ArrayList<Car>();

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private Collection<Picture> pictureList = new ArrayList<Picture>();
}
