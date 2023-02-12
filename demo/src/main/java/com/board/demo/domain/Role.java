package com.board.demo.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Enumerated(EnumType.STRING) // 1
    @Column(nullable = false, unique = true) // 2
    private RoleType roleType;

    public Role(RoleType roleType) {
        this.roleType = roleType;
    }
}