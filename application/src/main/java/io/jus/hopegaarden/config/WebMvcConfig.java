package io.jus.hopegaarden.config;

import io.jus.hopegaarden.config.filter.CorsCustomFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // CORS 필터를 등록
    @Bean
    public FilterRegistrationBean corsFilter() {
        FilterRegistrationBean<CorsCustomFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new CorsCustomFilter());

        // 필터의 순서 설정 -> 1로 설정해 가장 먼저 실행되도록
        filterRegistrationBean.setOrder(1);

        // 모든 URL에 대해 필터 적용
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }

}
