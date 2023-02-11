package com.board.demo.service;

import com.board.demo.domain.Member;
import com.board.demo.domain.RoleType;
import com.board.demo.dto.SignInRequest;
import com.board.demo.dto.SignInResponse;
import com.board.demo.dto.SignUpRequest;
import com.board.demo.exception.LoginFailureException;
import com.board.demo.exception.MemberEmailAlreadyExistsException;
import com.board.demo.exception.MemberNicknameAlreadyExistsException;
import com.board.demo.exception.RoleNotFoundException;
import com.board.demo.repository.MemberRepository;
import com.board.demo.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SignService {
    private MemberRepository memberRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private TokenService tokenService;

    public void signUp(SignUpRequest req){
        validateSignUpInfo(req);
        memberRepository.save(SignUpRequest.toEntity(
                req,
                roleRepository.findByRoleType(RoleType.ROLE_NORMAL).orElseThrow(RoleNotFoundException::new),
                passwordEncoder)
        );
    }

    public SignInResponse signIn(SignInRequest req) {
        Member member = memberRepository.findByEmail(req.getEmail()).orElseThrow(LoginFailureException::new);
        validatePassword(req, member); // 1
        String subject = createSubject(member); // 2
        String accessToken = tokenService.createAccessToken(subject); // 3
        String refreshToken = tokenService.createRefreshToken(subject); // 4
        return new SignInResponse(accessToken, refreshToken); // 5
    }


    private void validateSignUpInfo(SignUpRequest req) {
        if(memberRepository.existsByEmail(req.getEmail()))
            throw new MemberEmailAlreadyExistsException(req.getEmail());
        if(memberRepository.existsByNickname(req.getNickname()))
            throw new MemberNicknameAlreadyExistsException(req.getNickname());
    }

    private void validatePassword(SignInRequest req, Member member) {
        if(!passwordEncoder.matches(req.getPassword(), member.getPassword())) {
            throw new LoginFailureException();
        }
    }

    private String createSubject(Member member) {
        return String.valueOf(member.getId());
    }
}
