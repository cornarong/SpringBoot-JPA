package com.jpabook.jpashop.controller;

import lombok.Data;

@Data
public class MemberForm {

//    @NotEmpty
    private String name;

    private String city;
    private String street;
    private String zipcode;
}
