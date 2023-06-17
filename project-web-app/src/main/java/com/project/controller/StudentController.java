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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import java.net.URI;

@Controller
@RequestMapping("/app")
public class StudentController {

    private Sort.Direction getSortDirection(String direction) {
        direction = direction.toLowerCase();
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    private StudentService studentService;
    public StudentController(StudentService studentService, ProjektService projektService) {
        this.studentService = studentService;

    }
    @GetMapping("/studentList")
    public String studentList(Model model, Pageable pageable) {
        try {
            model.addAttribute("studenci", studentService.getStudenci(pageable).getContent());
            model.addAttribute("size",5);
            model.addAttribute("page",0);
            model.addAttribute("nextPage",1);
            model.addAttribute("previousPage",0);
            model.addAttribute("reverseSortDir", "desc");
            return "redirect:/app/studentList/results?size=5&page=0&sort=studentId&order=asc";
        } catch (HttpStatusCodeException e) {
            return "404";
        }
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
        return "redirect:/app/studentList";
    }

    @PostMapping(params="cancel", path = "/studentEdit")
    public String studentEditCancel() { // żądanie będzie zawierało parametr 'cancel'
        return "redirect:/studentList";
    }

    @PostMapping(params="delete", path = "/studentEdit")
    public String studentEditDelete(@ModelAttribute Student student) {
        studentService.deleteStudent(student.getStudentId());
        return "redirect:/app/studentList";
    }


    @GetMapping("/studentList/search")
    public String searchProjectList(Model model, @RequestParam Integer size,
                                    @RequestParam String nazwisko, @RequestParam String sort,
                                    @RequestParam String order, @RequestParam Integer page) {
        Pageable pageable = PageRequest.of(page, size);
        model.addAttribute("studenci", studentService.searchByNazwisko(nazwisko,pageable).getContent());
        model.addAttribute("size", size);
        model.addAttribute("nazwisko", nazwisko);
        model.addAttribute("sort", sort);
        model.addAttribute("order", order);
        model.addAttribute("page", page.equals(0) ? 0 : page);
        model.addAttribute("nextPage", page.equals(0)? 1 : page+1);
        model.addAttribute("previousPage", page.equals(0)? 0 : page-1);
        model.addAttribute("reverseSortDir", order.equals("asc") ? "desc" : "asc");
        return "studentList";
    }

    @GetMapping("/studentList/results")
    public String sortProjectList(Model model, @RequestParam String sort, @RequestParam String order,
                                  @RequestParam Integer size, @RequestParam Integer page) {
        Pageable pageable = PageRequest.of(page, size, getSortDirection(order), sort);
        model.addAttribute("studenci", studentService.getStudenci(pageable).getContent());
        model.addAttribute("order", order);
        model.addAttribute("size", size);
        model.addAttribute("sort", sort);
        ProjectController.getTheRest(model, page, order);
        return "studentList";
    }
}