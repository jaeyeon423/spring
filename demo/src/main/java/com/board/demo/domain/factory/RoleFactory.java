package com.board.demo.domain.factory;

import com.board.demo.domain.Role;
import com.board.demo.domain.RoleType;

public class RoleFactory {
    public static Role createRole() {
        return new Role(RoleType.ROLE_NORMAL);
    }
}
