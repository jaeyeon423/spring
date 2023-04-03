package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void save(){

        Member member = new Member();
        member.setName("jaeyeon");


        Long joinedMember = memberService.join(member);

        Assertions.assertThat(member).isEqualTo(memberRepository.findByName(member.getName()).get(0));
    }

    @Test
    void exception(){

        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        memberService.join(member1);


        Assertions.assertThatThrownBy(() -> {
            memberService.join(member2);
        }).isInstanceOf(IllegalStateException.class);

    }


}