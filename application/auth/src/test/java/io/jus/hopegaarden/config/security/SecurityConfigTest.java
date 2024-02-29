package io.jus.hopegaarden.config.security;

import io.jus.hopegaarden.domain.define.member.Member;
import io.jus.hopegaarden.domain.define.member.repository.MemberRepository;
import io.jus.hopegaarden.domain.define.token.jwt.JwtToken;
import io.jus.hopegaarden.domain.define.token.jwt.constant.TokenType;
import io.jus.hopegaarden.domain.define.token.jwt.repository.TokenRepository;
import io.jus.hopegaarden.domain.fixture.MemberFixture;
import io.jus.hopegaarden.exception.ErrorCode;
import io.jus.hopegaarden.sevice.jwt.JwtTokenProvider;
import io.jus.hopegaarden.utils.IntegrationHelper;
import io.jus.hopegaarden.utils.TokenUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static io.jus.hopegaarden.utils.IntegrationHelper.NON_ASCII;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings(NON_ASCII)
@AutoConfigureMockMvc
class SecurityConfigTest extends IntegrationHelper {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Test
    @DisplayName("인증되지 않은 사용자는 허용되지 않은 엔드포인트에 접근할 수 없다.")
    void unAuthUserTest() throws Exception {// given
        String uri = "/test";

        // when
        mockMvc.perform(
                        get(uri)
                )
                // then
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("모든 사용자는 인증 없이 \"/auth/**\" URI에 접근 가능하다.")
    void authUriPermitAllTest() throws Exception {
        // given
        String expectBody = "test!!";
        String uri = "/auth/test";

        // when
        mockMvc.perform(
                        get(uri)
                )
                // then
                .andExpect(status().isOk())
                .andExpect(content().string(expectBody))
                .andDo(print());
    }

    @Test
    void 인증_받은_사용자는_모든_엔드포인트에_접근할_수_있다() throws Exception {
        // given
        String uri = "/test";
        String expectBody = "test!!";


        Member savedMember = memberRepository.save(MemberFixture.일반_유저_생성());
        String jwtToken = jwtTokenProvider.generateToken(TokenUtil.createTokenMap(savedMember), savedMember);

        tokenRepository.save(JwtToken.builder()
                .token(jwtToken)
                .email(savedMember.getEmail())
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build());

        String refreshToken = jwtTokenProvider.generateRefreshToken(savedMember);

        // when
        mockMvc.perform(
                get(uri)
                        .header("Authorization", "Bearer " + jwtToken)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(expectBody))
                .andDo(print());
    }

    @Test
    void 더_이상_사용할_수_없는_토큰은_검증에_실패한다() throws Exception {
        // given
        String uri = "/test";
        String expectBody = "test!!";


        Member savedMember = memberRepository.save(MemberFixture.일반_유저_생성());
        String jwtToken = jwtTokenProvider.generateToken(TokenUtil.createTokenMap(savedMember), savedMember);

        tokenRepository.save(JwtToken.builder()
                .token(jwtToken)
                .email(savedMember.getEmail())
                .tokenType(TokenType.BEARER)
                .expired(true)
                .revoked(true)
                .build());

        String refreshToken = jwtTokenProvider.generateRefreshToken(savedMember);

        // when
        mockMvc.perform(
                        get(uri)
                                .header("Authorization", "Bearer " + jwtToken)
                )
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.body.value").value(401))
                .andExpect(jsonPath("$.body.message").value(ErrorCode.JWT_TOKEN_INVALID.getMessage()))
                .andDo(print());
    }

}
