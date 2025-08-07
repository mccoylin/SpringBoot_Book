package org.example.demo.model;

// 第四章 讓你的 Spring Boot APP 存取資料庫
// 這個範例採用第三章的範例加入 H2 資料庫的功能。

// 和書本不同的是，我把程式碼拆開了。不再和書本一樣放在同一個檔案裡。
// Coffee Class 有兩個成員變數 member variables：
// id : 用來唯一識別特定的一種咖啡。
// name : 以名稱描述咖啡。

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Coffee 模型類
 * 代表一種咖啡產品，包含唯一識別碼和名稱
 */
@Data                   // Data：自動生成所有字段的 getter、setter 方法，以及 toString()、equals() 和 hashCode() 方法。
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


========================================================

 */