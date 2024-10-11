package org.zerock.api2.product.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.api2.product.domain.Review;
import org.zerock.api2.product.repository.search.ReviewSearch;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewSearch {

}
