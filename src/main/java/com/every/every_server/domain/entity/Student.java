package com.every.every_server.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "student")
@Getter
@Setter
@AllArgsConstructor
public class Student {
    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idx;

    @JsonProperty("member_idx")
    @JoinColumn(name = "member_idx", unique = true, nullable = false)
    @OneToOne
    private Member member;

    @JsonProperty("school_id")
    @Column(name="school_id", nullable = true, length = 20)
    private String schoolId;
}
