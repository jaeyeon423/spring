package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemServiceTest {

    @Autowired
    EntityManager em;

    @Test
    public void updateTest(){
        //given
        Book book = em.find(Book.class, 1L);

        //Tx
        book.setName("asdfasdf");

        //변경 감지 == dirty checking
        //TX commit

    }

}