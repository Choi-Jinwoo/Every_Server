package com.every.every_server.domain.vo.bamboo.reply;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 대나무숲 댓글 VO
 */
@Getter
@Setter
public class BambooReplyVO {
    private Integer idx;
    private String content;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("student_idx")
    private Integer studentIdx;
}
