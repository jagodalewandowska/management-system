package com.project.controller;
import com.project.model.Projekt;
import com.project.model.Zadanie;
import com.project.service.ZadanieService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
@Controller
@SessionAttributes("zadanie")
public class ZadanieController {
    private ZadanieService zadanieService;

    public ZadanieController(ZadanieService zadanieService) {
        this.zadanieService = zadanieService;
    }

    @GetMapping("/zadanieList")
    public String zadanieList(Model model, Pageable pageable) {
        model.addAttribute("zadania", zadanieService.getZadania(pageable).getContent());
        return "zadanieList";
    }

    @GetMapping("/zadanieEdit")
    public String zadanieEdit(@RequestParam(required = false) Integer zadanieId, Model model) {
        if (zadanieId != null) {
            model.addAttribute("zadanie", zadanieService.getZadanie(zadanieId).get());
        } else {
            Zadanie zadanie = new Zadanie();
            Projekt projekt = new Projekt();
            zadanie.setProjekt(projekt);
            model.addAttribute("zadanie", zadanie);
        }
        return "zadanieEdit";
    }

    @PostMapping(path = "/zadanieEdit")
    public String zadanieEditSave(@ModelAttribute @Valid Zadanie zadanie, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "zadanieEdit";
        }
        try {
            zadanie = zadanieService.setZadanie(zadanie, zadanie.getProjekt().getProjektId());
        } catch (HttpStatusCodeException e) {
            bindingResult.rejectValue(Strings.EMPTY, String.valueOf(e.getStatusCode().value()), e.getStatusCode().toString());
            return "zadanieEdit";
        }
        return "redirect:/zadanieList";
    }

    @PostMapping(params="cancel", path = "/zadanieEdit")
    public String zadanieEditCancel() {
        return "redirect:/zadanieList";
    }

    @PostMapping(params="delete", path = "/zadanieEdit")
    public String zadanieEditDelete(@ModelAttribute Zadanie zadanie) {
        zadanieService.deleteZadanie(zadanie.getZadanieId());
        return "redirect:/zadanieList";
    }
}
