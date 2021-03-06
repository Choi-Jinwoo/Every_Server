package com.every.every_server.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bamboo_post")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BambooPost {
    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @JsonProperty("student_idx")
    @JoinColumn(name = "student_idx", nullable = true)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Student student;

    @JsonProperty("created_at")
    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt = new Date();
}

