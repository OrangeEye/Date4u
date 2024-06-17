package com.materna.date4u.demo.inherit;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.UUID;

public abstract class MyInheritDemo {
    @Bean
    UUID uuid() {
        System.out.println("UUID");
        return UUID.randomUUID();
    }

    @Component
    public static class InheritDemo extends MyInheritDemo {
    }

    @Component
    public static class InheritDemo2 extends MyInheritDemo {
    }
}
