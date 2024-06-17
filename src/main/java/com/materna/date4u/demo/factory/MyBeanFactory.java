package com.materna.date4u.demo.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

// Es lassen sich auch Factories mit @Component erzeugt. Allerdings sind dies dann Lite-Beans, die eingeschränkt sind
@Configuration(proxyBeanMethods = false)
public class MyBeanFactory {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public MyBeanFactory() {
        log.debug("created MyBeanFactory Bean");
    }

    // Mit @Bean annotierte Methoden werden zur Bean Erzeugung von Spring aufgerufen
    // RÜckgabetyp sollte so Präzise wie möglich sein, da Spring gewisse Informationen nicht zur Laufzeit
    // auswertet sondern aus dem Bytecode extrahiert.
    // BeanName=method_name_will_be_bean_name
    @Bean
    Foo method_name_will_be_bean_name() {
        log.debug("created method_name_will_be_bean_name Bean");
        return new Foo();
    }

    @Bean
    @Lazy(value = true)
    Foo method_name_will_be_bean_name2() {
        log.debug("created method_name_will_be_bean_name Bean");
        return new Foo();
    }

    public class Foo {
        private final Logger log = LoggerFactory.getLogger(getClass());

        public Foo() {
            log.debug("created Foo Bean");
        }
    }
}
