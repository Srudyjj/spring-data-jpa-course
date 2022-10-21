package com.example.demo;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, StudentIdCardRepository studentIdCardRepository, StudentService studentService) {
        return args -> {
            Faker faker = new Faker();
            FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-GB"), new RandomService());
            Student student = generateRandomStudent(faker, fakeValuesService);
            student.addBook(new Book("Clean Code", LocalDateTime.now().minusDays(10)));
            student.addBook(new Book("Think and Grow Rich", LocalDateTime.now().minusDays(1)));
            student.addBook(new Book("Spring Boot in Action", LocalDateTime.now().minusYears(10)));

            StudentIdCard studentIdCard = new StudentIdCard("123456789", student);

            student.setStudentIdCard(studentIdCard);

            student.addEnrolment(new Enrolment(
                    new EnrolmentId(1L, 1L),
                    student,
                    new Course("Computer Science", "IT"),
                    LocalDateTime.now()));
            student.addEnrolment(new Enrolment(
                    new EnrolmentId(1L, 2L),
                    student,
                    new Course("Amigoscode Spring Data JPA", "IT"),
                    LocalDateTime.now()));

//            student.enrolToCourse(
//                    new Course("Computer Science", "IT"));
//            student.enrolToCourse(
//                    new Course("Amigoscode Spring Data JPA", "IT"));

            studentRepository.save(student);

            studentService.processBooks();
//            studentRepository.findById(1L).ifPresent(s -> {
//                System.out.println("Fetching books ...");
//                List<Book> books = s.getBooks();
//                books.forEach(book -> {
//                    System.out.println(s.getFirstName() + " borrowed " + book.getBookName());
//                });
//            });


//            Student studentFromDb = studentRepository.findById(1L).orElseThrow();
//
//            System.out.println("fetch book lazy...");
//            List<Book> books = studentFromDb.getBooks();
//            System.out.println(books.get(0));
//            books.forEach(book -> {
//                System.out.println(studentFromDb.getFirstName() + " borrowed " + book.getBookName());
//            });

//
//            studentIdCardRepository.findById(1L).ifPresent(System.out::println);

//            studentRepository.deleteById(1L);
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
        studentRepository.findAll(sort).forEach(student -> System.out.println(student.getFirstName() + " " + student.getAge()));
    }

    private Student generateRandomStudent(Faker faker, FakeValuesService fakeValuesService) {
        Student student = new Student(faker.name().firstName(), faker.name().lastName(), fakeValuesService.bothify("????##@gmail.com"), faker.number().numberBetween(17, 60));
        return student;
    }

}
