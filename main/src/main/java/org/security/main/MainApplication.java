package org.security.main;

import org.mybatis.spring.annotation.MapperScan;
import org.security.main.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan({"org.security.main.repository"}) //
@ComponentScan(basePackages= {"org.security.common.*","org.security.main.*"})
@EnableConfigurationProperties(RsaKeyProperties.class)
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class,args);
    }
}
