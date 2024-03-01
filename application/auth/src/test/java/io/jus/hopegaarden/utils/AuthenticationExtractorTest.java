package io.jus.hopegaarden.utils;

import auth.AuthenticationExtractor;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static io.jus.hopegaarden.utils.IntegrationHelper.NON_ASCII;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings(NON_ASCII)
class AuthenticationExtractorTest {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER = "Bearer";

    private HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    @Test
    void 토큰이_정상적으로_조회된다() {
        // given
        String expectedResponseToken = "Bearer jwtToken";
        when(request.getHeader(AUTHORIZATION_HEADER)).thenReturn(expectedResponseToken);

        // when
        Optional<String> result = AuthenticationExtractor.extractToken(request);

        // then
        assertSoftly(softly -> {
            softly.assertThat(result).isPresent();
            softly.assertThat(result).isEqualTo(Optional.of("jwtToken"));
        });
    }

    @Test
    void 토큰_헤더가_없다면_빈_값이_반환된다() {
        // given
        when(request.getHeader(AUTHORIZATION_HEADER)).thenReturn("InvalidType token");

        // when
        Optional<String> result = AuthenticationExtractor.extractToken(request);

        // then
        assertThat(result).isEmpty();
    }
}