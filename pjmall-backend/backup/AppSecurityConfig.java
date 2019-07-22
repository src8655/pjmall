package com.example.pjmall.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;




@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter{
	

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}
	
	// Interceptor URL의 요청을 안전하게 보호(보안)
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 인증이 되어있을 때(authenticated?)
		http.authorizeRequests()
		.anyRequest().permitAll();		// 모두 허용
		
		
	}
	
	// UserDetailService를 설정
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		super.configure(auth);
	}
}
