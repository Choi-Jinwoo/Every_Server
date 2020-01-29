package com.every.every_server.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter
@Setter
@NoArgsConstructor
public class Member {
    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idx;

    @Column(name = "email", unique = true, nullable = false, length = 50)
    private String email;

    @Column(name="pw", nullable = false, length = 200)
    private String pw;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "phone", unique = true, nullable = false, length = 20)
    private String phone;

    @JsonProperty("birth_year")
    @Column(name = "birth_year", nullable = false)
    private Integer birthYear;
}
