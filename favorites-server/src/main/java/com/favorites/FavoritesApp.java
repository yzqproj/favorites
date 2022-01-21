package com.favorites;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author yanni
 * @date time 2022/1/20 18:32
 * @modified By:
 */
@SpringBootApplication
@MapperScan("com.favorites.mapper")
public class FavoritesApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(FavoritesApp.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(FavoritesApp.class, args);
    }

}