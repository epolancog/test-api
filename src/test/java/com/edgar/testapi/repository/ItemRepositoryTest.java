package com.edgar.testapi.repository;

import com.edgar.testapi.entity.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void testFindByName() {
        Item item = new Item("Laptop", 1000.0, null);
        itemRepository.save(item);

        List<Item> items = itemRepository.findByName("Laptop");

        assertEquals(1, items.size());
        assertEquals("Laptop", items.get(0).getName());
    }
}
