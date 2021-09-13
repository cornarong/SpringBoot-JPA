package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.OrderStatus;
import lombok.Data;

@Data
public class OrderSearch {

    private String memberName; // 회원 이름
    private OrderStatus orderStatus; // 주문상태[ORDER, CANCEL]
}
