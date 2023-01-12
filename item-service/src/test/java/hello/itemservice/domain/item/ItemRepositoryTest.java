package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach(){
        itemRepository.clearStroe();
    }

    @Test
    void save() {
        //given
        Item item = new Item("itemA", 10000, 10);
        //when
        Item saveItem = itemRepository.save(item);

        //then
        Item findItem = itemRepository.findById(item.getId());

        assertThat(findItem).isEqualTo(saveItem);
    }

    @Test
    void findById() {

    }

    @Test
    void findAll() {
        //given
        Item item1 = new Item("Item1", 4000, 1);
        Item item2 = new Item("Item2", 8000, 1);

        itemRepository.save(item1);
        itemRepository.save(item2);

        //when

        List<Item> items = itemRepository.findAll();

        //then

        assertThat(items.size()).isEqualTo(2);
        assertThat(items).contains(item1, item2);
    }

    @Test
    void update() {
        //given
        Item item1 = new Item("Item1", 4000, 1);

        Item saveItem = itemRepository.save(item1);
        Long itemId = saveItem.getId();

        //when
        Item updateParam = new Item("item2", 8000, 2);
        itemRepository.update(itemId, updateParam);

        //then
        Item findItem = itemRepository.findById(itemId);

        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }

}