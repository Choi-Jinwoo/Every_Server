package com.every.every_server.service.bamboo;

import com.every.every_server.domain.vo.bamboo.post.BambooPostVO;
import com.every.every_server.domain.vo.bamboo.post.BambooWritePostVO;
import com.every.every_server.domain.vo.bamboo.reply.BambooModifyReplyVO;
import com.every.every_server.domain.vo.bamboo.reply.BambooReplyVO;
import com.every.every_server.domain.vo.bamboo.reply.BambooWriteReplyVO;

import java.util.List;

public interface BambooService {
    public List<BambooPostVO> getBambooPosts(Integer memberIdx, String order);
    public boolean writeBambooPost(Integer memberIdx, BambooWritePostVO bambooWritePostVO);
    public List<BambooReplyVO> getBambooReplies(Integer memberIdx, Integer postIdx);
    public boolean writeBambooReply(Integer memberIdx, BambooWriteReplyVO bambooWriteReplyVO);
    public boolean modifyBambooReply(Integer memberIdx, Integer idx, BambooModifyReplyVO bambooModifyReplyVO);
    public boolean deleteBambooReply(Integer memberIdx, Integer idx);
    public BambooPostVO getBambooPost(Integer memberIdx, Integer postIdx);
}
