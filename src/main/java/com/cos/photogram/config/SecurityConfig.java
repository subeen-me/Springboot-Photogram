package com.cos.photogram.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity //해당 파일로 시큐리티를 활성화
@Configuration //IoC 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean //SecurityConfig가 등록될 때 BCryptPasswordEncoder도 같이 빈으로 등록된다
    public BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //원래 있던 super 삭제 - 기존 시큐리티가 가지고 있는 기능이 비활성화된다
        http.csrf().disable(); //csrf 토큰 설정 해제. postman으로 들어와도 입력 가능
        http.authorizeRequests()
                .antMatchers("/","/user/**","/image/**",
                        "/subscribe/**","/comment/**", "/api/**").authenticated() //이렇게 요청이 오면 인증이 필요하다고 설정
                .anyRequest().permitAll() //(위에 것이 아닌 모든 요청)은 허용한다
                .and() //그리고
                .formLogin() //인증이 필요한 페이지 요청이 오면 로그인하게 페이지를 이동하는데
                .loginPage("/auth/signin") //GET, formLogin 페이지 주소 설정
                .loginProcessingUrl("/auth/signin") //POST, 로그인 요청을 하면 스프링 시큐리티가 로그인 프로세스릉 진행한다.
                .defaultSuccessUrl("/"); //로그인이 성공적으로 되면 이 url로 이동된다
    }
}
