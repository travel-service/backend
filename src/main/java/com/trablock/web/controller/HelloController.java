package com.trablock.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
public class HelloController {
    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }


    @GetMapping("/test-data")
    public String testV1() {

        String url = "http://127.0.0.1:5000/test";
        String sb = "";

        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

            String line = null;

            while ((line = br.readLine()) != null) {
                sb = sb + line + "\n";
            }
            System.out.println("========br======" + sb.toString());
            if (sb.toString().contains("ok")) {
                System.out.println("test");

            }
            br.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb;
    }

    @GetMapping("/test-data2")
    public testModel testV2() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> model = restTemplate.getForObject("http://127.0.0.1:5000/test", Map.class);
//        System.out.println("model = " + model);

        testModel result = new testModel(model.get("date"), model.get("rank"));

        return result;
    }

    @GetMapping("/test-data3")
    public Map<String, String> testV3() {
        Map<String, String> model = restTemplate.getForObject("http://127.0.0.1:5000/test", Map.class);
        System.out.println("model = " + model);
        return model;
    }

    @GetMapping("/test-data4")
    public Map<String, Object> testV4() {
        restTemplate.getInterceptors().add((request, body, execution) -> {
            ClientHttpResponse response = execution.execute(request,body);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return response;
        });

        Map<String, Object> model = restTemplate.getForObject("http://127.0.0.1:5000/test", Map.class);
        System.out.println("model = " + model);

        return model;
    }
}
