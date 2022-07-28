package com.rain.config;

import com.rain.interceptor.LoginInterceptor;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/** 处理器、拦截器的注册 **/
@Configuration//加载当前拦截器并进行注册
public class LoginInterceptorConfigurer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //获取jar包所在目录
        ApplicationHome h = new ApplicationHome(getClass());
        File jarFile = h.getSource();
        //System.out.println(jarFile.getParentFile().toString());
        if(jarFile!=null) {
            //在jar包目录下生成一个上传图片保存的文件夹
            String path = jarFile.getParentFile().toString();
            //映射到的路径前缀"file:"不能少
            registry.addResourceHandler("/upload/**").addResourceLocations("file:" + path + "/upload/");
        }else {
            System.out.println("jar包所在目录情况:"+jarFile);
        }
    }

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
        patterns.add("/web/index.html");
        patterns.add("/users/reg");
        patterns.add("/users/login");
        patterns.add("/districts/**");

        //测试用
        patterns.add("/addresses/**");


        //完成拦截器的注册
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")//表示要拦截的url是什么
                .excludePathPatterns(patterns);
    }

    //跨域请求:就是当前网页打开的协议是file,而请求的url是http协议
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS","HEAD")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }
}
