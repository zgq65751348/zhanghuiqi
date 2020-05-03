package org.security.auth.config;

import org.security.auth.filter.JwtTokenFilter;
import org.security.auth.filter.JwtVerifyFilter;
import org.security.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired(required = false)
    private RsaKeyProperties rsaKeyProperties;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭跨站请求防护
        http  .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/index/**").permitAll()
                //允许不登陆就可以访问的方法，多个用逗号分隔
                .and()
                .authorizeRequests()
                .antMatchers("/user").hasAnyRole("USER")
                .antMatchers("/file").hasAnyRole("USER")
                //其他的需要授权后访问
                .anyRequest().authenticated()
                .and()
                //增加自定义认证过滤器
                .addFilter(new JwtTokenFilter(authenticationManager(), rsaKeyProperties))
                //增加自定义验证认证过滤器
                .addFilter(new JwtVerifyFilter(authenticationManager(), rsaKeyProperties))

                // 前后端分离是无状态的，不用session了，直接禁用。
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
