package org.example.demo.controller;

// 第四章 讓你的 Spring Boot APP 存取資料庫
// 這個範例採用第三章的範例加入 H2 資料庫的功能。

// https://chikuwacode.github.io/articles/spring-boot-swagger-ui-openapi-documentation/
// 【Spring Boot】第13課－使用 Swagger UI 製作 API 文件與呼叫介面

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag; // Swagger 註解，用於生成 API 文檔o
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.Parameter; // 用於描述方法參數的 Swagger 註解
import io.swagger.v3.oas.annotations.media.ExampleObject; // 用於提供參數範例的 Swagger 註解


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.demo.model.Coffee;

/**
 * REST API 控制器
 * 負責處理與咖啡相關的 HTTP 請求
 */
@Tag(name = "Coffee API", description = "提供咖啡相關的 API") // Swagger 註解，用於生成 API 文檔
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
    @Operation(summary = "獲取所有咖啡", description = "返回所有咖啡的列表") // Swagger 註解，用於生成 API 文檔
    @ApiResponses( value = {        // 定義可能的 HTTP 響應狀態碼和描述
           @ApiResponse( responseCode = "200", description = "成功獲取所有咖啡", content = @Content(schema = @Schema(implementation = Coffee.class))),
           @ApiResponse( responseCode = "404", description = "找不到端點")
    })
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
    @Operation(summary = "根據 ID 獲取特定咖啡", description = "根據咖啡的唯一識別碼返回對應的咖啡對象") // Swagger 註解，用於生成 API 文檔
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "成功獲取特定咖啡", content = @Content(schema = @Schema(implementation = Coffee.class))),
    })
    @GetMapping("/coffees/{id}")        // 映射 HTTP GET 請求到 /coffees/{id} 路徑，{id} 是路徑變量
    Optional<Coffee> getCoffeeById(
            @Parameter(description = "咖啡的唯一識別碼", example = "1f0cc0fc-f672-4fc1-a062-3dfaa514dcc3", required = true) // Swagger 註解，描述此參數
            @PathVariable String id)     // @PathVariable 從 URL 路徑中提取 id 參數
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
    @Operation(summary = "新增一組咖啡", description = "將新的咖啡對象添加到列表中") // Swagger 註解，用於生成 API 文檔
    @ApiResponses( value = {
            @ApiResponse( responseCode = "201", description = "成功新增咖啡", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Coffee.class))),
            @ApiResponse( responseCode = "400", description = "請求錯誤，可能是缺少必要的字段")
    })
    @PostMapping("/coffees")
    Coffee postCoffee(
            @Parameter(description = "要新增的咖啡對象", examples = { @ExampleObject( name = "拿鐵咖啡", value = "{\"name\" : \"拿鐵咖啡\", \"id\" : \"99999\"}") }, required = true)
            @RequestBody Coffee coffee)
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
    @Operation(summary = "更新一個咖啡", description = "根據 ID 更新咖啡對象，如果不存在則新增") // Swagger 註解，用於生成 API 文檔
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "成功更新咖啡", content = @Content(schema = @Schema(implementation = Coffee.class))),
            @ApiResponse( responseCode = "201", description = "成功新增咖啡，因為找不到匹配的 ID")
    })
    @PutMapping("/coffees/{id}")
    ResponseEntity<Coffee> putCoffee(
            @Parameter(description = "咖啡的唯一識別碼", example = "1f0cc0fc-f672-4fc1-a062-3dfaa514dcc3", required = true)
            @PathVariable String id,
            @Parameter(description = "咖啡的名稱", example = "Cafe Ganador", required = true)
            @RequestBody Coffee coffee)
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
    @Operation(summary = "刪除一個咖啡", description = "根據 ID 刪除對應的咖啡對象") // Swagger 註解，用於生成 API 文檔
    @ApiResponses( value = {
            @ApiResponse( responseCode = "204", description = "成功刪除咖啡"),
            @ApiResponse( responseCode = "404", description = "找不到指定的咖啡")
    })
    @DeleteMapping("/coffees/{id}")
    void deleteCoffee(
            @Parameter(description = "咖啡的唯一識別碼", example = "1f0cc0fc-f672-4fc1-a062-3dfaa514dcc3", required = true)
            @PathVariable String id)
    {
        coffees.removeIf( c -> c.getId().equals(id));
    }


    /**
     * 根據名稱獲取特定咖啡的端點
     * @param name 咖啡的名稱
     * @return 如果找到匹配的咖啡，返回包含該咖啡的 Optional；否則返回空的 Optional
     */
    @Operation(summary = "根據名稱獲取特定咖啡", description = "根據咖啡的名稱返回對應的咖啡對象") // Swagger 註解，用於生成 API 文檔
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "成功獲取特定咖啡", content = @Content(schema = @Schema(implementation = Coffee.class))),
            @ApiResponse( responseCode = "404", description = "找不到指定的咖啡")
    })
    @GetMapping("/coffees/name/{name}")     // 不能使用 @GetMapping("/coffees/{name}")，因為 {name} 和先前的 {id} 會分不出來。
    Optional<Coffee> getCoffeeByName(
            @Parameter(description = "咖啡的名稱", example = "Cafe Lareno", required = true)
            @PathVariable String name)     // @PathVariable 從 URL 路徑中提取 id 參數
    {
        // 遍歷咖啡列表，查找匹配的 Name
        // 如果找到，返回該咖啡的 Optional；否則返回空的

        for (Coffee coffee : coffees)
        {
            if (coffee.getName().equals(name))
            {
                return Optional.of(coffee);
            }
        }

        return Optional.empty();
    }

    // 傳來的 json 是很多 Coffee 物件的集合
    /**
     * 根據名稱獲取特定咖啡的端點
     * @param coffees 要新增的咖啡對象集合
     * @return 返回新增的咖啡對象集合
     *
     */
    @Operation(summary = "新增咖啡組合", description = "將新的咖啡 Json 集合添加到列表中") // Swagger 註解，用於生成 API 文檔
    @ApiResponses( value = {
            @ApiResponse( responseCode = "201", description = "成功新增咖啡組合", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Coffee.class)))),
            @ApiResponse( responseCode = "400", description = "請求錯誤，可能是缺少必要的字段")
    })
    @PostMapping("/coffees/batch")
    List<Coffee> postCoffees(
            @Parameter(description = "咖啡的名稱", example = "Cafe Lareno", required = true)
            @RequestBody List<Coffee> coffees)
    {
        // 將新咖啡添加到列表中
        this.coffees.addAll(coffees);
        return coffees; // 返回新增的咖啡對象集合
    }

}


/*

========================================================

* */