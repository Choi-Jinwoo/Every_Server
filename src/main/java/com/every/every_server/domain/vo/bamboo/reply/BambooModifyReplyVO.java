package com.every.every_server.domain.vo.bamboo.reply;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/*
 * 대나무숲 댓글 수정 VO
 */
@Getter
public class BambooModifyReplyVO {
    @NotBlank
    @Size(max = 490)
    private String content;
}
