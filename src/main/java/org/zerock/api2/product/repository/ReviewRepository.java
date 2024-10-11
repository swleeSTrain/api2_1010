package org.zerock.api2.product.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.api2.product.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
