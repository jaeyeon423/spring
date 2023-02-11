package com.board.demo.dto;

import com.board.demo.domain.Member;
import com.board.demo.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Data
@AllArgsConstructor
public class SignUpRequest {
    private String email;
    private String password;
    private String username;
    private String nickname;

    public static Member toEntity(SignUpRequest req, Role role, PasswordEncoder encoder){
        return new Member(req.email, encoder.encode(req.password), req.username, req.nickname, List.of(role));
    }
}
