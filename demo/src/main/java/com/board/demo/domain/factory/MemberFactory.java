package com.board.demo.domain.factory;

import com.board.demo.domain.Member;
import com.board.demo.domain.Role;

import java.util.List;

import static java.util.Collections.emptyList;

public class MemberFactory {
    public static Member createMember() {
        return new Member("email@email.com", "123456a!", "username", "nickname", emptyList());
    }

    public static Member createMember(String email, String password, String username, String nickname) {
        return new Member(email, password, username, nickname, emptyList());
    }

    public static Member createMemberWithRoles(List<Role> roles) {
        return new Member("email@email.com", "123456a!", "username", "nickname", roles);
    }
}
