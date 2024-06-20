package com.materna.date4u.interfaces.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoRestController {


    @GetMapping("/tip")
    String sortQuote() {
        System.out.println("Get Tip");
        return "Die with memories, not dreams.";
    }

    @GetMapping("/stats")
    String stats() {

        return "MyStats";
    }
}
