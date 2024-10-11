package org.zerock.api2.product.repository.search;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.api2.common.dto.PageRequestDTO;
import org.zerock.api2.common.dto.PageResponseDTO;
import org.zerock.api2.product.domain.Product;
import org.zerock.api2.product.domain.ProductStatus;
import org.zerock.api2.product.domain.QProduct;
import org.zerock.api2.product.domain.QReview;
import org.zerock.api2.product.dto.ProductListDTO;

import java.util.List;

@Log4j2
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {

    public ProductSearchImpl() {
        super(Product.class);
    }

    @Override
    public Page<Product> list1(Pageable pageable) {

        QProduct product = QProduct.product;
        JPQLQuery<Product> query = from(product);
        query.leftJoin(product.tags).fetchJoin();//이거 있으면 Batchsize작동안함
        query.where(product.status.eq(ProductStatus.SALE));
        query.where(product.pno.gt(0L));

        this.getQuerydsl().applyPagination(pageable, query);//paging

        List<Product> list = query.fetch();
        long total = query.fetchCount();

        return new PageImpl<>(list, pageable, total);
    }

    @Override
    public Page<Product> list2(String keyword, ProductStatus status, Pageable pageable) {
        QProduct product = QProduct.product;
        JPQLQuery<Product> query = from(product);
        query.where(product.status.eq(ProductStatus.SALE));
        query.where(product.pno.gt(0L));

        if(keyword != null) {
            query.where(product.pname.like("%" + keyword + "%"));
        }

        if(status != null) {
            query.where(product.status.eq(status));
        }

        this.getQuerydsl().applyPagination(pageable, query);//paging

        List<Product> list = query.fetch();
        long total = query.fetchCount();

        return new PageImpl<>(list, pageable, total);
    }

    @Override
    public Page<Product> listWithReplyCount(Pageable pageable) {

        QProduct product = QProduct.product;
        QReview review = QReview.review;

        JPQLQuery<Product> query = from(product);
        query.leftJoin(review).on(review.product.eq(product));
        query.groupBy(product);

        this.getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<ProductListDTO> dtojpqlQuery = query.select(
                Projections.bean(ProductListDTO.class,
                        product.pno,
                        product.pname,
                        product.price,
                        review.count().as("reviewCnt"),
                        review.score.avg().coalesce(0.0).as("avgScore")
                )
        );

        log.info(dtojpqlQuery);

        java.util.List<ProductListDTO> dtoList = dtojpqlQuery.fetch();

        dtoList.forEach(log::info);

        return null;
    }

    @Override
    public PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage()-1,
                pageRequestDTO.getSize(),
                Sort.by("pno").descending()
        );

        QProduct product = QProduct.product;
        QReview review = QReview.review;

        JPQLQuery<Product> query = from(product);
        query.leftJoin(review).on(review.product.eq(product));
        query.groupBy(product);

        this.getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<ProductListDTO> dtojpqlQuery = query.select(
                Projections.bean(ProductListDTO.class,
                        product.pno,
                        product.pname,
                        product.price,
                        review.count().as("reviewCnt"),
                        review.score.avg().coalesce(0.0).as("avgScore")
                )
        );

        log.info(dtojpqlQuery);

        java.util.List<ProductListDTO> dtoList = dtojpqlQuery.fetch();

        dtoList.forEach(log::info);

        long total = dtojpqlQuery.fetchCount();

        return PageResponseDTO.<ProductListDTO>withAll()
                .dtoList(dtoList)  // 변환된 DTO 리스트를 설정
                .pageRequestDTO(pageRequestDTO)  // 요청받은 페이지 관련 정보 설정
                .totalCount(total)      // 전체 개수를 설정
                .build();
    }
}
