package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            var maria = new Student("Maria", "Jones", "mrjones@gmail.com", 27);
            var ahmed = new Student("Ahmed", "Ali", "ahali@gmail.com", 27);
            System.out.println("Adding...");
            studentRepository.saveAll(List.of(maria, ahmed));

            studentRepository.findStudentByEmail("mrjones@gmail.com")
                    .ifPresentOrElse(System.out::println, () -> System.out.println("Not found"));

            studentRepository
                    .findStudentsByFirstNameEqualsAndAgeIsGreaterThan("Maria", 26)
                    .forEach(System.out::println);

            studentRepository
                    .findStudentsByFirstNameEqualsAndAgeIsGreaterThanNative("Maria", 26)
                    .forEach(System.out::println);

            studentRepository.deleteStudentById(1l);
        };
    }

}
