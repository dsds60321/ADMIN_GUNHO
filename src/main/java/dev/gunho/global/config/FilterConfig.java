package dev.gunho.toooy.global.config;

import dev.gunho.toooy.global.filter.LogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<LogFilter> logFilter() {
        FilterRegistrationBean<LogFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LogFilter());
        registrationBean.addUrlPatterns("/*"); // 모든 URL에 대해 필터 적용
        registrationBean.setOrder(1); // 필터가 실행되는 순서 (낮을수록 먼저 실행)
        return registrationBean;
    }

}
