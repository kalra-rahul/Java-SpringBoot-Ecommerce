package com.example.demo;

public class HelloResponse {
    private String message;

    public HelloResponse(String message){
        this.message= message;
    }

    public String getMessage(){
        return this.message;
    }

    public void setMessage(String message){
        this.message = message;
    }

}
