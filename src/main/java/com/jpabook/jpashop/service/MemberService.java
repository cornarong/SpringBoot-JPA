package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// readOnly 옵션을 true 로 주면 '조회' 하는 기능에서는 JPA 에서 성능을 좀 더 최적화 해준다. -> 즉 읽기에서는 가급적 readOnly = true 를 사용한다.
// 클래스 레벨에 readOnly 를 선언 해주었기 때문에 쓰기 메소드 에서는 Transactional 를 메소드 레벨로 선언해준다. 그부분은 클래스 레벨에 선언한 readOnly옵션이 먹지 않는다. -> 우선순위를 가진다.
@Transactional(readOnly = true)
@RequiredArgsConstructor // final을 가진 필드의 생성자를 자동으로 만들어 준다.
public class MemberService {

    /*// 필드 인젝션
    @Autowired
    private MemberRepository memberRepository;*/

    // 생성자 인젝션
    private final MemberRepository memberRepository;
/*    @Autowired // 클래스 레벨에 RequiredArgsConstructor 어노테이션을 사용하면 생성자 인젝션에서 생성자를 지워되 된다. (해당 필드 final 필수)
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/

    /**
     * 회원 가입
     */
    @Transactional(readOnly = false)
    public Long join(Member member){
        validateDuplicateMemeber(member); // 중복회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMemeber(Member member) {
        // Exception
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    @Transactional(readOnly = true)
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    // 회원 단건 조회
    @Transactional(readOnly = true)
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }


}
