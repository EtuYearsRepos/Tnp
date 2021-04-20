package fr.univ_smb.isc.m1.totaly_not_p.adapters.web;

import fr.univ_smb.isc.m1.totaly_not_p.application.ComicsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    private final ComicsService comicsService;

    public HomePageController(ComicsService comicsService) {
        this.comicsService = comicsService;
    }

    @GetMapping(value="/")
    public String index(Model model) {
        model.addAttribute("comics", comicsService.allComics());
        return "base";
    }
    
}
