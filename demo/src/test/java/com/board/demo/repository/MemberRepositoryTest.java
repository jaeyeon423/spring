package com.board.demo.repository;

import com.board.demo.domain.Member;
import com.board.demo.domain.MemberRole;
import com.board.demo.domain.Role;
import com.board.demo.domain.RoleType;
import com.board.demo.exception.MemberNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    RoleRepository roleRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    public void createAndReadTest() {
        //given
        Member member = createMember();

        //when
        memberRepository.save(member);
        clear();

        //then
        Member findMember = memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new);
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
    }

    @Test
    public void memberDateTest() {
        //given
        Member member = createMember();
        //when
        memberRepository.save(member);
        clear();

        //then
        Member findMember = memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new);
        Assertions.assertThat(findMember.getCreatedAt()).isNotNull();
        Assertions.assertThat(findMember.getModifiedAt()).isNotNull();
        Assertions.assertThat(findMember.getCreatedAt()).isEqualTo(findMember.getModifiedAt());

    }

    @Test
    public void updateTest() {
        //given
        String updatedNickname = "updated";
        Member member = memberRepository.save(createMember());
        clear();

        //when
        Member findMember = memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new);
        findMember.updateNickname(updatedNickname);
        clear();

        //then
        Member updateMember = memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new);
        Assertions.assertThat(updateMember.getNickname()).isEqualTo(updatedNickname);
    }

    @Test
    public void deleteTest() {
        //given
        Member member = memberRepository.save(createMember());
        clear();

        //when
        memberRepository.delete(member);
        clear();

        //then
        Assertions.assertThatThrownBy(() -> memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new)).isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    public void findByEmailTest() {
        //given
        Member member = memberRepository.save(createMember());
        clear();

        //when
        Member findMember = memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new);

        //then
        Assertions.assertThat(findMember.getEmail()).isEqualTo(member.getEmail());

    }

    @Test
    public void findByNicknameTest() {
        //given
        Member member = memberRepository.save(createMember());
        clear();

        //when
        Member findMember = memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new);

        //then
        Assertions.assertThat(findMember.getNickname()).isEqualTo(member.getNickname());

    }

    @Test
    void uniqueEmailTest() {
        // given
        Member member = memberRepository.save(createMember("email1", "password1", "username1", "nickname1"));
        clear();


        //when, then
        assertThatThrownBy(() -> memberRepository.save(createMember(member.getEmail(), "password2", "username2", "nickname2")))
                .isInstanceOf(DataIntegrityViolationException.class);

    }

    @Test
    public void uniqueNickNameTest(){
        // given
        Member member = memberRepository.save(createMember("email1", "password1", "username1", "nickname1"));
        clear();


        //when, then
        assertThatThrownBy(() -> memberRepository.save(createMember("email2", "password2", "username2", member.getNickname())))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void existsByEmailTest() {
        // given
        Member member = memberRepository.save(createMember());
        clear();

        // when, then
        assertThat(memberRepository.existsByEmail(member.getEmail())).isTrue();
        assertThat(memberRepository.existsByEmail(member.getEmail() + "test")).isFalse();
    }

    @Test
    void existsByNicknameTest() {
        // given
        Member member = memberRepository.save(createMember());
        clear();

        // when, then
        assertThat(memberRepository.existsByNickname(member.getNickname())).isTrue();
        assertThat(memberRepository.existsByNickname(member.getNickname() + "test")).isFalse();
    }

    @Test
    public void memberRoleCascadePersistTest(){
        //given
        List<RoleType> roleTypes = List.of(RoleType.ROLE_NORMAL, RoleType.ROLE_SPECIAL_BUYER, RoleType.ROLE_ADMIN);
        List<Role> roles = roleTypes.stream().map(roleType -> new Role(roleType)).collect(Collectors.toList());
        roleRepository.saveAll(roles);
        clear();

        Member member = memberRepository.save(createMemberWithRoles(roleRepository.findAll()));
        clear();

        // when
        Member foundMember = memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new);
        Set<MemberRole> memberRoles = foundMember.getRoles();

        // then
        assertThat(memberRoles.size()).isEqualTo(roles.size());
    }


    private void clear() {
        em.flush();
        em.clear();
    }
    private Member createMemberWithRoles(List<Role> roles) {
        return new Member("email", "password", "username", "nickname", roles);
    }

    private Member createMember(String email, String password, String username, String nickname) {
        return new Member(email, password, username, nickname, emptyList());
    }

    private Member createMember() {
        return new Member("email", "password", "username", "nickname", emptyList());
    }

}