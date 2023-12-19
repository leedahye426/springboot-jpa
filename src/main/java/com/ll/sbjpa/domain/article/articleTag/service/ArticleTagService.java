package com.ll.sbjpa.domain.article.articleTag.service;

import com.ll.sbjpa.domain.article.articleTag.entity.ArticleTag;
import com.ll.sbjpa.domain.article.articleTag.repository.ArticleTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleTagService {
    private final ArticleTagRepository articleTagRepository;

    public List<ArticleTag> findByAuthorId(long authorId) {
        return articleTagRepository.findByAuthorId(authorId);
    }

    public List<ArticleTag> findByAuthorUsername(String username) {
        return articleTagRepository.findByAuthor_username(username);
    }
}
