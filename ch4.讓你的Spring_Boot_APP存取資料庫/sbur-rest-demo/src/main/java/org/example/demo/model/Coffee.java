package org.example.demo.model;

// 第四章 讓你的 Spring Boot APP 存取資料庫
// 這個範例採用第三章的範例加入 H2 資料庫的功能。

// 和書本不同的是，我把程式碼拆開了。不再和書本一樣放在同一個檔案裡。
// Coffee Class 有兩個成員變數 member variables：
// id : 用來唯一識別特定的一種咖啡。
// name : 以名稱描述咖啡。

// https://ithelp.ithome.com.tw/articles/10309099
// 在 Spring boot 開發中使用 H2 Database

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Coffee 模型類
 * 代表一種咖啡產品，包含唯一識別碼和名稱
 */
@Getter
@Setter
@AllArgsConstructor     // AllArgsConstructor：生成包含所有字段的參數化構造函數。
@NoArgsConstructor      // @NoArgsConstructor：生成無參構造函數。
@Entity
public class Coffee
{
    @Id
    private String id;         // 咖啡的唯一識別碼
    private String name;        // 咖啡的名稱

    /**
     * 建構函數 - 僅使用名稱建立咖啡實例，自動生成 UUID 作為 ID
     * @param name 咖啡的名稱
     */
    public Coffee(String name)
    {
        // 調用另一個建構函數，並使用 UUID 生成唯一識別碼
        this(UUID.randomUUID().toString(), name);
    }

}



/*

JPA Entity 不應該用 lobmok 的 @Data

主要是 @Data 包含了 @EqualsAndHashCode 與 @ToString 這個功能, 分別會去實作 Java Class 的 equals, hashCode, toString 方法。
這會有什麼影響呢? 可以思考一下, JPA 的 Entity 本身就有 @Id field 可以做 equals 的比較了, 真的還需要去覆寫 equals(Object o) 這個方法嗎？
其二, 當資料庫有關聯時, 比如說 @OneToMany 時, lombok 因為會自動掃描所有 fields 去產生 hashCode() 與 toString() 而破壞了 lazy loadding 機制, 這不就失去 lazy loadding 的用意了嗎？故 非常不建議 在 Entity 使用 lombok 的 @Data。

========================================================

 */