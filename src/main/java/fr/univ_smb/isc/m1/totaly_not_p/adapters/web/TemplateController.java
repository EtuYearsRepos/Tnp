package fr.univ_smb.isc.m1.totaly_not_p.adapters.web;

import fr.univ_smb.isc.m1.totaly_not_p.application.ComicsService;
import fr.univ_smb.isc.m1.totaly_not_p.application.UserService;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.Comic;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.user.UserDTO;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.user.User;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.user.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TemplateController {

    private static final int ELEMENT_PER_PAGE = 20;
    private static final int NUMBER_DISPLAY_PAGE = 4;

    private static final String TITLE_VALUE = "title";
    private static final String TOTAL_PAGES = "totalPages";
    private static final String TOTAL_ELEMENTS = "totalElements";
    private static final String COMICS = "comics";
    private static final String ANONYMOUS_USER = "anonymousUser";
    private static final String IS_CONNECTED = "isConnected";

    private final ComicsService comicsService;
    private final UserService userService;
    private final UserRepository userRepository;

    public TemplateController(ComicsService comicsService, UserRepository userRepository, UserService userService) {
        this.comicsService = comicsService;
        this.userRepository = userRepository;
        this.userService = userService;
    }
    /*************************************/
    //Home Page
    @GetMapping(value="/")
    public String homePage(Model model)
    {
        return findPage(model, 1); 
    }
    
    @GetMapping(value="/{page}") // /?page=1  /?page=2 ...   --> comics from 0+range*(index-1) to range*index
    public String findPage(Model model, @PathVariable(value = "page") int page) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Page<Comic> p = comicsService.comicsPageRange(page - 1, ELEMENT_PER_PAGE, "title", true);
        List<Comic> listComics = p.getContent();

        model.addAttribute("currentPage", page);
        model.addAttribute(TOTAL_PAGES, p.getTotalPages());
        model.addAttribute(TOTAL_ELEMENTS, p.getTotalElements());
        model.addAttribute("numberDisplayPage", NUMBER_DISPLAY_PAGE);

        model.addAttribute(TITLE_VALUE, "Home");
        model.addAttribute(COMICS, listComics );

        model.addAttribute(IS_CONNECTED, !username.equals(ANONYMOUS_USER));
        
        return "home_template";
    }
    /***********************************/
    //Comic page
    @GetMapping(value="/comic/{id}")
    public String comicPage(Model model, @PathVariable("id") long id) {
        var c = comicsService.findById(id);
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User u;

        var found = false;
        if (!username.equals(ANONYMOUS_USER))
        {
            u = userRepository.findByUsername(username);
            HashSet<Comic> h = u.getSubscriptions();
            for (Comic comic : h) {
                if (comic.getId().longValue() == c.getId().longValue())
                {
                    found = true;
                    break;
                }
            }
            model.addAttribute("user", u);

        }

        model.addAttribute(TITLE_VALUE, c.getTitle());
        model.addAttribute("comic", c);
        model.addAttribute(IS_CONNECTED, !username.equals(ANONYMOUS_USER));
        model.addAttribute("isSubscribed", found);


        return "comic_template";
    }
    
    /*********************************/
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
            
            model.addAttribute(TOTAL_ELEMENTS, lenghtList);
            model.addAttribute(TOTAL_PAGES, nbPages);
            
            model.addAttribute(COMICS, p);
        }
        else
        {
            Page<Comic> p = comicsService.comicsPageRange(page - 1, ELEMENT_PER_PAGE, "title", false);
            List<Comic> listComics = p.getContent();
            
            model.addAttribute(TOTAL_PAGES, p.getTotalPages());
            model.addAttribute(TOTAL_ELEMENTS, p.getTotalElements());
            
            model.addAttribute(COMICS, listComics );  
        }
        
        model.addAttribute("currentPage", page);
        model.addAttribute("numberDisplayPage", NUMBER_DISPLAY_PAGE);
        model.addAttribute(TITLE_VALUE, "Search");
        
        model.addAttribute("keyword_search", keyword);
        model.addAttribute(IS_CONNECTED, !username.equals(ANONYMOUS_USER));
        
        return "search_template";
    }
    /************************************************/
    
    //User Actions
    @GetMapping(value = "/login")
    public String login(Model model) {
        model.addAttribute(TITLE_VALUE, "Login");
        return "login";
    }

    @GetMapping(value = "/register")
    public String registerPage(Model model){

        var userDto = new UserDTO();
        model.addAttribute(TITLE_VALUE, "Register");
        model.addAttribute("user", userDto);
        return "register";
    }

    @GetMapping(value = "/profile")
    public String userPage(Model model){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (username != null && !username.equals(ANONYMOUS_USER)) {
            User user = userRepository.findByUsername(username);
            List<Comic> favorites = new ArrayList<>(user.getSubscriptions());
            model.addAttribute("current_user", user);
            model.addAttribute("favs", favorites);
            model.addAttribute(IS_CONNECTED, !username.equals(ANONYMOUS_USER));
        }
        model.addAttribute(TITLE_VALUE, username);


        return "profile_template";
    }
    
    @GetMapping(value = "/edit_profile")
    public String editProfilePage(Model model){
        model.addAttribute(TITLE_VALUE, "Profile edition");
        return "edit_profile";
    }

    @PostMapping("/register")
    public String registration(@ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult, Model model) {
        userService.validate(userDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            return "register";
        }

        userService.save(userDTO);

        return "redirect:/login";
    }

}
