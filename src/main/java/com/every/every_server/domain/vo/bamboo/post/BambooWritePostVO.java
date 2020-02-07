package com.every.every_server.domain.vo.bamboo.post;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 대나무숲 게시글 작성 VO
 */
@Getter
public class BambooWritePostVO {
    @NotBlank
    @Size(max = 40)
    private String title;

    @NotNull
    @Size(max = 950)
    private String content;
}
