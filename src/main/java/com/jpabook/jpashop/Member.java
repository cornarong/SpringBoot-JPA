package com.jpabook.jpashop;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Member {

    // 식별자 = @Id
    // @GenerateValue로 자동 생성
    @Id
    @GeneratedValue
    private Long id;

    private String username;

}
