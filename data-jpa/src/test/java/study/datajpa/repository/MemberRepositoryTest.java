package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;

    @Test
    public void testMember(){
        System.out.println("memberRepository = " + memberRepository.getClass());
        Member member = new Member("jaeyeon", 32);
        Member savedMember = memberRepository.save(member);
        Optional<Member> byId = memberRepository.findById(savedMember.getId());
        Member findMember = byId.get();

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void baseicCRUD(){
        //given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        //단건 저회 검증
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        //리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        //카운트 검즈
        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        //삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        long count2 = memberRepository.count();
        assertThat(count2).isEqualTo(0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThen(){
        //given
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 12);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("BBB", 10);

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getAge()).isEqualTo(12);

    }

    @Test
    public void namedQueryTest(){
        //given
        Member m1 = new Member("회원1", 10);
        Member m2 = new Member("BBB", 12);
        memberRepository.save(m1);
        memberRepository.save(m2);
        //when

        List<Member> result = memberRepository.findByUsername("BBB");
        Member findMember
                = result.get(0);

        //then

        assertThat(findMember).isEqualTo(m2);


    }
    @Test
    public void namedQuery2Test(){
        //given
        Member m1 = new Member("회원1", 10);
        Member m2 = new Member("BBB", 12);
        memberRepository.save(m1);
        memberRepository.save(m2);
        //when

        List<Member> result = memberRepository.findUser("BBB", 12);
        Member findMember = result.get(0);

        //then

        assertThat(findMember).isEqualTo(m2);
    }

    @Test
    public void findUsernameList(){
        //given
        Member m1 = new Member("회원1", 10);
        Member m2 = new Member("BBB", 12);
        memberRepository.save(m1);
        memberRepository.save(m2);

        //when
        List<String> usernameList = memberRepository.findUsernameList();

        //then
        for (String s : usernameList) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void findMemberDto(){

        Team team = new Team("teamA");
        teamRepository.save(team);

        Member m1 = new Member("회원1", 10);
        m1.setTeam(team);
        memberRepository.save(m1);

        List<MemberDto> memberDto = memberRepository.findMemberDto();
        for (MemberDto dto : memberDto) {
            System.out.println("dto.getUsername() = " + dto.getUsername());
        }


    }

}