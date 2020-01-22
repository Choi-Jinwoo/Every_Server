package com.every.every_server.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "worker")
@Getter
@Setter
public class Worker {
    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idx;

    @JsonProperty("member_id")
    @JoinColumn(name = "member_id", nullable = false)
    @OneToOne
    private Member member;

    @JsonProperty("work_place")
    @Column(name="work_place", nullable = true, length = 60)
    private String workPlace;

    @JsonProperty("work_category")
    @JoinColumn(name = "work_category", nullable = false)
    @ManyToOne
    private WorkCategory workCategory;
}
