package com.jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional // @Transactional이 TEST에 있으면 테스트 후 데이터를 롤백한다. DB에서 직접 확인 하고 싶으면 아래의 어노테이션을 추가해 롤백을 하지 않도록 한다.
    @Rollback(value = false)
    void testMember() throws Exception {
        //Given
        Member member = new Member();
        member.setUsername("memberA");

        //When
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        //Then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member);

    }
}