package com.ll.sbjpa.domain.article.articleTag.repository;

import com.ll.sbjpa.domain.article.articleTag.entity.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {
    List<ArticleTag> findByAuthorId(long authorId);

    List<ArticleTag> findByAuthor_username(String username);
}
