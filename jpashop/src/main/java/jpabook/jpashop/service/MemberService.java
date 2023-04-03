package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;


    //회원 가입
    @Transactional
    public Long join(Member member){

        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();

    }

    private void validateDuplicateMember(Member member){
        //exception
        List<Member> findMembers = memberRepository.findByName(member.getName());

        log.info("name = {}", member.getName());

        if(!findMembers.isEmpty()){
            log.info("존재하는 회원");
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }else{
            log.info("존재하지 않는 회원");
        }

    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    public Member findOne(Long id){
        return memberRepository.findOne(id);
    }
}
