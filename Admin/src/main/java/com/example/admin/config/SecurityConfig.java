package com.example.admin.config;

import com.example.admin.manage.AdminManage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * spring security 配置
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/css/**", "/js/**", "/favicon.svg").permitAll() // 必须放行静态资源！
                        .anyRequest()
                        .authenticated()
                )
                // 移除 .formLogin(withDefaults()) 和 .httpBasic(withDefaults())
                .formLogin(formLogin -> {
                    formLogin
                            .loginPage("/login").permitAll() // 登录页面无需授权即可访问
                            .loginProcessingUrl("/login")    // 明确指定处理登录的URL
                            .usernameParameter("username")   // 自定义表单用户名参数
                            .passwordParameter("password")   // 自定义表单密码参数
                            .failureUrl("/login?error")      // 登录失败的返回地址
                            .successHandler(new MyAuthenticationSuccessHandler()) // 登录成功的处理
                            .failureHandler(new MyAuthenticationFailureHandler()) // 认证失败时的处理
                    ;
                })
                .logout(logout -> {
                    logout
                            .logoutUrl("/logout")            // 明确指定注销URL
                            .logoutSuccessHandler(new MyLogoutSuccessHandler()) // 注销成功时的处理
                            .permitAll();
                })
                .csrf(csrf -> csrf.disable()); // 注意：生产环境应该启用CSRF保护

        return http.build();
    }

}
