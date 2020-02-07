package com.every.every_server.service.bamboo;

import com.every.every_server.domain.repository.BambooPostRepo;
import com.every.every_server.domain.repository.BambooReplyRepo;
import com.every.every_server.domain.vo.bamboo.post.BambooPostVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BambooServiceImpl implements BambooService {
    @Autowired
    private BambooPostRepo bambooPostRepo;
    @Autowired
    private BambooReplyRepo bambooReplyRepo;

    @Override
    public List<BambooPostVO> getBambooPosts(Integer memberIdx) {
        return null;
    }
}
