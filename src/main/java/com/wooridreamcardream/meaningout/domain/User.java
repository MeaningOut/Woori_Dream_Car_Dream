package com.wooridreamcardream.meaningout.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.ArrayList;

@NoArgsConstructor
@Getter
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable=false)
    private String name;

    @Column(name="email", nullable=false)
    private String email;

    @Column(name="phone", nullable=false)
    private String phone;

    @OneToOne(mappedBy="user")
    private UserTaste userTaste;

    @OneToMany(mappedBy="user")
    private Collection<BookmarkedCar> bookmarkedCar = new ArrayList<BookmarkedCar>();
}
