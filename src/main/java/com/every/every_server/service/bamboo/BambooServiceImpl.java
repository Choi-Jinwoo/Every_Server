package com.every.every_server.service.bamboo;

import com.every.every_server.domain.entity.BambooPost;
import com.every.every_server.domain.entity.Member;
import com.every.every_server.domain.entity.Student;
import com.every.every_server.domain.repository.BambooPostRepo;
import com.every.every_server.domain.repository.BambooReplyRepo;
import com.every.every_server.domain.repository.MemberRepo;
import com.every.every_server.domain.repository.StudentRepo;
import com.every.every_server.domain.vo.bamboo.post.BambooPostVO;
import com.every.every_server.domain.vo.bamboo.post.BambooWritePostVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BambooServiceImpl implements BambooService {
    @Autowired
    private BambooPostRepo bambooPostRepo;
    @Autowired
    private BambooReplyRepo bambooReplyRepo;
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private MemberRepo memberRepo;

    @Override
    public List<BambooPostVO> getBambooPosts(Integer memberIdx) {
        Optional<Member> member = memberRepo.findById(memberIdx);
        if (!member.isPresent()) {
            throw  new HttpClientErrorException(HttpStatus.NOT_FOUND, "회원 없음.");
        }

        Student student = studentRepo.findByMember(member.get());
        if (student == null) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "권한 없음.");
        }

        List<BambooPost> rawPostList = bambooPostRepo.findAll();
        List<BambooPostVO> postList = new ArrayList<>();

        ModelMapper modelMapper = new ModelMapper();
        for (int i = 0; i < rawPostList.size(); i++) {
            BambooPostVO post = modelMapper.map(rawPostList.get(i), BambooPostVO.class);
            post.setCreatedAt(rawPostList.get(i).getCreatedAt().toString());
            postList.add(post);
        }

        return postList;
    }

    @Override
    public boolean writeBambooPost(Integer memberIdx, BambooWritePostVO bambooWritePostVO) {
        Optional<Member> member = memberRepo.findById(memberIdx);
        if (!member.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "없는 회원.");
        }

        Student student = studentRepo.findByMember(member.get());
        if (student == null) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "권한 없음.");
        }

        ModelMapper modelMapper = new ModelMapper();
        BambooPost post = modelMapper.map(bambooWritePostVO, BambooPost.class);
        post.setStudent(student);

        bambooPostRepo.save(post);
        return  true;
    }
}
