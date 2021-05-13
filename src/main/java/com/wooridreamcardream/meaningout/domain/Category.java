package com.wooridreamcardream.meaningout.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
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

    @Column(name= "category_name", nullable=false, unique=true)
    private String categoryName;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private Collection<Car> carList = new ArrayList<Car>();

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private Collection<Picture> pictureList = new ArrayList<Picture>();

    @Builder
    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category(Long id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }
    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", carList=" + carList +
                ", pictureList=" + pictureList +
                '}';
    }
}
