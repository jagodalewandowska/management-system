package com.project.controller;
import com.project.model.Projekt;
import com.project.model.Student;
import com.project.service.ProjektService;
import com.project.service.ServiceUtil;
import com.project.service.StudentService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import java.net.URI;

@Controller
public class StudentController {
    private StudentService studentService;
//    private ProjektService projektService;
    public StudentController(StudentService studentService, ProjektService projektService) {
        this.studentService = studentService;
//        this.projektService = projektService;

    }
    @GetMapping("/studentList")
    public String studentList(Model model, Pageable pageable) {
        model.addAttribute("studenci", studentService.getStudenci(pageable).getContent());
//        model.addAttribute("projekty", projektService.getProjekty(pageable).getContent());
        return "studentList";
    }
    @GetMapping("/studentEdit")
    public String studentEdit(@RequestParam(required = false) Integer studentId, Model model) {
//                              , Integer projektId) {
        if (studentId != null) {
            model.addAttribute("student", studentService.getStudent(studentId).get());
//            model.addAttribute("projekt", projektService.getProjekt(projektId).get());
        } else {
            Student student = new Student();
//            Projekt projekt = new Projekt();
            model.addAttribute("student", student);
//            model.addAttribute("projekt", projekt);
        }
        return "studentEdit";
    }

    @PostMapping(path = "/studentEdit")
    public String studentEditSave(@ModelAttribute @Valid Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "studentEdit";
        }
        try {
            student = studentService.setStudent(student);
        } catch (HttpStatusCodeException e) {
            bindingResult.rejectValue(Strings.EMPTY, String.valueOf(e.getStatusCode().value()), e.getStatusCode().toString());
            return "studentEdit";
        }
        return "redirect:/studentList";
    }

    @PostMapping(params="cancel", path = "/studentEdit")
    public String studentEditCancel() { // żądanie będzie zawierało parametr 'cancel'
        return "redirect:/studentList";
    }

    @PostMapping(params="delete", path = "/studentEdit")
    public String studentEditDelete(@ModelAttribute Student student) {
        studentService.deleteStudent(student.getStudentId());
        return "redirect:/studentList";
    }


    @GetMapping("/studentList/search")
    public String searchProjectList(Model model, @RequestParam Integer size, @RequestParam String nazwisko) {
        Pageable pageable = PageRequest.of(0, size);
        model.addAttribute("studenci", studentService.searchByNazwisko(nazwisko,pageable).getContent());
        model.addAttribute("size", size);
        model.addAttribute("page", 0);
        model.addAttribute("nazwisko", nazwisko);
        model.addAttribute("nextPage", 1);
        model.addAttribute("previousPage", 0);
        return "studentList";
    }
}