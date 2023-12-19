package com.ll.sbjpa.domain.article.articleComment.entity;

import com.ll.sbjpa.domain.article.article.entity.Article;
import com.ll.sbjpa.domain.member.member.entity.Member;
import com.ll.sbjpa.global.jpa.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@SuperBuilder
@Getter
@Setter
@ToString(callSuper = true)
public class ArticleComment extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;
    private String body;

}
