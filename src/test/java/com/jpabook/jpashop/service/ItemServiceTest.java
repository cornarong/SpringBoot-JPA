package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.repository.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.util.AssertionErrors;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemService itemService;

    @Test
    void 아이템등록() throws Exception {
        //Given
        Book book = new Book();
        book.setName("Spring");
        book.setPrice(10000);
        //When
        Long itemId = itemService.saveItem(book);
        Item item = itemRepository.findOne(itemId);
        //Then
        Assertions.assertEquals(item.getName(), "Spring");
        Assertions.assertEquals(item.getPrice(), 10000);
    }

/*    @Test
    void 중복_아이템_예외() throws Exception {
        //Given
        Book book = new Book();
        book.setName("Spring");
        Book book2 = new Book();
        book.setName("Spring");
        //When
        itemService.saveItem(book);
        try {
            itemService.saveItem(book2); // 여기서 예외가 발생해야 한다.
        }catch (IllegalStateException e){
            System.out.println("aaaaaaaaaaa");
            return;
        }
        //Then
        AssertionErrors.fail("위에서 return 해야 한다. 여기까지 오면 안된다.");
    }*/
}