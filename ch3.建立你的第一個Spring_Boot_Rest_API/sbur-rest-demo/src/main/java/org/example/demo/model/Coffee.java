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
     * 預設建構函數 - 用於創建空的 Coffee 實例
     * 這個建構函數通常用於 JSON 反序列化，
     */
    public Coffee()
    {
        // 因為 id 是 final 的，所以在這裡也需要初始化它。
        this.id = UUID.randomUUID().toString(); // 自動生成一個唯一識別碼
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



/*

 書裡的範例中使用 pipe (<) 來處理 POST 作業。但一直試不成功。
 底下是使用 GitHub Copilot 幫忙的程式碼。

 加入 `Coffee()` 無參數建構子可以解決 JSON 傳入的問題，原因是像 Spring Boot 使用的 Jackson 序列化/反序列化庫需要一個無參數建構子來建立物件。以下是原因：

 1. **Jackson 的反序列化需求**：
   Jackson 在反序列化 JSON 時，會使用反射來創建 Java 類的實例。如果類中沒有無參數建構子，Jackson 無法正確地創建物件，導致反序列化失敗。

 2. **初始化物件**：
   無參數建構子確保物件在創建時被正確初始化，即使 JSON 中未提供某些字段的值。例如，`Coffee()` 建構子中初始化了 `id` 字段，確保物件在反序列化後是有效的。

 Adding the `Coffee()` constructor allows the JSON deserialization process to work correctly. When a JSON object is sent to the server, frameworks like Spring Boot use libraries such as Jackson to map the JSON data to a Java object. Jackson requires a no-argument constructor to create an instance of the object before populating its fields.

 Without the no-argument constructor, Jackson cannot instantiate the `Coffee` class, resulting in errors during the deserialization process.

========================================================

 */