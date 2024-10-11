package org.zerock.api2.product.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.api2.product.domain.Product;
import org.zerock.api2.product.domain.Review;
import org.zerock.api2.product.repository.search.ReviewSearch;


public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewSearch {

    Page<Review> findByProduct(Product product, Pageable pageable);

    //동적으로 처리할일 없으면 이게 더 편함
    @Query("select r.rno, ri.filename, ri.ord from Review r left join r.images ri " +
            "where r.product.pno = :pno " +
            "and (ri is null or ri.ord = 0) ")
    Page<Object[]> listByProductQuery(@Param("pno") Long pno, Pageable pageable);

}
