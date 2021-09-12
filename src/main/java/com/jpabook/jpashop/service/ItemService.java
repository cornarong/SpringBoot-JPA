package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    // 상품서비스는 단순하게 상품 레포지토리에 위임만 한다.
    // 따라서 경우에 따라서는 컨트롤러에서 바로 상품 레포지토리에 접근해서 처리해도 문제가 없다.

    private final ItemRepository itemRepository;

    /**
     * 아이템 등록
     * @return
     */
    @Transactional(readOnly = false)
    public Long saveItem(Item item){
        itemRepository.save(item);
        return item.getId();
    }

/*    private void validateDuplicateItem(Item item) {
        // Exception
        List<Item> findItems = itemRepository.findByName(item.getName());
        if(!findItems.isEmpty()){
            System.out.println("여기탄다~");
            throw new IllegalStateException("이미 존재하는 아이템입니다.");
        }
    }*/

    /**
     * 아이템 단건 조회
     */
    public Item findOne(Long itemId){
        Item item = itemRepository.findOne(itemId);
        return item;
    }

    /**
     * 아이템 전체 조회
     */
    public List<Item> findItems(){
        List<Item> items = itemRepository.findAll();
        return items;
    }

}
