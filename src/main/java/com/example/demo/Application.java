package com.example.demo;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, StudentIdCardRepository studentIdCardRepository) {
        return args -> {
            Faker faker = new Faker();
            FakeValuesService fakeValuesService = new FakeValuesService(
                    new Locale("en-GB"), new RandomService());
            StudentIdCard studentIdCard = new StudentIdCard("123456789", generateRandomStudent(faker, fakeValuesService));
            studentIdCardRepository.save(studentIdCard);
//
//            studentRepository.findById(1L).ifPresent(System.out::println);
//
//            studentIdCardRepository.findById(1L).ifPresent(System.out::println);

            studentRepository.deleteById(1L);
//            studentIdCardRepository.deleteById(1L);
//
//            List<Student> students = Stream
//                    .generate(() -> generateRandomStudent(faker, fakeValuesService))
//                    .limit(30)
//                    .collect(Collectors.toList());
//            studentRepository.saveAll(students);

//            PageRequest pageRequest = PageRequest.of(2, 5, Sort.by("firstName"));
//            Page<Student> studentPage = studentRepository.findAll(pageRequest);
//            System.out.println(studentPage);
//            sorting(studentRepository);
        };
    }

    private void sorting(StudentRepository studentRepository) {
        Sort sort = Sort.by("firstName").ascending().and(Sort.by("age").descending());
        studentRepository
                .findAll(sort)
                .forEach(student -> System.out.println(student.getFirstName() + " " + student.getAge()));
    }

    private Student generateRandomStudent(Faker faker, FakeValuesService fakeValuesService) {
        Student student = new Student(
                faker.name().firstName(),
                faker.name().lastName(),
                fakeValuesService.bothify("????##@gmail.com"),
                faker.number().numberBetween(17, 60));
        return student;
    }

}
