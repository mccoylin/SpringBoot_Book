package org.example.demo.controller;

// 第三章 建立你的第一個 Spring Boot REST API
// 這個範例展示了如何建立一個簡單的 Coffee 類別，並使用 UUID 作為唯一識別碼。

// 和書本不同的是，我把程式碼拆開了。不再和書本一樣放在同一個檔案裡。
// 也把我不懂的寫在結尾處。

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.demo.model.Coffee;

/**
 * REST API 控制器
 * 負責處理與咖啡相關的 HTTP 請求
 */
@RestController // 標記這個類別為 REST 控制器，自動將返回值轉換為 JSON 格式
public class RestApiDemoController
{
    // 存儲咖啡數據的內存集合
    private List<Coffee> coffees = new ArrayList<>();


    /**
     * 構造函數
     * 初始化咖啡列表，新增幾個預設的咖啡數據
     */
    public RestApiDemoController()
    {
        // 初始化一些咖啡數據
        coffees.addAll(List.of(
                new Coffee("Cafe Cereza"),
                new Coffee("Cafe Ganador"),
                new Coffee("Cafe Lareno"),
                new Coffee("Cafe Tres Pontas")
        ));
    }


    // @RequestMapping(value = "/coffees", method = RequestMethod.GET)
    // 使用 @GetMapping 簡化 GET 請求的映射
    /**
     * 獲取所有咖啡的端點
     * @return 返回所有咖啡的列表
     */
    @GetMapping("/coffees")     // 映射 HTTP GET 請求到 /coffees 路徑
    Iterable<Coffee> getCoffees()
    {
        // 選用 Iterable<Coffee> 因為任何可迭代的型別(iterable type) 都能適切地提供所需的功能性。
        return coffees;     // 返回所有咖啡對象
    }


    /**
     * 根據 ID 獲取特定咖啡的端點
     * @param id 咖啡的唯一識別碼
     * @return 如果找到匹配的咖啡，返回包含該咖啡的 Optional；否則返回空的 Optional
     */
    @GetMapping("/coffees/{id}")        // 映射 HTTP GET 請求到 /coffees/{id} 路徑，{id} 是路徑變量
    Optional<Coffee> getCoffeeById(@PathVariable String id)     // @PathVariable 從 URL 路徑中提取 id 參數
    {
        // 遍歷咖啡列表，查找匹配的 ID
        // 如果找到，返回該咖啡的 Optional；否則返回空的
        for (Coffee coffee : coffees)
        {
            if (coffee.getId().equals(id))
            {
                return Optional.of(coffee);
            }
        }

        return Optional.empty();
    }


    /**
     * 新增一個咖啡的端點
     * @param coffee 要新增的咖啡對象
     * @return 返回新增的咖啡對象
     */
    @PostMapping("/coffees")
    Coffee postCoffee(@RequestBody Coffee coffee)
    {
        // 將新咖啡添加到列表中
        coffees.add(coffee);
        return coffee; // 返回新增的咖啡對象
    }


    /**
     * 更新一個咖啡的端點
     * 如果找到匹配的 ID，則更新其名稱；否則新增一個新的咖啡
     * @param id 咖啡的唯一識別碼
     * @param coffee 要更新的咖啡對象
     * @return 返回更新後的咖啡對象或新增的咖啡對象
     */
    @PutMapping("/coffees/{id}")
    // Coffee putCoffee(@PathVariable String id, @RequestBody Coffee coffee)
    // {
    //     int index = -1; // 用於記錄找到的咖啡索引
    //
    //     // 遍歷咖啡列表，查找匹配的 ID
    //     for (Coffee c: coffees)
    //     {
    //         if (c.getId().equals(id))
    //         {
    //             // 找到匹配的咖啡，更新其名稱
    //             index = coffees.indexOf(c);
    //             coffees.set(index, coffee);
    //             return coffee; // 返回更新後的咖啡對象
    //         }
    //     }
    //
    //     return (index == -1) ?
    //         postCoffee(coffee) :
    //         coffee;
    // }
    // ResponseEntity<Coffee> 是返回類型，這是 Spring 框架中的一個類，代表 HTTP 回應。它不僅包含回應主體（Coffee 物件），還包含狀態碼和標頭。
    ResponseEntity<Coffee> putCoffee(@PathVariable String id, @RequestBody Coffee coffee)
    {
        int coffeeIndex = -1;

        for(Coffee c: coffees)
        {
            if(c.getId().equals(id))
            {
                coffeeIndex = coffees.indexOf(c);
                coffees.set(coffeeIndex, coffee);
            }
        }

        return (coffeeIndex == -1) ?
                new ResponseEntity<>(postCoffee(coffee), HttpStatus.CREATED) :
                new ResponseEntity<>(coffee, HttpStatus.OK);
    }


    /**
     * 刪除一個咖啡的端點
     * 根據 ID 刪除匹配的咖啡對象
     * @param id 咖啡的唯一識別碼
     */
    @DeleteMapping("/coffees/{id}")
    void deleteCoffee(@PathVariable String id)
    {
        coffees.removeIf( c -> c.getId().equals(id));
    }

}


/*
Optional 是 Java 8 引入的一個容器類，用於處理可能為 null 的值，幫助開發者避免空指針異常（NullPointerException）。在這段程式碼中：

```java
Optional<Coffee> getCoffeeById(@PathVariable String id)
```

使用 `Optional<Coffee>` 作為返回類型有以下幾個好處：

1. **明確表達意圖**：明確告知調用者，此方法可能找不到對應的咖啡對象。

2. **避免 null 返回值**：不返回 null，而是返回一個可能為空的容器，強制調用者處理「找不到對象」的情況。

3. **提供安全的方法**：Optional 提供了如 `isPresent()`, `orElse()`, `orElseThrow()` 等方法，使處理可能不存在的值更加安全和優雅。

使用時，客戶端可以這樣處理返回值：

```java
// 安全地取得咖啡，如果不存在則提供默認值
Coffee coffee = getCoffeeById("some-id").orElse(new Coffee("默認咖啡"));

// 或者條件性處理
getCoffeeById("some-id").ifPresent(coffee -> {
    // 只有當咖啡存在時才執行這裡的代碼
    System.out.println("找到咖啡: " + coffee.getName());
});

// 如果不存在則拋出異常
Coffee coffee = getCoffeeById("some-id")
    .orElseThrow(() -> new NotFoundException("找不到指定的咖啡"));
```

這種模式幫助開發出更加健壯的代碼，避免了許多常見的 null 相關錯誤。

========================================================

`Iterable` 是 Java 中的一個核心接口(interface)，定義在 `java.lang` 包中。它具有以下重要特點：

1. **可迭代性**：實現了 `Iterable` 接口的類可以使用 Java 的 for-each 循環進行遍歷
   ```java
   for (Coffee coffee : getCoffees()) {
       // 處理每個咖啡對象
   }
   ```

2. **單一方法接口**：它只定義了一個方法 `iterator()`，返回一個 `Iterator` 對象
   ```java
   Iterator<T> iterator();
   ```

3. **集合框架的基礎**：Java 集合框架中的 `Collection` 接口擴展了 `Iterable`，所以所有集合類（如 `List`、`Set` 等）都是可迭代的

在你的代碼中，`Iterable<Coffee> getCoffees()` 表示：
- 方法返回一個可迭代的咖啡對象集合
- 實際上返回的是 `coffees`，它是一個 `ArrayList<Coffee>`（`ArrayList` 實現了 `List`，而 `List` 繼承了 `Iterable`）
- 使用 `Iterable` 作為返回類型而不是具體的 `List` 提供了更好的抽象，使方法不綁定到特定的集合實現

這種設計遵循了「依賴抽象而非具體實現」的良好實踐，允許將來可能更改底層集合類型而不影響接口。

========================================================

`coffees.removeIf(c -> c.getId().equals(id));` 是一個使用 Java 8 引入的集合方法來刪除特定元素的語句。

這行程式碼：

1. `removeIf()` 是 Java 集合類型中的方法，用於根據條件刪除元素
2. 它接受一個斷言(predicate)作為參數，這個斷言是一個返回布林值的函數
3. `c -> c.getId().equals(id)` 是一個 lambda 表達式：
   - `c` 代表集合中的每個 `Coffee` 物件
   - 對每個咖啡物件檢查它的 ID 是否等於傳入的 `id` 參數
   - 如果相等則返回 `true`，該咖啡物件將被從集合中移除

這是在 `@DeleteMapping("/coffees/{id}")` 方法中實現刪除操作的簡潔方式，它比傳統的循環遍歷和手動刪除更加簡潔高效。當客戶端發送 DELETE 請求到 `/coffees/{id}` 端點時，這段程式碼會移除具有指定 ID 的咖啡物件。

========================================================

這行程式碼是一個 Spring Boot REST API 方法的聲明，用於處理 HTTP PUT 請求來更新咖啡資源。具體說明：

1. `ResponseEntity<Coffee>` 是返回類型，這是 Spring 框架中的一個類，代表 HTTP 回應。它不僅包含回應主體（Coffee 物件），還包含狀態碼和標頭。

2. `putCoffee` 是方法名稱，指示此方法的目的是更新一個咖啡資源。

3. `@PathVariable String id` 表示從 URL 路徑中提取一個名為 `id` 的變數（例如從 `/coffees/{id}` 中獲取）。

4. `@RequestBody Coffee coffee` 表示從 HTTP 請求的內容主體中反序列化一個 Coffee 物件。

這個方法對應於 `@PutMapping("/coffees/{id}")` 端點，允許客戶端透過 PUT 請求更新特定 ID 的咖啡資源。與普通返回 Coffee 物件的方法不同，使用 ResponseEntity 可以更精確地控制 HTTP 回應，包括設置不同的狀態碼（如 201 CREATED 或 200 OK）。

========================================================

* */