package org.example.demo.controller;

// 第四章 讓你的 Spring Boot APP 存取資料庫
// 這個範例採用第三章的範例加入 H2 資料庫的功能。

import jakarta.annotation.PostConstruct;
import org.example.demo.model.Coffee;
import org.springframework.stereotype.Component;

import java.util.List;

import org.example.demo.repository.CoffeeRepository;

@Component()
public class DataLoader
{
    private final CoffeeRepository coffeeRepository;

    public DataLoader(CoffeeRepository coffeeRepository)
    {
        this.coffeeRepository = coffeeRepository; // 注入咖啡存儲庫
    }

    @PostConstruct
    public void loadData()
    {
        // 初始化咖啡數據
        coffeeRepository.saveAll(List.of(
            new Coffee("Cafe Cereza"),
            new Coffee("Cafe Ganador"),
            new Coffee("Cafe Lareno"),
            new Coffee("Cafe Tres Pontas")
        ));
    }


}
