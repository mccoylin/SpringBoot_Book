package org.example.demo.repository;

// 第四章 讓你的 Spring Boot APP 存取資料庫
// 這個範例採用第三章的範例加入 H2 資料庫的功能。

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import org.example.demo.model.Coffee;

public interface CoffeeRepository extends JpaRepository<Coffee, String>
{
    // Spring Data JPA 會自動根據方法名稱產生 SQL 查詢：
    // SELECT * FROM coffee WHERE name = ?
    Optional<Coffee> findByName(String name);

    // 或是，如果你需要忽略大小寫的查詢，可以這樣寫：
    Optional<Coffee> findByNameIgnoreCase(String name);
}


/*

```coffeeRepository.findAll() ```
會將資料庫中 coffees 這張表的「所有」資料都拉到應用程式的記憶體中！
如果你的資料庫裡有 100 萬筆咖啡資料，你的應用程式就會瞬間載入 100 萬筆資料，只為了從中找出 1 筆。
這會造成巨大的記憶體浪費和網路延遲。
正確的做法是：讓資料庫去做它最擅長的事——查詢和過濾。

Spring Data JPA 提供了一個極其優雅的解決方案：你只需要在 Repository 介面中定義一個方法，JPA 就會自動為你產生對應的 SQL 查詢。

========================================================

*/