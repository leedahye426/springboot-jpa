package com.ll.sbjpa.domain.article.article.entity;

import com.ll.sbjpa.domain.article.articleComment.entity.ArticleComment;
import com.ll.sbjpa.domain.article.articleTag.entity.ArticleTag;
import com.ll.sbjpa.domain.member.member.entity.Member;
import com.ll.sbjpa.global.jpa.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@SuperBuilder
@Getter
@Setter
@ToString(callSuper = true)
public class Article extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;
    private String title;
    private String body;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<ArticleComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<ArticleTag> tags = new ArrayList<>();

    public void addComment(Member commentAuthor, String commentBody) {
        ArticleComment comment = ArticleComment
                .builder()
                .article(this)
                .author(commentAuthor)
                .body(commentBody)
                .build();

        comments.add(comment);
    }

    public void removeComment(ArticleComment lastComment) {
        comments.remove(lastComment);
    }

    public void addTag(String tagContent) {
        ArticleTag tag = ArticleTag.builder()
                .article(this)
                .author(author)
                .content(tagContent)
                .build();

        tags.add(tag);
    }

    public String getTagsStr() {
        String tagsStr =  tags
                            .stream()
                            .map(ArticleTag :: getContent)
                            .collect(Collectors.joining(" #"));
        if(tagsStr.isBlank()) {
            return "";
        }
        return "#" + tagsStr;
    }
}
