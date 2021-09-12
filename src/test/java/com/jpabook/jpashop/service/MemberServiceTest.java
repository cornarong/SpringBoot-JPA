package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.util.AssertionErrors;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.fail;

// junit5 에서는 @RunWith(SpringRunner.class)이 포함되어 있어 명시하지 않아도 된다. -> junit실행할 때 스프링이랑 엮어서 같이 실행시키는 것.
@SpringBootTest // 스프링부트를 띄운 상태에서 테스트해야 됨. 인젝션 등등..
@Transactional // TEST에 있으면 테스트 후 데이터를 *롤백 한다. DB에서 직접 확인 하고 싶으면 아래의 어노테이션 Rollback(false)을 추가해 롤백을 하지 않도록 한다.
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() throws Exception {
        //Given 이렇게 주어졌을 떄
        Member member = new Member();
        member.setName("Chan Namkung");
        //When 이렇게 하면
        Long saveId = memberService.join(member);
        //Then 이렇게 되야된다.
        Assertions.assertEquals(member, memberRepository.findOne(saveId));

    }

    // junit4 에서는 expected = "예외명" 해주며 된다. junit5는 지원 안됨.
    @Test
    void 중복_회원_예외() throws Exception {
        //Given
        Member member1 = new Member();
        member1.setName("Namkung");
        Member member2 = new Member();
        member2.setName("Namkung");
        //When
        memberService.join(member1);
        try {
            memberService.join(member2); // 여기서 예외가 발생해야 한다.
        }catch (IllegalStateException e){
            return;
        }
        //Then
        AssertionErrors.fail("위에서 return 해야 한다. 여기까지 오면 안된다.");
    }
}