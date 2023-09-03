package com.atrs.airticketreservationsystem.config;


import com.atrs.airticketreservationsystem.utils.LoginInterceptor;
import com.atrs.airticketreservationsystem.utils.RefreshTokenInterception;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

import static com.atrs.airticketreservationsystem.common.SystemConstants.IMAGE_UPLOAD_DIR_AVATAR;
import static com.atrs.airticketreservationsystem.common.SystemConstants.IMAGE_UPLOAD_DIR_FIND;


@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("*")
                .maxAge(3600);

    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns(
                        "/admin/code",
                        "/admin/login",
                        "/admin/logout",
                        "/user/login",
                        "/agent/login",
                        "/user/register",
                        "/flight/queryAll",
                        "/user/activeUser/**"
                ).order(100);

        registry.addInterceptor(new RefreshTokenInterception(stringRedisTemplate)).addPathPatterns(
                "/**"
        ).order(0);
    }
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/" + IMAGE_UPLOAD_DIR_AVATAR +"/**").
                addResourceLocations("file:"+IMAGE_UPLOAD_DIR_FIND);
    }

}

