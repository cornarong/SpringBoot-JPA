package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
//@RequiredArgsConstructor
public class ItemRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Item item){
        if(item.getId() == null){
            em.persist(item);
        }else{
            em.merge(item);
        }
    }

    /**
     * 아이템 단건 조회 (아이디)
     */
    public Item findOne(Long itemId){
        return em.find(Item.class, itemId);
    }

    /**
     * 아이템 단건 조회 (이름, 회원중복 확인 시 사용)
     */
/*    public List<Item> findByName(String name){
        List<Item> resultList = em.createQuery("select i from Item i where i.name = :name", Item.class)
                .setParameter("name", name)
                .getResultList();
        return resultList;
    }*/

    /**
     * 아이템 전체 조회
     */
    public List<Item> findAll(){ // jpql 사용
        List<Item> resultList = em.createQuery("select i from Item i", Item.class)
                .getResultList();
        return resultList;
    }





}
