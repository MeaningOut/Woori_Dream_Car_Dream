package com.wooridreamcardream.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name="pictures")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @Column(name="image_url", nullable=false)
    private String imageUrl;
}
