package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repositoryV0 = new MemberRepositoryV0();



    @Test
    void crud() throws SQLException {
        Member member = new Member("memberV0", 10000);
        repositoryV0.save(member);

        Member findMember = repositoryV0.findById(member.getMemberId());
        log.info("findMember = {}", findMember);
        assertThat(findMember).isEqualTo(member);

        repositoryV0.update(member.getMemberId(), 2000);
        Member updateMember = repositoryV0.findById(member.getMemberId());
        assertThat(updateMember.getMoney()).isEqualTo(2000);

        repositoryV0.delete(updateMember.getMemberId());
        assertThatThrownBy(() -> repositoryV0.findById(member.getMemberId())).isInstanceOf(NoSuchElementException.class);

    }

}