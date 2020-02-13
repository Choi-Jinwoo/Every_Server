package com.every.every_server.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bamboo_reply")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BambooReply {
    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column(name = "content", nullable = false, length = 500)
    private String content;

    @JsonProperty("student_idx")
    @JoinColumn(name = "student_idx", nullable = false)
    @ManyToOne(cascade = CascadeType.REMOVE)
    private Student student;

    @JsonProperty("bamboo_post_idx")
    @JoinColumn(name = "bamboo_post_idx", nullable = false)
    @ManyToOne(cascade = CascadeType.REMOVE)
    private BambooPost bambooPost;

    @JsonProperty("created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();
}
