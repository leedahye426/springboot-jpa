package com.ll.sbjpa.domain.article.article.repository;

import com.ll.sbjpa.domain.article.article.entity.Article;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.ll.sbjpa.domain.article.article.entity.QArticle.article;


@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Article> search(List<String> kwTypes, String kw, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        if (!kw.isBlank()) {
            // 기존의 조건을 리스트에 담습니다.
            List<BooleanExpression> conditions = new ArrayList<>();

            if (kwTypes.contains("authorUsername")) {
                conditions.add(article.author.username.containsIgnoreCase(kw));
            }

            if (kwTypes.contains("title")) {
                conditions.add(article.title.containsIgnoreCase(kw));
            }

            if (kwTypes.contains("body")) {
                conditions.add(article.body.containsIgnoreCase(kw));
            }

            if (kwTypes.contains("tagContent")) {
                conditions.add(article.tags.any().content.eq(kw));
            }

            if (kwTypes.contains("commentAuthorUsername")) {
                conditions.add(article.comments.any().author.username.containsIgnoreCase(kw));
            }

            if (kwTypes.contains("commentBody")) {
                conditions.add(article.comments.any().body.containsIgnoreCase(kw));
            }

            // 조건 리스트를 or 조건으로 결합
            BooleanExpression combinedCondition = conditions.stream()
                    .reduce(BooleanExpression::or)
                    .orElse(null);

            // 최종적으로 생성된 조건을 쿼리에 적용
            if (combinedCondition != null) {
                builder.and(combinedCondition);
            }
        }

        JPAQuery<Article> articlesQuery = jpaQueryFactory
                .select(article)
                .from(article)
                .where(builder);

        // 정렬 조건 적용
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(article.getType(), article.getMetadata());
            articlesQuery.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }

        // 페이징 조건 적용
        articlesQuery.offset(pageable.getOffset()).limit(pageable.getPageSize());

        // 총 결과 수를 가져오기 위한 쿼리 작성
        JPAQuery<Long> totalQuery = jpaQueryFactory
                .select(article.count())
                .from(article)
                .where(builder);

        // 페이징된 결과와 총 결과 수를 가져와서 Page 객체로 변환
        return PageableExecutionUtils.getPage(articlesQuery.fetch(), pageable, totalQuery::fetchOne);
    }
}