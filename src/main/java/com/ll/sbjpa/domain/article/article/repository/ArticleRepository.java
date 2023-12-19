package com.ll.sbjpa.domain.article.article.repository;

import com.ll.sbjpa.domain.article.article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom {
    List<Article> findByOrderByIdDesc();
    Page<Article> findAll(Pageable pageable);
}