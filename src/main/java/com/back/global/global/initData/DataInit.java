package com.back.global.global.initData;

import com.back.boundedContext.member.app.MemberFacade;
import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.post.app.PostFacade;
import com.back.boundedContext.post.domain.PostMember;
import com.back.global.global.RsData.RsData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@Slf4j
public class DataInit {
    private final DataInit self;
    private final MemberFacade memberFacade;
    private final PostFacade postFacade;

    public DataInit(
            @Lazy DataInit self,
            MemberFacade memberFacade,
            PostFacade postFacade
    ) {
        this.self = self;
        this.memberFacade = memberFacade;
        this.postFacade = postFacade;
    }

    @Bean
    public ApplicationRunner baseInitDataRunner() {
        return args -> {
            self.makeBaseMembers();
            self.makeBasePosts();
            self.makeBasePostComments();
        };
    }

    @Transactional
    public void makeBaseMembers() {
        if (memberFacade.count() > 0) return;

        Member systemMember = memberFacade.join("system", "1234", "시스템").getData();
        Member holdingMember = memberFacade.join("holding", "1234", "홀딩").getData();
        Member adminMember = memberFacade.join("admin", "1234", "관리자").getData();
        Member user1Member = memberFacade.join("user1", "1234", "유저1").getData();
        Member user2Member = memberFacade.join("user2", "1234", "유저2").getData();
        Member user3Member = memberFacade.join("user3", "1234", "유저3").getData();
    }

    @Transactional
    public void makeBasePosts() {
        if (postFacade.count() > 0) return;

        Member user1Member = memberFacade.findByUsername("user1").get();
        Member user2Member = memberFacade.findByUsername("user2").get();
        Member user3Member = memberFacade.findByUsername("user3").get();

        PostMember user1 = postFacade.findPostMember (user1Member.getUsername()).get();
        PostMember user2 = postFacade.findPostMember (user2Member.getUsername()).get();
        PostMember user3 = postFacade.findPostMember (user3Member.getUsername()).get();

        RsData<Post> post1 = postFacade.write(user1, "제목1", "내용1"); log.debug(post1.getMsg());
        RsData<Post> post2 = postFacade.write(user1, "제목2", "내용2"); log.debug(post2.getMsg());
        RsData<Post> post3 = postFacade.write(user1, "제목3", "내용3"); log.debug(post3.getMsg());
        RsData<Post> post4 = postFacade.write(user2, "제목4", "내용4"); log.debug(post4.getMsg());
        RsData<Post> post5 = postFacade.write(user2, "제목5", "내용5"); log.debug(post5.getMsg());
        RsData<Post> post6 = postFacade.write(user3, "제목6", "내용6"); log.debug(post6.getMsg());
    }

    @Transactional
    public void makeBasePostComments() {
        Post post1 = postFacade.findById(1).get();
        Post post2 = postFacade.findById(2).get();
        Post post3 = postFacade.findById(3).get();
        Post post4 = postFacade.findById(4).get();
        Post post5 = postFacade.findById(5).get();
        Post post6 = postFacade.findById(6).get();

        PostMember user1Member = postFacade.findPostMember("user1").get();
        PostMember user2Member = postFacade.findPostMember("user2").get();
        PostMember user3Member = postFacade.findPostMember("user3").get();

        if (post1.hasComments()) return;

        post1.addComment(user1Member, "댓글1");
        post1.addComment(user2Member, "댓글2");
        post1.addComment(user3Member, "댓글3");

        post2.addComment(user2Member, "댓글4");
        post2.addComment(user2Member, "댓글5");

        post3.addComment(user3Member, "댓글6");
        post3.addComment(user3Member, "댓글7");

        post4.addComment(user1Member, "댓글8");
        post5.addComment(user1Member, "댓글9");
    }
}