package com.ll.sbjpa.global.initProd;

import com.ll.sbjpa.domain.article.article.entity.Article;
import com.ll.sbjpa.domain.article.article.service.ArticleService;
import com.ll.sbjpa.domain.member.member.entity.Member;
import com.ll.sbjpa.domain.member.member.service.MemberService;
import jakarta.transaction.Transactional;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.stream.IntStream;

@Profile("!prod")
@Configuration
public class NotProd {// 샘플 데이터 생성
    @Bean
    public ApplicationRunner initNotProdData(
            MemberService memberService,
            ArticleService articleService
    ) {
        return new ApplicationRunner() {
            @Transactional
            @Override
            public void run(ApplicationArguments args) throws Exception {
                if(memberService.count() > 0 ) return;
                Member member1 = memberService.join("user1", "1234").getData();
                Member member2 = memberService.join("user2", "1234").getData();

                Article article1 = articleService.write(member1.getId(), "제목1", "내용1").getData();
                Article article2 = articleService.write(member1.getId(), "제목2", "내용2").getData();

                Article article3 = articleService.write(member2.getId(), "제목3", "내용3").getData();
                Article article4 = articleService.write(member2.getId(), "제목4", "내용4").getData();

                article1.addComment(member2, "댓글1-1");
                article1.addComment(member2, "댓글1-2");

                article2.addComment(member2, "댓글2-1");
                article2.addComment(member2, "댓글2-2");

                article3.addComment(member1, "댓글3-1");
                article3.addComment(member1, "댓글3-2");
                article3.addComment(member1, "댓글3-3");
                article3.addComment(member1, "댓글3-4");
                article3.addComment(member1, "댓글3-5");
                article3.addComment(member1, "댓글3-6");
                article3.addComment(member1, "댓글3-7");
                article3.addComment(member1, "댓글3-8");
                article3.addComment(member1, "댓글3-9");
                article3.addComment(member1, "댓글3-10");

                article4.addComment(member1, "댓글4-1");
                article4.addComment(member1, "댓글4-2");

                article1.addTag("자바");
                article1.addTag("백엔드");
                article2.addTag("프레임워크");
                article2.addTag("스프링부트");
                article4.addTag("자바");
                article4.addTag("스프링부트");

                IntStream.rangeClosed(5, 120).forEach(
                        i -> {
                            String title = "제목" + i;
                            String body = "내용" + i;
                            articleService.write(member2.getId(), title, body);
                        }
                );
            }
        };
    }
}