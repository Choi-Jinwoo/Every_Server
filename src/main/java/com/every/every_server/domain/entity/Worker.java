package com.every.every_server.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "worker")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Worker {
    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idx;

    @JsonProperty("member_idx")
    @JoinColumn(name = "member_idx", unique = true, nullable = false)
    @OneToOne(cascade = CascadeType.REMOVE)
    private Member member;

    @JsonProperty("work_place")
    @Column(name="work_place", nullable = true, length = 60)
    private String workPlace;

    @JsonProperty("work_category")
    @JoinColumn(name = "work_category", nullable = true)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private WorkCategory workCategory;
}
