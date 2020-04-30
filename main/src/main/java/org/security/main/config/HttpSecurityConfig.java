package org.security.main.config;

import org.security.main.filter.JwtVerifyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class HttpSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private RsaKeyProperties jwtRsaKeyProperties;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭跨站请求防护
        http  .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/index/**").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/main").hasAnyRole("USER")
                .antMatchers("/category").hasAnyRole("ADMIN")
                //其他的需要授权后访问
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtVerifyFilter(authenticationManager(), jwtRsaKeyProperties))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


}
