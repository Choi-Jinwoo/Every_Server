package com.every.every_server.service.bamboo;

import com.every.every_server.domain.entity.BambooPost;
import com.every.every_server.domain.entity.BambooReply;
import com.every.every_server.domain.entity.Student;
import com.every.every_server.domain.repository.BambooPostRepo;
import com.every.every_server.domain.repository.BambooReplyRepo;
import com.every.every_server.domain.repository.MemberRepo;
import com.every.every_server.domain.repository.StudentRepo;
import com.every.every_server.domain.vo.bamboo.post.BambooPostVO;
import com.every.every_server.domain.vo.bamboo.post.BambooWritePostVO;
import com.every.every_server.domain.vo.bamboo.reply.BambooModifyReplyVO;
import com.every.every_server.domain.vo.bamboo.reply.BambooReplyVO;
import com.every.every_server.domain.vo.bamboo.reply.BambooWriteReplyVO;
import com.every.every_server.service.member.MemberServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

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
    private MemberServiceImpl memberService;

    @Override
    public List<BambooPostVO> getBambooPosts(Integer memberIdx) {
        try {
            Student student = memberService.getStudentByMemberIdx(memberIdx);
            if (student == null) {
                throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "권한 없음.");
            }
        } catch (Exception e) {
            throw e;
        }

        List<BambooPost> rawPostList = bambooPostRepo.findAllByOrderByCreatedAtDesc();
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
    public BambooPostVO getBambooPost(Integer memberIdx, Integer postIdx) {
        try {
            Student student = memberService.getStudentByMemberIdx(memberIdx);
            if (student == null) {
                throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "권한 없음.");
            }
        } catch (Exception e) {
            throw e;
        }

        Optional<BambooPost> rawPost = bambooPostRepo.findById(postIdx);
        if (!rawPost.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "게시글 없음.");
        }

        ModelMapper modelMapper = new ModelMapper();
        BambooPostVO post = modelMapper.map(rawPost.get(), BambooPostVO.class);
        return post;
    }

    @Override
    public boolean writeBambooPost(Integer memberIdx, BambooWritePostVO bambooWritePostVO) {
        Student student;
        try {
            student = memberService.getStudentByMemberIdx(memberIdx);
            if (student == null) {
                throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "권한 없음.");
            }
        } catch (Exception e) {
            throw e;
        }

        ModelMapper modelMapper = new ModelMapper();
        BambooPost post = modelMapper.map(bambooWritePostVO, BambooPost.class);
        post.setStudent(student);

        bambooPostRepo.save(post);
        return  true;
    }

    @Override
    public List<BambooReplyVO> getBambooReplies(Integer memberIdx, Integer postIdx) {
        Student student;
        try {
            student = memberService.getStudentByMemberIdx(memberIdx);
            if (student == null) {
                throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "권한 없음.");
            }
        } catch (Exception e) {
            throw e;
        }

        Optional<BambooPost> post = bambooPostRepo.findById(postIdx);
        if (!post.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "게시글 없음.");
        }

        List<BambooReply> rawReplyList = bambooReplyRepo.findAllByBambooPost(post.get());
        List<BambooReplyVO> replyList = new ArrayList<>();

        ModelMapper modelMapper = new ModelMapper();
        for (int i = 0; i < rawReplyList.size(); i++) {
            BambooReplyVO reply = modelMapper.map(rawReplyList.get(i), BambooReplyVO.class);
            reply.setCreatedAt(rawReplyList.get(i).getCreatedAt().toString());
            reply.setStudentIdx(rawReplyList.get(i).getStudent().getIdx());
            replyList.add(reply);
        }

        return replyList;
    }

    @Override
    public boolean writeBambooReply(Integer memberIdx, BambooWriteReplyVO bambooWriteReplyVO) {
        Student student;
        try {
            student = memberService.getStudentByMemberIdx(memberIdx);
            if (student == null) {
                throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "권한 없음.");
            }
        } catch (Exception e) {
            throw e;
        }

        Optional<BambooPost> post = bambooPostRepo.findById(bambooWriteReplyVO.getPost());
        if (!post.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "게시글 없음.");
        }

        BambooReply reply = new BambooReply();
        reply.setContent(bambooWriteReplyVO.getContent());
        reply.setBambooPost(post.get());
        reply.setStudent(student);
bambooReplyRepo.save(reply);
        return true;
    }

    @Override
    public boolean modifyBambooReply(Integer memberIdx, Integer idx, BambooModifyReplyVO bambooModifyReplyVO) {
        Student student;
        try {
            student = memberService.getStudentByMemberIdx(memberIdx);
            if (student == null) {
                throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "권한 없음.");
            }
        } catch (Exception e) {
            throw e;
        }

        Optional<BambooReply> reply = bambooReplyRepo.findById(idx);
        if (!reply.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "댓글 없음.");
        }

        if (reply.get().getStudent().getIdx() != student.getIdx()) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "권한 없음.");
        }

        reply.get().setContent(bambooModifyReplyVO.getContent());
        bambooReplyRepo.save(reply.get());
        return true;
    }

    @Override
    public boolean deleteBambooReply(Integer memberIdx, Integer idx) {
        Student student;
        try {
            student = memberService.getStudentByMemberIdx(memberIdx);
            if (student == null) {
                throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "권한 없음.");
            }
        } catch (Exception e) {
            throw e;
        }

        Optional<BambooReply> reply = bambooReplyRepo.findById(idx);
        if (!reply.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "댓글 없음.");
        }

        if (reply.get().getStudent().getIdx() != student.getIdx()) {
            throw  new HttpClientErrorException(HttpStatus.FORBIDDEN, "권한 없음.");
        }

        bambooReplyRepo.delete(reply.get());
        return true;
    }

}
