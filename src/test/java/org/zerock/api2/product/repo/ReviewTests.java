package org.zerock.api2.product.repo;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.api2.product.domain.ContentImage;
import org.zerock.api2.product.domain.Product;
import org.zerock.api2.product.domain.Review;
import org.zerock.api2.product.repository.ReviewRepository;

import java.util.HashSet;
import java.util.Set;

@Log4j2
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReviewTests {
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @Transactional
    @Commit
    public void testInsert(){
        Product product = Product.builder().pno(15L).build();//상품번호 없을시 외래키 위반 조심

        Review review = Review.builder()
                .reviewer("r1")
                .score(5)
                .product(product)
                .build();

        review.addFile("AAA.jpg");
        review.addFile("BAA.jpg");
        review.addFile("CAA.jpg");

        reviewRepository.save(review);
    }
}
