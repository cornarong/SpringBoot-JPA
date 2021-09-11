package com.jpabook.jpashop.domain;

import com.jpabook.jpashop.domain.item.Item;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격
    private int count; // 주문 수량

    //== 비즈니스 로직 ==//
    public void cancel() { // 재고수량을 원복 해준다.
            getItem().addStock(count);
    }
}
