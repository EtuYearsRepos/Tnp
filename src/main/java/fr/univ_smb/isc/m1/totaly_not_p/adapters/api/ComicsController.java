package fr.univ_smb.isc.m1.totaly_not_p.adapters.api;

import fr.univ_smb.isc.m1.totaly_not_p.application.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;


import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class ComicsController {

    private final ComicsService comicsService;

    public ComicsController(ComicsService comicsService) {
        this.comicsService = comicsService;
    }


    @GetMapping(value="/comics")
    public List<String> comics() {
        return comicsService.allComics()
                .stream()
                .map(p-> p.toString())
                .collect(toList());
    }

    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("message", "Bienvenutti");
        return modelAndView;
    }

}
