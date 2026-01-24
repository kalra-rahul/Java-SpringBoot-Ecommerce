package com.example.demo;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public HelloResponse hello(){
        return new HelloResponse("Hello");
    }

    @GetMapping("/hello/{name}")
    public HelloResponse pathGetHello(@PathVariable String name){
        return new HelloResponse("Hello "+ name);
    }

    @PostMapping("/hello")
    public HelloResponse helloPost(@RequestBody String name) {
        return new HelloResponse("Hello" + " " + name + "!");
    }
}
