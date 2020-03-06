package com.every.every_server.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="schedule")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    @Id
    @Column(name="idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @JsonProperty("start_date")
    @Column(name="start_date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    @Column(name="end_date")
    private LocalDate endDate;

    @JsonProperty("member_idx")
    @JoinColumn(name="member_idx", nullable = false)

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;
}
