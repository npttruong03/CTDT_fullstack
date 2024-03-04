package CTDTFullStack.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import CTDTFullStack.controllers.AuthInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	private AppConfig appConfig;
	
	@Autowired
	public WebMvcConfig(AppConfig appConfig) {
		this.appConfig = appConfig;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInterceptor()).addPathPatterns("/admin/**").excludePathPatterns("/admin/login/**");
	}
	
	@Bean
	public AuthInterceptor authInterceptor() {
		return new AuthInterceptor(appConfig);
	}
	
	@Bean
	@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public AppConfig appConfig2() {
		return new AppConfig();
	}
}
