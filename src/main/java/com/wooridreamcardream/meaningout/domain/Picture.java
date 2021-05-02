package com.wooridreamcardream.meaningout.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
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

    @Builder
    public Picture(Category category, String imageUrl) {
        this.category = category;
        this.imageUrl = imageUrl;
    }
}
