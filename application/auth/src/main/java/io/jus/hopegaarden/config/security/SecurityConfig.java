package io.jus.hopegaarden.config.security;

import io.jus.hopegaarden.config.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final LogoutHandler logoutService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeHttpRequest ->
                        authorizeHttpRequest
                                // UnAuth Area
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/test").hasAnyAuthority("MEMBER")
                                // Others
                                .anyRequest().authenticated()
                )
                .sessionManagement((sessionManagement) ->
                        sessionManagement
                                // JWT 토큰 기반의 인증을 사용하기 위해 무상태 세션 정책 사용
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logoutConfig -> {
                    logoutConfig
                            .logoutUrl("/auth/logout")
                            .addLogoutHandler(logoutService)
                            .logoutSuccessHandler((request, response, authentication)
                                    -> SecurityContextHolder.clearContext()
                            );
                });

        return http.build();
    }
}