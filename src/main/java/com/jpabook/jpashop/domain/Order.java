package com.jpabook.jpashop.domain;

import lombok.Data;
import org.aspectj.weaver.ast.Or;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // OneToOne의 경우는 연관관계 주인을 아무나 가져도 된다.
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문 시간 (hibernate 지원)

    // enum 타입은 @Enumerated 꼭 넣어야 한다. 기본은 ORDINAL으로 DB에 숫자로 들어간다. 중간에 값이 추가되면 문제가 발생한다.(순서밀림) 꼭 STRING으로 쓰자.
    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

    //== 양방향 연관관계 메서드 ==// : 양방향 관계 일 때 셋팅해주면 한쪽에서 처리가 가능하도록 해준다.
    // ex) member과 order의 연관관계 메서드, setMember하나 order처리와 동시에 내부에서 member도 처리해준다.
    public void setMember(Member member) {
        this.member = member;
        member.getOrder().add(this);
    }

    public void addOrderitem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //== 생성 메서드 ==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems){
            order.addOrderitem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //== 비즈니스 로직 ==//
    /**
     * 주문 취소
     */
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능 합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }
}
