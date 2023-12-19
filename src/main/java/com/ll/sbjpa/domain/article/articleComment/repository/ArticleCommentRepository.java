package com.ll.sbjpa.domain.article.articleComment.repository;

import com.ll.sbjpa.domain.article.articleComment.entity.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
    public List<ArticleComment> findByAuthorId(long authorId);
}
