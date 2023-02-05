package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        public void dbInit1(){
            Member member = createMember("memberA","서울","1","1111");
            em.persist(member);

            Book book1 = createBook("JPA1", 10000, 10);

            Book book2 = createBook("JPA2", 20000, 20);

            em.persist(book1);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = getDelivery(member);

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }




        public void dbInit2(){
            Member member = createMember("memberB","진주","2","2222");
            em.persist(member);

            Book book1 = createBook("JPA1 Book", 20000, 200);

            Book book2 = createBook("JPA2 Book", 40000, 300);

            em.persist(book1);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            Delivery delivery = getDelivery(member);

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private Delivery getDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street,zipcode));
            return member;
        }

        private Book createBook(String name, int price, int cnt) {
            Book book1 = new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(cnt);
            return book1;
        }
    }
}
