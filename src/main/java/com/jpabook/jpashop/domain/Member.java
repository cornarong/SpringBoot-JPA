package com.jpabook.jpashop.domain;


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") // 연관관계의 조회만 할 수 있는 거울 역할
    private List<Order> order = new ArrayList<>();

}
