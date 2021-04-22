package fr.univ_smb.isc.m1.totaly_not_p.adapters.web;

import fr.univ_smb.isc.m1.totaly_not_p.application.ComicsService;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.Comic;

import java.io.Console;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TemplateController {

    private static final int ELEMENT_PER_PAGE = 10;
    private static final int NUMBER_DISPLAY_PAGE = 4;

    private final ComicsService comicsService;

    public TemplateController(ComicsService comicsService) {
        this.comicsService = comicsService;
    }

    @GetMapping(value="/")
    public String homePage(Model model)
    {
        return findPage(model, 1);
    }

    @GetMapping(value="/{page}") // /?page=1  /?page=2 ...   --> comics from 0+range*(index-1) to range*index
    public String findPage(Model model, @PathVariable(value = "page") int page) {

        Page<Comic> p = comicsService.comicsPageRange(page - 1, ELEMENT_PER_PAGE);
        List<Comic> listComics = p.getContent();

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", p.getTotalPages());
        model.addAttribute("totalElements", p.getTotalElements());
        model.addAttribute("numberDisplayPage", NUMBER_DISPLAY_PAGE);

        model.addAttribute("title", "Home");
        model.addAttribute("comics", listComics );

        return "home_template";
    }
    /*comicsService.comicsFromTo(0+10*(page-1), 10*page)*/

    @GetMapping(value="/comic/{id}")
    public String comicPage(Model model, @PathVariable("id") long id) {
        Comic c = comicsService.findById(id);

        model.addAttribute("title", c.getTitle());
        model.addAttribute("comic", c);
        return "comic_template";
    }

    @GetMapping(value="/search")
    public String comicPage(Model model, String keyword) {

        if (keyword != null)
        {
            model.addAttribute("comics", comicsService.findByKeyword(keyword));
        }
        else
            model.addAttribute("comics", comicsService.allComics());

        return "search_template";
    }
}