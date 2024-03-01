package io.jus.hopegaarden.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import io.jus.hopegaarden.domain.define.token.jwt.repository.TokenRepository;
import io.jus.hopegaarden.exception.ErrorCode;
import io.jus.hopegaarden.exception.exceptions.auth.*;
import io.jus.hopegaarden.exception.util.ErrorUtil;
import io.jus.hopegaarden.sevice.jwt.JwtTokenProvider;
import auth.AuthenticationExtractor;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("[HG INFO]: Jwt 인증 필터에 진입합니다");

        // 헤더에서 JWT 토큰 추출
        final Optional<String> extractToken = AuthenticationExtractor.extractToken(request);
        final String jwtToken;
        final String subject;

        // JWT 토큰이 BEARER 형식이 아니거나 존재하지 않는다면 다음 필터로
        if (extractToken.isEmpty()) {
            log.info("[HG INFO]: Jwt 토큰이 헤더에 없으므로 다음 필터로 이동합니다");

            filterChain.doFilter(request, response);
            return;
        }

        try {
            jwtToken = extractToken.get();

            // 식별자 추출
            subject = jwtTokenProvider.extractSubject(jwtToken);

            // subject이 null인 경우 예외 발생
            if (subject == null) {
                throw new TokenInvalidException(ErrorCode.JWT_SUBJECT_IS_NULL);
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(subject);

            boolean isTokenValid = tokenRepository.findByToken(jwtToken)
                    .map(token -> !token.isExpired() && !token.isRevoked())
                    .orElse(false);

            // 토큰 유효성 검증
            if (jwtTokenProvider.isTokenValid(jwtToken, userDetails) && isTokenValid) {

                // UserDetails를 사용해 Authentication 생성
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                // Authentication에 현재 요청 정보를 저장
                authToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));

                // Security Context에 Authentication 등록
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);

                log.info("[HG INFO]: Jwt 토큰이 성공적으로 인증되었습니다");

                // JWT 토큰 인증을 마치면 다음 인증 필터로 이동
                filterChain.doFilter(request, response);

            } else {
                jwtExceptionHandler(response, ErrorCode.JWT_TOKEN_INVALID);
            }

        } catch (ExpiredJwtException e) {
            jwtExceptionHandler(response, ErrorCode.JWT_TOKEN_EXPIRED);

        } catch (UnsupportedJwtException e) {
            jwtExceptionHandler(response, ErrorCode.JWT_UNSUPPORTED);

        } catch (MalformedJwtException e) {
            jwtExceptionHandler(response, ErrorCode.JWT_MALFORMED);

        } catch (SignatureException e) {
            jwtExceptionHandler(response, ErrorCode.JWT_SIGNATURE);

        } catch (IllegalArgumentException e) {
            jwtExceptionHandler(response, ErrorCode.JWT_ILLEGAL_ARGUMENT);
        }
    }

    // 모든 JWT Exception을 처리하는 핸들러
    private void jwtExceptionHandler(HttpServletResponse response, ErrorCode error) throws IOException {
        log.error(">>>> [ JWT 토큰 인증 중 Error 발생 : {} ] <<<<", error.getMessage());

        response.setStatus(error.getValue());
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(
                ErrorUtil.getErrorResponseResponseEntity(error)
        ));
    }
}
