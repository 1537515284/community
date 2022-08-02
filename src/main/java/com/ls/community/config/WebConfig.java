package com.ls.community.config;

import com.ls.community.interceptor.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    // 获取application.yaml中的配置
    @Value("${file.staticAccessPath}")
    private String staticAccessPath;
    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 灵活一点
        registry.addResourceHandler(staticAccessPath)
                .addResourceLocations("file:" + uploadFolder);
    }
}
