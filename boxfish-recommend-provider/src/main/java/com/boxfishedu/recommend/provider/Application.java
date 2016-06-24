package com.boxfishedu.recommend.provider;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
@EnableJpaRepositories(includeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = org.springframework.data.jpa.repository.JpaRepository.class)})
@EnableMongoRepositories(includeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = org.springframework.data.mongodb.repository.MongoRepository.class)})
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}