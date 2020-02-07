package com.every.every_server.domain.vo.bamboo.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 대나무숲 게시글 VO
 */
@Getter
@Setter
public class BambooPostVO {
    private Integer idx;
    private String title;
    private String content;
    @JsonProperty("created_at")
    private String createdAt;
}
