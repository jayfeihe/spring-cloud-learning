package com.jay.bean;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "test_user")
public class TestUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column
    private String password;

}
