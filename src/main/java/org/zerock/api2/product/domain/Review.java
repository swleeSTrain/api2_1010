package org.zerock.api2.product.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@ToString(exclude = "product")
@Table(name = "tbl_review", indexes = {
        @Index(name="idx_review_product", columnList = "product_pno")
})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String reviewer;

    private int score;

    @ManyToOne(fetch = FetchType.LAZY)//누군가 product에 접근하기 전까지 조회안함
    private Product product;

    @ElementCollection
    @CollectionTable(name = "tbl_review_img")
    @Builder.Default
    @Getter
    private Set<ContentImage> images = new HashSet<ContentImage>();

    //ord번호 번호 겹치는거 방지하기
    public void addFile(String filename) {
        ContentImage image = new ContentImage(images.size(), filename);
        images.add(image);
    }

    public void changeImages(Set<ContentImage> images) {
        this.images = images;
    }
}
