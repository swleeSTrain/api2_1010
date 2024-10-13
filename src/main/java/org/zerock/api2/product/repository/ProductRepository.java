package org.zerock.api2.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.api2.product.domain.Product;
import org.zerock.api2.product.dto.ProductReadDTO;
import org.zerock.api2.product.repository.search.ProductSearch;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductSearch {

    @Query("select " +
            "new org.zerock.api2.product.dto.ProductReadDTO(p.pno,p.pname,p.price, p.status)" +
            " from Product  p where p.pno = :pno")
    Optional<ProductReadDTO> read(@Param("pno") Long pno);

    @EntityGraph(attributePaths = {"tags"})
    @Query("select p from Product p where p.pno = :pno")
    Optional<Product> read2(@Param("pno") Long pno);

    @EntityGraph(attributePaths = {"tags"})
    @Query("select p from Product p")
    Page<Product> listOld(Pageable pageable);

}
