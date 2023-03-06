package hello.core.member;

import hello.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    MemberService memberService;

    @BeforeEach
    void beforeEach(){
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    @Test
    public void join(){
        //given
        Member member1 = new Member(1L, "member1", Grade.BASIC);

        //when
        memberService.joinMember(member1);
        Member findMember = memberService.findMember(1L);

        //then
        Assertions.assertThat(findMember.getName()).isEqualTo(member1.getName());
    }

}