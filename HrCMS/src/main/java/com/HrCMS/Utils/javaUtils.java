package com.HrCMS.Utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class javaUtils {
    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus status){
        return new ResponseEntity<String>("{\n\"Message\":\""+responseMessage+"\"\n}", status);
    }

    public static ResponseEntity<String> gettokenResponseEntity(String responseMessage, String token, HttpStatus status){
        return new ResponseEntity<String>("{\"Message\":\""+responseMessage+"\" \n \"Token\":\""+token+"\"}", status);
    }
}
