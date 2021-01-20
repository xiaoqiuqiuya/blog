package com.nice.demo.config;


import com.nice.demo.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    //视图映射
    // 通过这个方法可以直接在地址栏访问或者重定向到templates下的模板页面
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("article.html").setViewName("article");
        registry.addViewController("read.html").setViewName("read");
        registry.addViewController("index.html").setViewName("index");
        registry.addViewController("diary.html").setViewName("diary");
        registry.addViewController("link.html").setViewName("link");
        registry.addViewController("message.html").setViewName("message");
        registry.addViewController("publish.html").setViewName("publish");
        registry.addViewController("login.html").setViewName("login");
//        registry.addViewController("login2.html").setViewName("login2");
    }
    //配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                //需要拦截的请求  --> /** 拦截所有的请求
                .addPathPatterns("/publish")
                //不拦截的请求
                .excludePathPatterns("/", "/login", "index.html", "/css/**", "/js/**");
    }

}
