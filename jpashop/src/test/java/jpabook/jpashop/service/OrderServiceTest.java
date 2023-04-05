package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.order.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void order(){

        Member member = createMember();
        Item item = createBook("jaeyeon jpa", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        Order getOrder = orderRepository.findOne(orderId);

        Assertions.assertThat(OrderStatus.ORDER).isEqualTo(getOrder.getStatus());
        Assertions.assertThat(1).isEqualTo(getOrder.getOrderItems().size());
        Assertions.assertThat(10000*2).isEqualTo(getOrder.getTotalPrice());
        Assertions.assertThat(8).isEqualTo(item.getStockQuantity());
    }

    @Test
    public void exception(){
        Member member = createMember();
        Item item = createBook("jaeyeon jpa", 1000, 10);

        int orderCount = 100;

        Assertions.assertThatThrownBy(() -> {
            orderService.order(member.getId(), item.getId(), orderCount);
        }).isInstanceOf(NotEnoughStockException.class);

    }

    @Test
    public void cancel(){

        Member member = createMember();
        Item item = createBook("jaeyeon jpa", 1000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        orderService.cancelOrder(orderId);

        Order getOrder = orderRepository.findOne(orderId);

        Assertions.assertThat(OrderStatus.CANCEL).isEqualTo(getOrder.getStatus());
        Assertions.assertThat(10).isEqualTo(item.getStockQuantity());

    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }
    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }

}