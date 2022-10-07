package com.example.demo.filter;


import com.alibaba.fastjson.JSON;
import com.example.demo.common.BaseContext;
import com.example.demo.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //路径匹配器,支持通配符
public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //1获取本次请求的uri
        String requestURI = request.getRequestURI();

        log.info("拦截到请求: {}",requestURI);

        //定义不需要请求的路径
        String[] urls = new String[]{
                "/user/login","/user/logout","/user/register"
        };
        //2判断本次请求是否需要处理
        boolean check = check(urls, requestURI);
        //如果不需要处理
        if (check){
            log.info("本次请求{}不需要处理",requestURI);
            filterChain.doFilter(request,response);
            return;
        }
        //如果已登录则直接放行
        if (request.getSession().getAttribute("user") != null){
            log.info("用户已登录,用户id为{}",request.getSession().getAttribute("user"));

            //使用线程存放当前用户id
            Long empId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(empId);

            /*long id = Thread.currentThread().getId();
            log.info("线程为{}",id);*/

            filterChain.doFilter(request,response);
            return;
        }
        log.info("用户未登录");
        //如果未登录则返回响应数据
        response.getWriter().write(JSON.toJSONString(Result.error("NOTLOGIN")));
        return;
    }

    //路径匹配,检查本次请求是否需要放行
    public boolean check(String[] urls, String requestUri) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestUri);
            if (match)
                return true;
        }
        return false;
    }
}
