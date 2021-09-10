package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

// 이번에는 Spring Data JPA(JpaRepository)를 사용하지 않고 EntityManager를 직접 다뤄보기 위함.

    @PersistenceContext
    private EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){ // jpql에서 from의 대상이 table이 아닌 entity가 된다.
        List<Member> resultList = em.createQuery("select m from Member m", Member.class)
                .getResultList();
        return resultList;
    }

    public List<Member> findByName(String name){
        List<Member> resultList = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return resultList;
    }
}
