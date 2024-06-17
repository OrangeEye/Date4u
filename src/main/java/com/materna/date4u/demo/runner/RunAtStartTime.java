package com.materna.date4u.demo.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


/**
 * Ein Runner, welcher beim Start der Applikation ausgeführt wird (nach der Interaktiven Shell). Eine Alternative wäre der CommandLineRunner, welcher die String[] args
 * aus der main() bekommt. Der ApplicationRunner bekommt stattdessen die ApplicationArguments, wodurch die Argumente leichter zu verabeiten sind.
 * zB. nonoption --option1=value1 --option2=value2 --option3
 * <p>
 * Diese Klasse ist nur eine Demo und nicht Teil der Anwendung.
 */
@Component
public class RunAtStartTime implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        System.out.println(args.getNonOptionArgs());
//        System.out.println(args.getOptionNames());
//        System.out.println(Arrays.toString(args.getSourceArgs()));
//        args.getOptionNames().forEach(args::getOptionValues);
    }
}
