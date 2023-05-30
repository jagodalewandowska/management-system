package com.project.controller;
import com.project.model.Projekt;
import com.project.model.Zadanie;
import com.project.service.ProjektService;
import com.project.service.ZadanieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@Controller
@RequiredArgsConstructor
@RequestMapping("/app")
public class ZadanieController {
    private ZadanieService zadanieService;
    private ProjektService projektService;

    private Sort.Direction getSortDirection(String direction) {
        direction = direction.toLowerCase();
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    public ZadanieController(ZadanieService zadanieService, ProjektService projektService) {
        this.zadanieService = zadanieService;
        this.projektService = projektService;
    }

    @GetMapping("/zadanieList")
    public String zadanieList(Model model, Pageable pageable) {
        model.addAttribute("zadania", zadanieService.getZadania(pageable).getContent());
        return "redirect:/app/zadanieList/results?sort=zadanieId&order=asc";
    }

    @GetMapping("/zadanieEdit")
    public String zadanieEdit(@RequestParam(required = false) Integer zadanieId, Model model, Pageable pageable) {
        if (zadanieId != null) {
            model.addAttribute("zadanie", zadanieService.getZadanie(zadanieId).get());
            model.addAttribute("projekty", projektService.getProjekty(pageable).getContent());
        } else {
            Zadanie zadanie = new Zadanie();
            Projekt projekt = new Projekt();
            zadanie.setProjekt(projekt);
            model.addAttribute("zadanie", zadanie);
            model.addAttribute("projekty", projektService.getProjekty(pageable).getContent());
        }
        return "zadanieEdit";
    }

    @PostMapping(path = "/zadanieEdit")
    public String zadanieEditSave(@ModelAttribute @Valid Zadanie zadanie, Integer projektId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "zadanieEdit";
        }
        try {
            zadanieService.setZadanie(zadanie);
        } catch (HttpStatusCodeException e) {
            bindingResult.rejectValue(Strings.EMPTY,
                                      String.valueOf(e.getStatusCode().value()), e.getStatusCode().toString());
            System.out.println(e.getStatusCode() + e.getMessage());
            return "zadanieEdit";
        }
        return "redirect:/app/zadanieList";
    }

    @PostMapping(params = "cancel", path = "/zadanieEdit")
    public String zadanieEditCancel() {
        return "redirect:/app/zadanieList";
    }

    @PostMapping(params = "delete", path = "/zadanieEdit")
    public String zadanieEditDelete(@ModelAttribute Zadanie zadanie) {
        zadanieService.deleteZadanie(zadanie.getZadanieId());
        return "redirect:/app/zadanieList";
    }

    @GetMapping("/zadanieList/search")
    public String searchProjectList(Model model, @RequestParam Integer size, @RequestParam String nazwa) {
        Pageable pageable = PageRequest.of(0, size);
        model.addAttribute("zadania", zadanieService.searchByNazwa(nazwa, pageable).getContent());
        model.addAttribute("size", size);
        model.addAttribute("page", 0);
        model.addAttribute("nazwa", nazwa);
        model.addAttribute("nextPage", 1);
        model.addAttribute("previousPage", 0);
        return "studentList";
    }

    @GetMapping("/zadanieList/results")
    public String sortProjectList(Model model, @RequestParam String sort, @RequestParam String order) {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, getSortDirection(order), sort);
        model.addAttribute("zadania", zadanieService.getZadania(pageable).getContent());
        model.addAttribute("order", order);
        model.addAttribute("reverseSortDir", order.equals("asc") ? "desc" : "asc");
        return "zadanieList";
    }
}
