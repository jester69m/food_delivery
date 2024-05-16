package com.shop.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String index() {
        return "Hello World! no auth";
    }

    @GetMapping("/verify")
    public String verify(@RequestHeader("Authorization") String authToken){
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("Create restTemplate");
        ResponseEntity<?> response = restTemplate.getForEntity("http://localhost:8000/user/hello", String.class);
        System.out.println("make req" + response.getStatusCode());
        return "Verify! no auth";
    }

    @GetMapping("/hello1")
    public String index1() {
        return "Hello World 1! user only";
    }

    @GetMapping("/hello2")
    public String index2() {
        return "Hello World 2! admin only";
    }

    @GetMapping("/hello3")
    public String index3() {
        return "Hello World 3! super admin only";
    }


}
