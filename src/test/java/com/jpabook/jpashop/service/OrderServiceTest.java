package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderStatus;
import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.exception.NotEnoughStockException;
import com.jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.util.AssertionErrors;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    // 파라미터 밖으로 빼기 Ctrl + Alt + P
    private Item createItem(String name, int price, int stockQuantity) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("memberA");
        member.setAddress(new Address("시흥시 호반 베르디움 더 프라임","배미골길23","1507동 1203호"));
        em.persist(member);
        return member;
    }

    @Test
    void 상품주문() throws Exception {
        //Given
        Member member = createMember();
        Item book = createItem("JPA", 10000, 10);

        int orderCount = 2;
        //When
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //Then
        Order getOrder = orderRepository.findOne(orderId);

        Assertions.assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        Assertions.assertEquals(getOrder.getOrderItems().size(), 1, "주문한 상품 종류 수가 정확해야 한다.");
        Assertions.assertEquals(getOrder.getTotalPrice(), 10000 * 2, "주문 가격은 가격 * 수량이다.");
        Assertions.assertEquals(book.getStockQuantity(), 8, "주문 수량만큼 재고가 줄어야 한다.");

    }

    @Test
    void 주문취소() throws Exception {
        //Given
        Member member = createMember();
        Item book = createItem("JPA", 10000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //When
        orderService.cancelOrder(orderId);

        //Then
        Order order = orderRepository.findOne(orderId);
        Assertions.assertEquals(order.getStatus(), OrderStatus.CANCEL, "주문 취소시 상태는 CANCEL 이다.");
        Assertions.assertEquals(book.getStockQuantity(), 10, "주문 취소된 상품은 재고가 다시 증가해야 한다.");
    }

    @Test
    void 상품주문_재고수량초과() throws Exception {
        //Given
        Member member = createMember();
        Item book = createItem("JPA", 10000, 10);
        int orderCount = 11;

        //When
        try {
            orderService.order(member.getId(), book.getId(), orderCount);
        }catch (NotEnoughStockException e){
            return;
        }

        //Then
        AssertionErrors.fail("재고 수량 부족 예외가 발생해야 한다. 여기까지 오면 안된다.");
    }
}