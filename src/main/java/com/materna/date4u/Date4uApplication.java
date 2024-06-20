package com.materna.date4u;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(
)
public class Date4uApplication {

    // slf4j ist nur eine Fassade. Das konkrete Logging-Framework dahinter kann ausgetauscht werden.
    private final Logger log = LoggerFactory.getLogger(getClass());

    public Date4uApplication() {
        log.debug("created Date4uApplication Bean");
    }

    public static void main(String[] args) {

        //1. Alternative die App zu starten. Kann dadurch vorkonfiguriert werden
//        SpringApplication app = new SpringApplication(Date4uApplication.class);
//        app.setHeadless(false); // Bei GUI-Anwendungen muss die Anwendung im Headless-Mode gestartet werden
//        app.setBannerMode(Banner.Mode.OFF); //Wird von den application.properties überschrieben
//        app.setLogStartupInfo(false);
//        app.run(args);

        // 2. Alternative: Builder Pattern
//        ConfigurableApplicationContext context = new SpringApplicationBuilder(Date4uApplication.class)
//                .headless(false)
//                .bannerMode(Banner.Mode.OFF)
//                .logStartupInfo(false)
//                .run(args);


        // Hier wird der ApplicationContext erzeugt.
        ConfigurableApplicationContext context = SpringApplication.run(Date4uApplication.class, args);
//
//        // Zugriff auf alle managed Beans. ConfigurableApplicationContext erbt von ListableBeanFactory
//        List<String> allBeanNames = Arrays.stream(context.getBeanDefinitionNames()).sorted().toList();
//        boolean containsBean = context.containsBean("date4uApplication");
//        Date4uApplication date4uApplication = (Date4uApplication) context.getBean("date4uApplication");
//        date4uApplication = context.getBean("date4uApplication", Date4uApplication.class);
//        date4uApplication = context.getBean(Date4uApplication.class);
//        allBeanNames.forEach(System.out::println);
//        System.out.println("Size All Beans: " + allBeanNames.size());
    }

}
