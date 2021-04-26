package com.wooridreamcardream.domain;

import javax.persistence.*;

@Entity
@Table(name="user_tastes")
public class UserTaste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="cc")
    private String cc;
}
