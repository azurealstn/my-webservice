package com.minsu.springboot.config.auth;

import com.minsu.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                // h2-console 화면을 사용하기 위해 해당 옵션들을 disable
                .headers().frameOptions().disable()
                .and()
                    // URL별 권한 권리를 설정하는 옵션의 시작점
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                    // "/api/v1/**" 주소를 가진 API는 USER 권한을 가진 사람만 가능하도록 설정
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    // 모든 인증된 사용자들에게만 허용
                    .anyRequest().authenticated()
                .and()
                    .logout()
                        // 로그아웃 성공시 / 주소로 이동
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        // 로그인 성공 이후 사용자 정보를 가져올 때의 설정
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);
    }
}
