package org.example.demo;

// 第三章 建立你的第一個 Spring Boot REST API
// 這個範例展示了如何建立一個簡單的 Coffee 類別，並使用 UUID 作為唯一識別碼。

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class Application
{
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }

    // @GetMapping("/hello")
    // public String hello(@RequestParam(value = "name", defaultValue = "World") String name)
    // {
    //     return String.format("Hello %s!", name);
    // }
}

