package com.project.controller;

import com.project.model.Projekt;
import com.project.service.ProjektService;
import com.project.service.ProjektServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ProjectController {

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

    public ProjectController(ProjektService projektService) {
        this.projektService = projektService;
    }

    @GetMapping({"","/projektList"})
    public String projektList(Model model, Pageable pageable) {
        model.addAttribute("projekty", projektService.getProjekty(pageable).getContent());
        model.addAttribute("size",5);
        model.addAttribute("page",0);
        model.addAttribute("nextPage",1);
        model.addAttribute("previousPage",0);
        model.addAttribute("reverseSortDir", "desc");
        return "redirect:/app/projektList/results?size=5&page=0&order=asc&sort=projektId";
    }



    @GetMapping("/projektEdit")
    public String projektEdit(@RequestParam(required = false) Integer projektId, Model model) {
        if(projektId != null) {
            model.addAttribute("projekt", projektService.getProjekt(projektId).get());
        }else {
            Projekt projekt = new Projekt();
            model.addAttribute("projekt", projekt);
        }
        return "projektEdit";
    }

    @PostMapping(path = "/projektEdit")
    public String projektEditSave(@ModelAttribute @Valid Projekt projekt, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "projektEdit";
        }
        try {
            projektService.setProjekt(projekt);
        } catch (HttpStatusCodeException e) {
            bindingResult.rejectValue(Strings.EMPTY, String.valueOf(e.getStatusCode().value()), e.getStatusCode().toString());
            return "projektEdit";
        }
        return "redirect:/app/projektList";
    }

    @PostMapping(params="cancel", path = "/projektEdit")
    public String projektEditCancel() {
        return "redirect:/projektList";
    }

    @PostMapping(params="delete", path = "/projektEdit")
    public String projektEditDelete(@ModelAttribute Projekt projekt) {
        projektService.deleteProjekt(projekt.getProjektId());
        return "redirect:/app/projektList";
    }

    @GetMapping("/projektList/search")
    public String searchProjectList(Model model, @RequestParam Integer size, @RequestParam String nazwa) {
        Pageable pageable = PageRequest.of(0, size);
        model.addAttribute("projekty", projektService.searchByNazwa(nazwa,pageable).getContent());
        model.addAttribute("size", size);
        model.addAttribute("page", 0);
        model.addAttribute("nazwa", nazwa);
        model.addAttribute("nextPage", 1);
        model.addAttribute("previousPage", 0);
        return "projektList";
    }

    @GetMapping("/projektList/results")
    public String sortProjectList(Model model, @RequestParam Integer size, @RequestParam String sort,
                                  @RequestParam String order, @RequestParam Integer page) {
        log.info("results" + order);
        Pageable pageable = PageRequest.of(page, size, getSortDirection(order), sort);
        model.addAttribute("projekty", projektService.getProjekty(pageable).getContent());
        model.addAttribute("order", order);
        model.addAttribute("size", size);
        model.addAttribute("sort", sort);
        model.addAttribute("page", page.equals(0) ? 0 : page);
        model.addAttribute("nextPage", page.equals(0)? 1 : page+1);
        model.addAttribute("previousPage", page.equals(0)? 0 : page-1);
        model.addAttribute("reverseSortDir", order.equals("asc") ? "desc" : "asc");
        return "projektList";
    }
}