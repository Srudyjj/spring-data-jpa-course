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
            System.out.print("Number of students: ");
            System.out.println(studentRepository.count());
            studentRepository.findById(2l).ifPresentOrElse(System.out::println, () -> System.out.println("Not found"));
            studentRepository.findById(3l).ifPresentOrElse(System.out::println, () -> System.out.println("Not found"));

            System.out.println("Select all");
            studentRepository.findAll().forEach(System.out::println);

            System.out.println("Delete by id");
            studentRepository.deleteById(1l);

            System.out.print("Number of students: ");
            System.out.println(studentRepository.count());
        };
    }

}
