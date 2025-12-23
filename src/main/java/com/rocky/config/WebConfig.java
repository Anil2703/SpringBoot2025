package com.rocky.config;

import com.rocky.controller.interceptor.RateLimitInterceptor;
import com.rocky.controller.interceptor.RateLimitInterceptorWithBucket4J;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /*private final RateLimitInterceptor rateLimitInterceptor;*/

    /*public WebConfig(RateLimitInterceptor rateLimitInterceptor) {
        this.rateLimitInterceptor = rateLimitInterceptor;
    }*/

    private final RateLimitInterceptorWithBucket4J rateLimitInterceptorWithBucket4J;



    public WebConfig(RateLimitInterceptorWithBucket4J rateLimitInterceptorWithBucket4J) {
        this.rateLimitInterceptorWithBucket4J = rateLimitInterceptorWithBucket4J;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimitInterceptorWithBucket4J)
                .addPathPatterns("/**")
                .excludePathPatterns("/actuator/**");
    }
}
