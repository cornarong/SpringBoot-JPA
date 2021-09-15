package com.jpabook.jpashop.Form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BookForm {

    private Long id; // 상품아이디

    // 상품의 공통 속성
    private String name; // 상품명
    private int price; // 주문 가격
    private int stockQuantity; // 주문 수량

    // 책의 개인 속성
    private String author; // 저자
    private String isbn; //ISBN
}
