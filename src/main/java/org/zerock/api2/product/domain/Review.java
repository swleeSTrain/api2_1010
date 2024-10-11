package org.zerock.api2.product.domain;


import jakarta.persistence.*;
import lombok.ToString;

@Entity
@ToString(exclude = "product")
@Table(name = "tbl_review", indexes = {
        @Index(name="idx_review_product", columnList = "product_pno")
})
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String reviewer;

    private int score;

    @ManyToOne(fetch = FetchType.LAZY)//누군가 product에 접근하기 전까지 조회안함
    private Product product;


}
