package com.project.init;

import com.project.model.Projekt;
import com.project.model.Role;
import com.project.model.Student;
import com.project.model.Zadanie;
import com.project.repository.ProjektRepository;
import com.project.repository.StudentRepository;
import com.project.repository.ZadanieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;

import static com.project.model.Role.ADMIN;

@Component
@Slf4j
public class DatabaseInitializer implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final ProjektRepository projektRepository;
    private final ZadanieRepository zadanieRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public DatabaseInitializer(StudentRepository studentRepository, ProjektRepository projektRepository, ZadanieRepository zadanieRepository, BCryptPasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.projektRepository = projektRepository;
        this.zadanieRepository = zadanieRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!studentRepository.existsByEmail("administrator@admin.admin")) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("---------------------------------------------------");
            System.out.println("Czy chcesz zainicjować administratora? (Y/N): ");
            System.out.println("---------------------------------------------------");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) {
                //          Dodawanie administratora
                Student student = new Student();
                student.setImie("Admin");
                student.setNazwisko("Administrator");
                student.setEmail("administrator@admin.admin");
                student.setPassword(passwordEncoder.encode("admin123"));
                student.setRole(ADMIN);
                student.setNrIndeksu("987654");
                student.setStacjonarny(false);
                studentRepository.save(student);
            }
        }


        if (!projektRepository.existsByNazwa("Przykładowy projekt 1")) {
            Scanner scanner2 = new Scanner(System.in);
            System.out.println("---------------------------------------------------");
            System.out.println("Czy chcesz zainicjować projekty, studentów i zadania? (Y/N): ");
            System.out.println("---------------------------------------------------");
            String input2 = scanner2.nextLine().trim().toLowerCase();
            if (input2.equals("y") || input2.equals("yes")) {
                for (int i = 1; i <= 10; i++) {

                    // Dodawanie projektów
                    Projekt projekt = new Projekt();
                    projekt.setNazwa("Przykładowy projekt " + i);
                    projekt.setOpis("Przykładowy opis projektu " + i);
                    projekt.setDataOddania(LocalDate.now().plusDays(i));

                    projektRepository.save(projekt);

                    // Dodawanie zadań
                    Zadanie zadanie = new Zadanie();
                    zadanie.setNazwa("Zadanie " + i);
                    zadanie.setOpis("Opis zadania " + i);
                    zadanie.setKolejnosc(i);
                    zadanie.setProjekt(projekt);

                    zadanieRepository.save(zadanie);

                    // Dodawanie studentów
                    Student student = new Student();
                    student.setImie("Imie" + i);
                    student.setNazwisko("Nazwisko" + i);
                    student.setEmail("email" + i + "@pbs.edu.pl");
                    student.setPassword(passwordEncoder.encode("student" + i));
                    student.setRole(Role.USER);
                    student.setNrIndeksu(String.valueOf(1234567+i));
                    student.setStacjonarny(i % 2 == 0);

                    studentRepository.save(student);
                }
                System.out.println("Dodano 10 projektów, studentów i zadań!");
            }
        }
        System.out.println("Inicjalizacja zakończona!");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
