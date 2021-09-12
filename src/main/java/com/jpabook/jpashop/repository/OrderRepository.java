package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    // 주문 저장
    public void save(Order order){
        em.persist(order);
    }
    // 주문 단건 조회
    public Order findOne(Long orderId){
        Order order = em.find(Order.class, orderId);
        return order;
    }

    /*public List<Order> findAll(OrderSearch orderSearch){
    }*/



}
