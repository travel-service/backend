package com.trablock.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class ResourceController {
    private final RequestMappingHandlerMapping handlerMapping;

    @GetMapping("/requests")
    public String show() {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        return handlerMethods.entrySet()
                .stream()
                .map(String::valueOf)
                .sorted()
                .collect(Collectors.joining("<br />"));
    }
}
