package com.rain.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 定义一个拦截器 **/
public class LoginInterceptor implements HandlerInterceptor {
    //在调用所有处理请求的方法之前被自动调用执行的方法
    /**
     * 检测全局session对象中是否有uid数据，有放行，否则重定向登陆页面
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器（url+Controller:映射）
     * @return true放行，false拦截请求
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //获取session对象
        Object obj = request.getSession().getAttribute("uid");
        if(obj == null){
            //当uid不存在，则重定向
            response.sendRedirect("/web/login.html");
            //拦截请求
            return false;
        }
        //请求放行
        return true;
    }

    //在ModelAndView对象返回之后被调用的方法
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    //在整个请求所有关联的资源被执行完毕最后所执行的方法
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
