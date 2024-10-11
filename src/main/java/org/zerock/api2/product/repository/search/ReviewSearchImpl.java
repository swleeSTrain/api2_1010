package org.zerock.api2.product.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.api2.product.domain.QContentImage;
import org.zerock.api2.product.domain.QReview;
import org.zerock.api2.product.domain.Review;

import java.util.List;

@Log4j2
public class ReviewSearchImpl extends QuerydslRepositorySupport implements ReviewSearch {

    public ReviewSearchImpl() {
        super(Review.class);
    }

    @Override
    public Page<Review> listByProduct(Long pno, Pageable pageable) {

        QReview review = QReview.review;
        QContentImage image = QContentImage.contentImage;

        JPQLQuery<Review> query = from(review);

        query.leftJoin(review.images, image); //leftJoin image
        query.where(review.product.pno.eq(pno));
        //query.where(image.ord.eq(0));

        //동적처리로 하는경우
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.or(image.isNull());
        booleanBuilder.or(image.ord.eq(0));

        query.where(booleanBuilder);

        this.getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<Tuple> tupleQuery =
                // review.images는 테이블에서 뽑아서 한번더 이미지 테이블에서 호출함  leftJoin image을 사용해야 새로 더 호출하는 불상사를 막음
                query.select(review.rno, review.score, image);

        List<Tuple> tupleList = tupleQuery.fetch();

        tupleList.forEach(tuple -> {
            log.info(tuple);
        });

        return null;
//        List<Review> list = query.fetch();
//        long total = query.fetchCount();
//
//        return new PageImpl<>(list, pageable, total);
    }
}
