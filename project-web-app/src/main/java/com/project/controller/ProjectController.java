package com.project.controller;
import jakarta.validation.Valid;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import com.project.model.Projekt;
import com.project.service.ProjektService;
@Controller
public class ProjectController {
    private ProjektService projektService;
    //@Autowired – przy jednym konstruktorze wstrzykiwanie jest zadaniem domyślnym, adnotacji nie jest potrzebna
    public ProjectController(ProjektService projektService) {
        this.projektService = projektService;
    }
    @GetMapping("/projektList") //np. http://localhost:8081/projektList?page=0&size=10&sort=dataCzasModyfikacji,desc
    public String projektList(Model model, Pageable pageable) {
        model.addAttribute("projekty", projektService.getProjekty(pageable).getContent());
        return "projektList";
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
//parametr BindingResult powinien wystąpić zaraz za parametrem opatrzonym adnotacją @Valid
        if (bindingResult.hasErrors()) {
            return "projektEdit";
        }
        try {
            projekt = projektService.setProjekt(projekt);
        } catch (HttpStatusCodeException e) {
            bindingResult.rejectValue(Strings.EMPTY, String.valueOf(e.getStatusCode().value()), e.getStatusCode().toString());
            return "projektEdit";
        }
        return "redirect:/projektList";
    }
    @PostMapping(params="cancel", path = "/projektEdit")
    public String projektEditCancel() {
        return "redirect:/projektList";
    }
    @PostMapping(params="delete", path = "/projektEdit")
    public String projektEditDelete(@ModelAttribute Projekt projekt) {
        projektService.deleteProjekt(projekt.getProjektId());
        return "redirect:/projektList";
    }
}