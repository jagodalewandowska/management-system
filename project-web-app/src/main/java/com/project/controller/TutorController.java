package com.project.controller;
import com.project.model.Student;
import com.project.model.Tutor;
import com.project.service.ProjektService;
import com.project.service.StudentService;
import com.project.service.TutorService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

@Controller
@RequestMapping("/app")
public class TutorController {

    private TutorService tutorService;
    public TutorController(TutorService tutorService) {
        this.tutorService = tutorService;

    }
    @GetMapping("/tutorList")
    public String tutorList(Model model, Pageable pageable) {
        try {
            model.addAttribute("tutors", tutorService.getTutors(pageable).getContent());
            return "tutorList";
        } catch (HttpStatusCodeException e) {
            return "404";
        }
    }
    @GetMapping("/tutorEdit")
    public String tutorEdit(@RequestParam(required = false) Integer tutorId, Model model) {
        if (tutorId != null) {
            model.addAttribute("tutor", tutorService.getTutor(tutorId).get());
        } else {
            Tutor tutor = new Tutor();
            model.addAttribute("tutor", tutor);
        }
        return "tutorEdit";
    }

    @PostMapping(path = "/tutorEdit")
    public String tutorEditSave(@ModelAttribute @Valid Tutor tutor, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "tutorEdit";
        }
        try {
            tutor = tutorService.setTutor(tutor);
        } catch (HttpStatusCodeException e) {
            bindingResult.rejectValue(Strings.EMPTY, String.valueOf(e.getStatusCode().value()), e.getStatusCode().toString());
            return "tutorEdit";
        }
        return "redirect:/app/tutorList";
    }

    @PostMapping(params="cancel", path = "/tutorEdit")
    public String tutorEditCancel() { // żądanie będzie zawierało parametr 'cancel'
        return "redirect:/tutorList";
    }
}