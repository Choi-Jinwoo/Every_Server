package com.every.every_server.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "student")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idx;

    @JsonProperty("member_idx")
    @JoinColumn(name = "member_idx", unique = true, nullable = false)
    @OneToOne(cascade = CascadeType.REMOVE)
    private Member member;

    @JsonProperty("school_id")
    @Column(name="school_id", nullable = true, length = 20)
    private String schoolId;
}
