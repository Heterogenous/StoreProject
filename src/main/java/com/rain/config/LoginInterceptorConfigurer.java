package com.rain.config;

import com.rain.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/** 处理器、拦截器的注册 **/
@Configuration//加载当前拦截器并进行注册
public class LoginInterceptorConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //创建自定义的拦截器的对象
        LoginInterceptor loginInterceptor = new LoginInterceptor();


        //配置不拦截的白名单
        List<String> patterns = new ArrayList<>();
        patterns.add("/bootstrap3/**");
        patterns.add("/css/**");
        patterns.add("/images/**");
        patterns.add("/js/**");
        patterns.add("/web/register.html");
        patterns.add("/web/login.html");
        patterns.add("/web/product.html");
        patterns.add("/users/reg");
        patterns.add("/users/login");


        //完成拦截器的注册
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")//表示要拦截的url是什么
                .excludePathPatterns(patterns);
    }
}
