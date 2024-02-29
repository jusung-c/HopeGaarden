package io.jus.hopegaarden.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jus.hopegaarden.controller.auth.request.AuthRequest;
import io.jus.hopegaarden.domain.define.member.Member;
import io.jus.hopegaarden.domain.define.member.repository.MemberRepository;
import io.jus.hopegaarden.sevice.auth.AuthService;
import io.jus.hopegaarden.sevice.auth.response.AuthResponse;
import io.jus.hopegaarden.sevice.jwt.JwtTokenProvider;
import io.jus.hopegaarden.utils.IntegrationHelper;
import io.jus.hopegaarden.utils.TokenUtil;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static io.jus.hopegaarden.domain.fixture.MemberFixture.일반_유저_생성;
import static io.jus.hopegaarden.utils.IntegrationHelper.NON_ASCII;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings(NON_ASCII)
@AutoConfigureMockMvc
class AuthControllerTest extends IntegrationHelper {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 로그인_요청_테스트() throws Exception {
        // given
        Member member = memberRepository.save(일반_유저_생성());

        Map<String, String> map = TokenUtil.createTokenMap(member);
        String token = jwtTokenProvider.generateToken(map, member);
        String refresh = jwtTokenProvider.generateRefreshToken(member);

        // when
        when(authService.authenticate(any(AuthRequest.class)))
                .thenReturn(AuthResponse.builder()
                        .accessToken(token)
                        .refreshToken(refresh)
                        .build());

        // then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(AuthRequest.builder()
                                .email("email")
                                .password("password")
                                .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").exists())
                .andExpect(header().exists(HttpHeaders.SET_COOKIE))
                .andExpect(header().string(HttpHeaders.SET_COOKIE, containsString("refreshToken")))
                .andDo(print());

    }

    @Test
    void refreshTokenTest() throws Exception {
        // given
        String newToken = "new_access_token";
        String refreshToken = "dummy_refresh_token";

        // when
        when(authService.refreshToken(any(String.class)))
                .thenReturn(newToken);

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);

        // when, then
        mockMvc.perform(post("/auth/refresh")
                        .cookie(refreshTokenCookie)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value(newToken));
    }

}