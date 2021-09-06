package com.jpabook.jpashop.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id @GeneratedValue
    @Column(name = "oder_id")
    private Long id;

    // 연관관계의 주인, 반대쪽은 mappedBy 설정 해준다.
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    private Delivery delivery;

    private LocalDateTime orderDate; // 주문 시간 (hibernate 지원)

    private OrderStatus status; // 주문상태 [ORDER, CANCEL]


}
