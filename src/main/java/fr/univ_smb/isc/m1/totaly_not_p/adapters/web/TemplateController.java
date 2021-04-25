package fr.univ_smb.isc.m1.totaly_not_p.adapters.web;

import fr.univ_smb.isc.m1.totaly_not_p.application.ComicsService;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.Comic;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TemplateController {

    private static final int ELEMENT_PER_PAGE = 20;
    private static final int NUMBER_DISPLAY_PAGE = 4;

    private final ComicsService comicsService;

    public TemplateController(ComicsService comicsService) {
        this.comicsService = comicsService;
    }
    
    //Home Page
    @GetMapping(value="/")
    public String homePage(Model model)
    {
        return findPage(model, 1); 
    }
    
    @GetMapping(value="/{page}") // /?page=1  /?page=2 ...   --> comics from 0+range*(index-1) to range*index
    public String findPage(Model model, @PathVariable(value = "page") int page) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Page<Comic> p = comicsService.comicsPageRange(page - 1, ELEMENT_PER_PAGE);
        List<Comic> listComics = p.getContent();

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", p.getTotalPages());
        model.addAttribute("totalElements", p.getTotalElements());
        model.addAttribute("numberDisplayPage", NUMBER_DISPLAY_PAGE);

        model.addAttribute("title", "Home");
        model.addAttribute("comics", listComics );

        model.addAttribute("isConnected", !username.equals("anonymousUser"));
        
        return "home_template";
    }
    
    //Comic page
    @GetMapping(value="/comic/{id}")
    public String comicPage(Model model, @PathVariable("id") long id) {
        Comic c = comicsService.findById(id);
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("title", c.getTitle());
        model.addAttribute("comic", c);

        model.addAttribute("isConnected", !username.equals("anonymousUser"));

        return "comic_template";
    }

    //Search Page
    @GetMapping(value = "/search")
    public String defaultSearch(Model model)
    {
        return searchPage(model, 1, null);
    }

    
    @GetMapping(value="/search/{page}")
    public String searchPage(Model model, @PathVariable(value = "page") int page, String keyword) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if (keyword != null)
        {
            int lenghtList = comicsService.findByKeyword(keyword).size();
            int nbPages = (int) Math.ceil( (double) lenghtList / ELEMENT_PER_PAGE);
            
            List<Comic> p = comicsService.findByKeywordPage(keyword, page -1, ELEMENT_PER_PAGE);
            
            model.addAttribute("totalElements", lenghtList);
            model.addAttribute("totalPages", nbPages);
            
            model.addAttribute("comics", p);
        }
        else
        {
            Page<Comic> p = comicsService.comicsPageRange(page - 1, ELEMENT_PER_PAGE);
            List<Comic> listComics = p.getContent();
            
            model.addAttribute("totalPages", p.getTotalPages());
            model.addAttribute("totalElements", p.getTotalElements());
            
            model.addAttribute("comics", listComics );  
        }
        
        model.addAttribute("currentPage", page);
        model.addAttribute("numberDisplayPage", NUMBER_DISPLAY_PAGE);
        model.addAttribute("title", "Search");
        
        model.addAttribute("keyword_search", keyword);
        model.addAttribute("isConnected", !username.equals("anonymousUser"));
        
        return "search_template";
    }

    //User Actions
    @GetMapping(value = "/login-form")
    public String login() {
        return "login_template";
    }
}
