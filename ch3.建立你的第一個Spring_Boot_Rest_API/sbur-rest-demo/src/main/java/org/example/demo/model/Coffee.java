package org.example.demo.model;

// 第三章 建立你的第一個 Spring Boot REST API
// 這個範例展示了如何建立一個簡單的 Coffee 類別，並使用 UUID 作為唯一識別碼。

// 和書本不同的是，我把程式碼拆開了。不再和書本一樣放在同一個檔案裡。
// 程式碼的部份來自「圖 3-3」
// Coffee Class 有兩個成員變數 member variables：
// id : 用來唯一識別特定的一種咖啡。
// name : 以名稱描述咖啡。

import java.util.UUID;

/**
 * Coffee 模型類
 * 代表一種咖啡產品，包含唯一識別碼和名稱
 */
public class Coffee
{
    private final String id;         // 咖啡的唯一識別碼，一旦設定後不可更改
    private String name;        // 咖啡的名稱

    /**
     * 建構函數 - 使用指定的 ID 和名稱建立咖啡實例
     * @param id 咖啡的唯一識別碼
     * @param name 咖啡的名稱
     */
    public Coffee(String id, String name)
    {
        this.id = id;
        this.name = name;
    }

    /**
     * 建構函數 - 僅使用名稱建立咖啡實例，自動生成 UUID 作為 ID
     * @param name 咖啡的名稱
     */
    public Coffee(String name)
    {
        // 調用另一個建構函數，並使用 UUID 生成唯一識別碼
        this(UUID.randomUUID().toString(), name);
    }

    /**
     * 取得咖啡的唯一識別碼
     * @return 咖啡的 ID
     */
    public String getId()
    {
        return id;
    }

    /**
     * 取得咖啡的名稱
     * @return 咖啡的名稱
     */
    public String getName()
    {
        return name;
    }

    /**
     * 設定咖啡的名稱
     * @param name 咖啡的新名稱
     */
    public void setName(String name)
    {
        this.name = name;
    }

}
