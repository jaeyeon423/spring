package com.board.demo.controller.sign;

import com.board.demo.dto.sign.RefreshTokenResponse;
import com.board.demo.dto.sign.SignInRequest;
import com.board.demo.dto.sign.SignInResponse;
import com.board.demo.dto.sign.SignUpRequest;
import com.board.demo.service.sign.SignService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.board.demo.dto.sign.SignUpRequestFactory.createSignUpRequest;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class SignControllerTest {
    @InjectMocks
    SignController signController;
    @Mock SignService signService;
    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(signController).build();
    }

    @Test
    public void signUpTest() throws Exception {
        // given
         SignUpRequest req = createSignUpRequest("email@email.com", "123456a!", "username", "nickname");

        // when, then
        mockMvc.perform(
                        post("/api/sign-up")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());

        verify(signService).signUp(req);
    }

    @Test
    public void signInTest() throws Exception{
        //given
        SignInRequest req = new SignInRequest("email@email.com", "123456a!");
        given(signService.signIn(req)).willReturn(new SignInResponse("access","refresh"));

        //when //then
        mockMvc.perform(post("/api/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.data.accessToken").value("access")) // 3
                .andExpect(jsonPath("$.result.data.refreshToken").value("refresh"));

        verify(signService).signIn(req);
    }

    @Test
    void refreshTokenTest() throws Exception {
        // given
        given(signService.refreshToken("refreshToken")).willReturn(createRefreshTokenResponse("accessToken"));

        // when, then
        mockMvc.perform(
                        post("/api/refresh-token")
                                .header("Authorization", "refreshToken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.data.accessToken").value("accessToken"));
    }

    public static RefreshTokenResponse createRefreshTokenResponse(String accessToken) {
        return new RefreshTokenResponse(accessToken);
    }

}