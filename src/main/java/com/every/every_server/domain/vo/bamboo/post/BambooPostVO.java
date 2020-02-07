package com.every.every_server.domain.vo.bamboo.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 대나무숲 게시글 VO
 */
@Getter
public class BambooPostVO {
    private Integer idx;
    private String title;
    private String content;
    @JsonProperty("created_at")
    private Date createdAt;
}
