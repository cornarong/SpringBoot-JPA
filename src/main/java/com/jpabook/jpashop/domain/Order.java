package com.jpabook.jpashop.domain;

import lombok.Data;

import javax.persistence.*;

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


}
