package com.materna.date4u.demo.injection.point;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.AnnotatedElement;
import java.security.SecureRandom;
import java.util.Random;

import static java.util.Objects.nonNull;

@Component
public class MyInjectionPointDemo {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    @CryptographicallyStrong
    private Random random;


    @Autowired
    @CryptographicallyStrong
    void setRnd1(Random random) {
        log.debug("setRnd1 wurde aufgerufen mit {} isSecure={}", random, random instanceof SecureRandom);
    }

    @Autowired
    void setRnd2(@CryptographicallyStrong Random random) {
        log.debug("setRnd2 wurde aufgerufen mit {} isSecure={}", random, random instanceof SecureRandom);
    }


    @Retention(RetentionPolicy.RUNTIME)
    public @interface CryptographicallyStrong {

    }

    @Configuration(proxyBeanMethods = false)
    static class MyRandomFactory {
        private final Logger log = LoggerFactory.getLogger(getClass());

        @Bean
        @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
            //Damit wird die Bean ein Prototyp und kein Singleton
        Random random(InjectionPoint injectionPoint) {
            boolean isAnnotated = nonNull(injectionPoint.getAnnotation(CryptographicallyStrong.class));

            // Prüft, ob die Setter-Methoden annotiert sind
            boolean isAnnotatedMember = injectionPoint.getMember() instanceof AnnotatedElement;
            boolean annotationPresent = isAnnotatedMember
                    ? ((AnnotatedElement) injectionPoint.getMember()).isAnnotationPresent(CryptographicallyStrong.class)
                    : false;

            log.debug("isAnnotated {}, isAnnotatedMember {}, annotationPresent {}", isAnnotated, isAnnotatedMember, annotationPresent);
            if (isAnnotated || (isAnnotatedMember && annotationPresent))
                return new SecureRandom();
            return new Random();
        }
    }
}
