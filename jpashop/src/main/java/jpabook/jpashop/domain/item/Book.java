package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B") // 이름 지정 안쓰면 Book가 default
@Getter
@Setter
public class Book extends Item{
    private String author;
    private String isbn;
}
