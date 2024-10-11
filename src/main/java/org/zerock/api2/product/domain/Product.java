package org.zerock.api2.product.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "tags")
@Table(name="tbl_product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pno;

    private String pname;

    private int price;

    private ProductStatus status;

    //ElementCollection 주의사항!! 한번에 값 바꾸면 에러남 tags가 가르키는 HashSet은 불변임 바꾸면 에러남
    @ElementCollection //그자체가 엔티티가 아니라서 PK가 없음 하지못하는게 많음 테이블 이름도 구림 단독으로 뭔가 하지 않음
    @CollectionTable(name = "tbl_product_tag")//구린 이름 원하는 테이블 이름으로
    @Builder.Default
    @BatchSize(size = 100) // in 조건 자동으로 처리
    private Set<String> tags = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "tbl_product_img")
    @Builder.Default
    private Set<ContentImage> images = new HashSet<ContentImage>();

    public void addTag(String tag) {
        tags.add(tag);
    }
    public void removeTag(String tag) {
        tags.remove(tag);
    }
    public void clearTags() {
        tags.clear();
    }
}
