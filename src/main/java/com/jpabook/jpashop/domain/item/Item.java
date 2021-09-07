package com.jpabook.jpashop.domain.item;

import com.jpabook.jpashop.domain.Category;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Data
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    // 공통속성 상속관계 전략. 부모클래스
    private String name; // 이름
    private int price; // 가격
    private int stockQuantity; // 수량

    @ManyToMany(mappedBy = "items") // 다대다 테스트용
    private List<Category> categories = new ArrayList<>();


}
