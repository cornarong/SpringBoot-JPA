package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.item.Book;
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

    // 트랜잭션 안에서 entity를 조회해야 영속성 상태로 조회가 되고, 또 값을 변경해면 변경 감지(dirty checking)가 일어난다.
    // ** 엔티티를 변경할 때는 항상 "변경 감지"를 사용하세요 / 병합(merge)는 최대한 자제하자.
    @Transactional(readOnly = false)
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        // 영속성 컨텍스트에서 엔티티를 다시 조회한 후에 데이터를 수정한다.
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
        // 여기서는 따로 itemRepository.save(); 할 필요가 없다.
        // @Transactional으로 인하여 로직이 끝나면 JPA에서 트랜잭션 commit 시점에 변경 감지(Dirty Checking)한 후 Flush를 하게 된다.
    }
    // 병합(merge) 방법도 있으로도 할 수 있다.
    // 변경 감지 기능을 사용하면 원하는 속성만 선택해서 변경할 수 있지만, 병합을 사용하면 모든 속성이 변경된다.

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
