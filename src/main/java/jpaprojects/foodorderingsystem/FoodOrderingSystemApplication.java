package jpaprojects.foodorderingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FoodOrderingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodOrderingSystemApplication.class, args);
    }

}
