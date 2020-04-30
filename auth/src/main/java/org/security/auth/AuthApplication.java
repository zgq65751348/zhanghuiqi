package org.security.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.security.auth.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
@MapperScan("org.security.auth.repository") //"org.security.common.*",
@ComponentScan(basePackages= {"org.security.common.*","org.security.auth.*"})
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class,args);
    }
}
