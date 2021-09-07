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

    @OneToOne // OneToOne의 경우는 연관관계 주인을 아무나 가져도 된다.
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문 시간 (hibernate 지원)

    // enum 타입은 @Enumerated 꼭 넣어야 한다. 기본은 ORDINAL으로 DB에 숫자로 들어간다. 중간에 값이 추가되면 문제가 발생한다.(순서밀림) 꼭 STRING으로 쓰자.
    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]


}
