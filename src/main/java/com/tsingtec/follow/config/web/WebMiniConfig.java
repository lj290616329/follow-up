package com.tsingtec.follow.config.web;

import com.tsingtec.follow.handler.interceptor.MiniInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMiniConfig implements WebMvcConfigurer {

	@Bean
	public MiniInterceptor androidInterceptor() {
		return new MiniInterceptor();
	}

	// 这个方法是用来配置静态资源的，比如html，js，css，等等
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {}
 
	// 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(androidInterceptor()).addPathPatterns("/api/**").excludePathPatterns("/api/index/**","/api/upload/**");
	}
}