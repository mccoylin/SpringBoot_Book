package org.example.demo.repository;

// 第四章 讓你的 Spring Boot APP 存取資料庫
// 這個範例採用第三章的範例加入 H2 資料庫的功能。

import org.springframework.data.repository.CrudRepository;

import org.example.demo.model.Coffee;

public interface CoffeeRepository extends CrudRepository<Coffee, String>
{

}
