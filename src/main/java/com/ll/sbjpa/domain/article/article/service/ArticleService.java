package com.ll.sbjpa.domain.article.article.service;

import com.ll.sbjpa.domain.article.article.entity.Article;
import com.ll.sbjpa.domain.article.article.repository.ArticleRepository;
import com.ll.sbjpa.domain.article.articleComment.entity.ArticleComment;
import com.ll.sbjpa.domain.member.member.entity.Member;
import com.ll.sbjpa.global.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional
    public RsData<Article> write(long authorId, String title, String body) {
        Article article = Article.builder()
                .author(Member.builder().id(authorId).build())
                .title(title)
                .body(body)
                .build();

        articleRepository.save(article);

        return RsData.of("200", "%d번 게시글이 작성되었습니다.".formatted(article.getId()), article);
    }

    public Optional<Article> findById(long id) {
        return articleRepository.findById(id);
    }

    @Transactional
    public void modify(Article article, String title, String body) {
        article.setTitle(title);
        article.setBody(body);
    }

    @Transactional
    public void modifyComment(ArticleComment comment, String s) {
        comment.setBody(s);
    }

    public List<Article> findAll() {
        return articleRepository.findByOrderByIdDesc();
    }

    public Page<Article> search(List<String> kwTypes, String kw, Pageable pageable) {
        return articleRepository.search(kwTypes, kw, pageable);
    }
}