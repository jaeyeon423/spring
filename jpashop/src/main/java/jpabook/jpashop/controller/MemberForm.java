package jpabook.jpashop.controller;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MemberForm {

    @NotEmpty(message = "")
    private String name;
    private String city;
    private String street;
    private String zipcode;


}
