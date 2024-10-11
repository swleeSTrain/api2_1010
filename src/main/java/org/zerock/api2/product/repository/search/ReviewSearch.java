package org.zerock.api2.product.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.api2.product.domain.Review;

public interface ReviewSearch {

    Page<Review> listByProduct(Long pno, Pageable pageable);


}
