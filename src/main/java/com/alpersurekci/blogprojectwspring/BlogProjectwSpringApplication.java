package com.alpersurekci.blogprojectwspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
@SpringBootApplication(exclude =  {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class
} )
*/

@SpringBootApplication//(exclude = {
     //   org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
/*        //org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class
}
)*/
public class BlogProjectwSpringApplication {

    public static void main(String[] args) {

        SpringApplication.run(BlogProjectwSpringApplication.class, args);


        System.setProperty("spring.devtools.restart.enabled", "false");

        System.setProperty("java.awt.headless", "false");
    }

}
