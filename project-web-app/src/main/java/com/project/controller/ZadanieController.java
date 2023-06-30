package com.project.controller;

import com.project.model.Projekt;
import com.project.model.Zadanie;
import com.project.service.ProjektService;
import com.project.service.ProjektServiceImpl;
import com.project.service.ZadanieService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ZadanieController {
    private static final Logger logger = LoggerFactory.getLogger(ProjektServiceImpl.class);

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
        model.addAttribute("size",5);
        model.addAttribute("page",0);
        model.addAttribute("nextPage",1);
        model.addAttribute("previousPage",0);
        model.addAttribute("reverseSortDir", "desc");
        return "redirect:/app/zadanieList/results?size=5&page=0&sort=zadanieId&order=asc";
    }

    @GetMapping("/zadanieEdit")
    public String zadanieEdit(@RequestParam(required = false) Integer zadanieId, @RequestParam(required = false) Integer projektId, Model model, Pageable pageable) {
        if (zadanieId != null) {
            model.addAttribute("zadanie", zadanieService.getZadanie(zadanieId).get());
            model.addAttribute("projekty", projektService.getProjekty(pageable).getContent());
        } else {
            Zadanie zadanie = new Zadanie();
            Projekt projekt;
            if (projektId != null) {
                projekt = projektService.getProjekt(projektId).get();
            } else {
                projekt = new Projekt();
            }
            zadanie.setProjekt(projekt);
            model.addAttribute("zadanie", zadanie);
        }
        model.addAttribute("projekty", projektService.getProjekty(pageable).getContent());
        return "zadanieEdit";
    }

    @PostMapping(path = "/zadanieEdit")
    public String zadanieEditSave(@ModelAttribute @Valid Zadanie zadanie, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "zadanieEdit";
        }
        try {
           zadanie = zadanieService.setZadanie(zadanie);
        } catch (HttpStatusCodeException e) {
            bindingResult.rejectValue(Strings.EMPTY, String.valueOf(e.getStatusCode().value()), e.getStatusCode().toString());
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
        logger.info("usuwam");
        zadanieService.deleteZadanie(zadanie.getZadanieId());
        return "redirect:/app/zadanieList";
    }

    @GetMapping("/zadanieList/search")
    public String searchProjectList(Model model, @RequestParam Integer size, @RequestParam String nazwa,
                                    @RequestParam String sort, @RequestParam Integer page,
                                    @RequestParam String order) {
        Pageable pageable = PageRequest.of(page, size);
        model.addAttribute("zadania", zadanieService.searchByNazwa(nazwa, pageable).getContent());
        model.addAttribute("size", size);
        model.addAttribute("page", 0);
        model.addAttribute("nazwa", nazwa);
        model.addAttribute("nextPage", 1);
        model.addAttribute("previousPage", 0);
        model.addAttribute("reverseSortDir", order.equals("asc") ? "desc" : "asc");
        model.addAttribute("order", order);
        model.addAttribute("sort", sort);
        return "zadanieList";
    }

    @GetMapping("/zadanieList/results")
    public String sortProjectList(Model model, @RequestParam String sort, @RequestParam Integer page,
                                  @RequestParam String order, @RequestParam Integer size ) {
        Pageable pageable = PageRequest.of(page, size, getSortDirection(order), sort);
        model.addAttribute("zadania", zadanieService.getZadania(pageable).getContent());
        model.addAttribute("order", order);
        model.addAttribute("reverseSortDir", order.equals("asc") ? "desc" : "asc");
        model.addAttribute("size", size);
        model.addAttribute("sort", sort);
        model.addAttribute("page", page.equals(0) ? 0 : page);
        model.addAttribute("nextPage", page.equals(0)? 1 : page+1);
        model.addAttribute("previousPage", page.equals(0)? 0 : page-1);
        return "zadanieList";
    }
}
