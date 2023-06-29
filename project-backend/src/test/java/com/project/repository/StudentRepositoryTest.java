package com.project.repository;

import com.project.model.Student;
import com.project.service.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

// TODO
@ExtendWith(MockitoExtension.class)
public class StudentRepositoryTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService; // Concrete implementation of the interface
    @Test
    public void testFindByNazwiskoStartsWithIgnoreCase() {
        String nazwisko = "Smith";
        Pageable pageable = Pageable.unpaged();
        List<Student> allStudents = new ArrayList<>();
        allStudents.add(new Student("John", "Doe", "12345612", "doejohn@gmail.com", true));
        allStudents.add(new Student("Janet", "Smith", "16445612", "janet@gmail.com", false));
        allStudents.add(new Student("Jina", "Smith", "1545612", "jina@gmail.com", false));

        List<Student> expectedStudents = allStudents.stream()
                .filter(student -> student.getNazwisko().equalsIgnoreCase(nazwisko))
                .collect(Collectors.toList());

        Page<Student> expectedPage = new PageImpl<>(expectedStudents);

        when(studentRepository.findByNazwiskoStartsWithIgnoreCase(nazwisko, pageable)).thenReturn(expectedPage);

        Page<Student> result = studentService.searchByNazwisko(nazwisko, pageable);

        assertEquals(Collections.emptyList(), result.getContent());

        System.out.println("Oczekiwani studenci:");
        expectedStudents.forEach(student -> System.out.println("Imię: " + student.getImie() + " Nazwisko: " + student.getNazwisko()));

        System.out.println("Znalezieni studenci:");
        result.getContent().forEach(student -> System.out.println("Imię: " + student.getImie() + " Nazwisko: " + student.getNazwisko()));

        System.out.println("Test 'findByNazwiskoStartsWithIgnoreCase' zakończony pomyślnie!");
    }

}
