package org.zerock.api2.product.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.api2.product.domain.Product;
import org.zerock.api2.product.domain.ProductStatus;

public interface ProductSearch {

    Page<Product> list1(Pageable pageable);

    Page<Product> list2(String keyword, ProductStatus status, Pageable pageable);

}
