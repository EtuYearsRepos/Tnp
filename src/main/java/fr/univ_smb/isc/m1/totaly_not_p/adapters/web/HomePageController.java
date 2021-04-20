package fr.univ_smb.isc.m1.totaly_not_p.adapters.web;

import fr.univ_smb.isc.m1.totaly_not_p.application.ComicsService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomePageController {

    private final ComicsService comicsService;

    public HomePageController(ComicsService comicsService) {
        this.comicsService = comicsService;
    }

    @GetMapping(value="/") // /?page=1  /?page=2 ...   --> comics from 0+range*(index-1) to range*index
    public String index(Model model, @RequestParam(defaultValue = "1") int page) {
        model.addAttribute("comics", comicsService.comicsPageRange(page-1, 10) );
        return "base";
    }
    /*comicsService.comicsFromTo(0+10*(page-1), 10*page)*/
    
}
