package com.trablock.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    @ResponseBody
    public String home() {
        return "Hello world";
    }

    @GetMapping("/api/test")
    public String test() {
        return "test api result return!!";
    }

    @GetMapping("/api/test2")
    public String test2() {
        return "test 2 api result return!!";
    }

}
