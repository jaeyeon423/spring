package jpabook.jpashop.domain;

import jpabook.jpashop.service.OrderService;
import lombok.Data;

@Data
public class OrderSearch {

    private String MemberName; // 회원 이름
    private OrderStatus orderStatus; // 주문 상태


}
