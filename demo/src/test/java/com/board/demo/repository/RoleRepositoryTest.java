package com.board.demo.repository;

import com.board.demo.domain.Role;
import com.board.demo.domain.RoleType;
import com.board.demo.exception.RoleNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RoleRepositoryTest {
    @Autowired
    RoleRepository roleRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    public void createAndReadTest(){
        //given
        Role role = createRole();

        //when
        roleRepository.save(role);
        clear();

        //then
        Role findRole = roleRepository.findById(role.getId()).orElseThrow(RoleNotFoundException::new);
        Assertions.assertThat(findRole.getId()).isEqualTo(role.getId());
    }

    @Test
    public void deleteTest(){
        //given
        Role role = roleRepository.save(createRole());
        clear();

        //when
        roleRepository.delete(role);

        //then
        Assertions.assertThatThrownBy(() -> roleRepository.findById(role.getId()).orElseThrow(RoleNotFoundException::new))
                .isInstanceOf(RoleNotFoundException.class);
    }

    @Test
    public void uniqueRoleTypeTest(){
        //given
        roleRepository.save(createRole());
        clear();

        //when //then
        Assertions.assertThatThrownBy(() -> roleRepository.save(createRole())).isInstanceOf(DataIntegrityViolationException.class);
    }
    private Role createRole() {
        return new Role(RoleType.ROLE_NORMAL);
    }

    private void clear() {
        em.flush();
        em.clear();
    }

}