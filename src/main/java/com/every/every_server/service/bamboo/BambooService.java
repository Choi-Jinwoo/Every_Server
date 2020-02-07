package com.every.every_server.service.bamboo;

import com.every.every_server.domain.vo.bamboo.post.BambooPostVO;
import com.every.every_server.domain.vo.bamboo.post.BambooWritePostVO;

import java.util.List;

public interface BambooService {
    public List<BambooPostVO> getBambooPosts(Integer memberIdx);
    public boolean writeBambooPost(Integer memberIdx, BambooWritePostVO bambooWritePostVO);
}
