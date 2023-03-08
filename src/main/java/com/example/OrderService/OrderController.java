package com.example.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OrderController {

    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping("/order")
    public String order(
            @RequestParam String userId,
            @RequestParam String productId,
            @RequestParam String price
    ) {

        // 특정 키에 하나의 엔트리를 추가할 것이고 그 안에 userId, productId, price field 및 value 추가
        Map fieldMap = new HashMap<String, String>();
        fieldMap.put("userId", userId);
        fieldMap.put("productId", productId);
        fieldMap.put("price", price);

        // stream 에 추가해준다.
        // 주문 관련 키를 명확하게 명시해준다.
        redisTemplate.opsForStream().add("order-events", fieldMap);

        System.out.println("Order created.");
        return "ok";
    }
}
