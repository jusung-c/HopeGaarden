package io.jus.hopegaarden.controller.auth.signup;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jus.hopegaarden.controller.auth.signup.request.SignUpRequest;
import io.jus.hopegaarden.domain.define.member.Member;
import io.jus.hopegaarden.domain.fixture.MemberFixture;
import io.jus.hopegaarden.sevice.auth.signup.SignUpService;
import io.jus.hopegaarden.utils.IntegrationHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static io.jus.hopegaarden.utils.IntegrationHelper.NON_ASCII;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings(NON_ASCII)
@AutoConfigureMockMvc
class SignUpControllerTest extends IntegrationHelper {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SignUpService signUpService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 회원가입_요청_성공_테스트() throws Exception {
        // given
        Member member = MemberFixture.일반_유저_생성();

        SignUpRequest request = SignUpRequest.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .password(member.getPassword())
                .passwordVerify(member.getPassword())
                .build();

        // when
        doNothing().when(signUpService).requestSignup(any(SignUpRequest.class));

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 회원가입_요청_유효성_검증_실패_테스트_1() throws Exception {
        // given
        Member member = MemberFixture.일반_유저_생성();

        SignUpRequest request = SignUpRequest.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .password("@@@@@@@@@@")
                .passwordVerify(member.getPassword())
                .build();

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("password: 비밀번호는 최소한 하나의 대문자, 소문자, 특수문자 및 숫자를 포함해야 합니다."))
                .andDo(print());
    }

    @Test
    void 회원가입_요청_유효성_검증_실패_테스트_2() throws Exception {
        // given
        Member member = MemberFixture.일반_유저_생성();

        SignUpRequest request = SignUpRequest.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .password("Aa@1")
                .passwordVerify(member.getPassword())
                .build();

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("password: 비밀번호는 8자 이상 20자 이하여야 합니다."))
                .andDo(print());
    }

}