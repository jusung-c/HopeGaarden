package io.jus.hopegaarden.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*
    실제 CORS 처리 수행
        - 모든 도메인('*')에서의 요청을 허용하도록 설정
 */
public class CorsCustomFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        // 모든 출처에서의 요청을 허용
        response.setHeader("Access-Control-Allow-Origin", "*");

        // 요청이 인증 정보를 포함하는지 여부
        response.setHeader("Access-Control-Allow-Credentials", "true");

        // 모든 HTTP 메서드 요청 허용
        response.setHeader("Access-Control-Allow-Methods", "*");

        // 클라이언트는 3600(1시간)동안 pre-flight 요청의 유효기간을 가짐
        response.setHeader("Access-Control-Max-Age", "3600");

        // 클라이언트가 서버에게 보낼 수 있는 헤더 지정 -> 모든 헤더 허용
        response.setHeader("Access-Control-Allow-Headers", "*");

        filterChain.doFilter(request, response);
    }

}
