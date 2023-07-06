package com.project.controller;

import com.project.model.FileInfo;
import com.project.model.Projekt;
import com.project.service.FilesStorageService;
import com.project.service.ProjektService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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

import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/app")
@Slf4j
public class ProjectController {

    private ProjektService projektService;
    private FilesStorageService filesStorageService;

    private Sort.Direction getSortDirection(String direction) {
        direction = direction.toLowerCase();
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    public ProjectController(ProjektService projektService, FilesStorageService filesStorageService) {
        this.projektService = projektService;
        this.filesStorageService = filesStorageService;
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
            projekt = projektService.setProjekt(projekt);
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
    public String projektEditDelete(@ModelAttribute Projekt projekt, Model model,
                                    BindingResult bindingResult, Pageable pageable,
                                    @RequestParam Integer projektId) {
        try {
            Page<FileInfo> fileInfoPage = filesStorageService.getFileInfos(pageable);
            List<FileInfo> files = fileInfoPage.getContent();
            for (FileInfo file : files) {
                if (file.getProjekt().getProjektId() == projektId) {
                    filesStorageService.delete(file.getName(), file.getFileId());
                }
            }
            projektService.deleteProjekt(projekt.getProjektId());
        } catch (HttpStatusCodeException e) {
            bindingResult.rejectValue(Strings.EMPTY, String.valueOf(e.getStatusCode().value()), e.getStatusCode().toString());
            return "projektEdit";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/app/projektList";
    }

    @GetMapping("/projektList/search")
    public String searchProjectList(Model model, @RequestParam Integer size,
                                    @RequestParam String nazwa, @RequestParam Integer page,
                                    @RequestParam String order, @RequestParam String sort) {
        Pageable pageable = PageRequest.of(page, size);
        model.addAttribute("projekty", projektService.searchByNazwa(nazwa,pageable).getContent());
        model.addAttribute("size", size);
        model.addAttribute("page", page);
        model.addAttribute("nazwa", nazwa);
        model.addAttribute("sort", sort);
        model.addAttribute("order", order);
        return getString(model, page, order);
    }

    private String getString(Model model, @RequestParam Integer page, @RequestParam String order) {
        getTheRest(model, page, order);
        return "projektList";
    }

    static void getTheRest(Model model, @RequestParam Integer page, @RequestParam String order) {
        model.addAttribute("page", page.equals(0) ? 0 : page);
        model.addAttribute("nextPage", page.equals(0)? 1 : page+1);
        model.addAttribute("previousPage", page.equals(0)? 0 : page-1);
        model.addAttribute("reverseSortDir", order.equals("asc") ? "desc" : "asc");
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
        return getString(model, page, order);
    }
}