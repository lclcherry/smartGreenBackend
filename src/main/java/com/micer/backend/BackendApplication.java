package com.micer.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Repository;

@SpringBootApplication(scanBasePackages = "com.micer.backend")
@MapperScan(basePackages = "com.micer.backend.dao", annotationClass = Repository.class)
@EnableCaching
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}

