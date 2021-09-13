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

    // orderSearcd의 파라미터의 따라서 쿼리가 달라지는 동적쿼리로 구현.
    // -> 동적쿼리는 jpql로 구현하기에는 코드도 복잡할 뿐더러 유지보수도 헬!이기에 querydsl을 사용한다.
    // 강의 마지막 시간에 querydsl로 구현할 것이다.
    public List<Order> findAll(OrderSearch orderSearch){
        List<Order> resultList = em.createQuery("select o from Order o join o.member m" +
                " where o.status = :status " +
                " and m.name like :name", Order.class)
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
                .setMaxResults(1000) // 최대 1000건 까지만 조회.
                .getResultList();
        return resultList;

    }



}
