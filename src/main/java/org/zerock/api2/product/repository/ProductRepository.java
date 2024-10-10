package org.zerock.api2.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.api2.product.domain.Product;
import org.zerock.api2.product.repository.search.ProductSearch;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductSearch {
}
