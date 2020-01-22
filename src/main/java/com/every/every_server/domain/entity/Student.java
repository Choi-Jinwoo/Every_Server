package com.every.every_server.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "student")
@Getter
@Setter
public class Student {
    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idx;

    @JsonProperty("member_id")
    @JoinColumn(name = "member_id", nullable = false)
    @OneToOne
    private Member member;

    @JsonProperty("school_id")
    @Column(name="school_id", nullable = true, length = 20)
    private String schoolId;
}
