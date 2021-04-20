package fr.univ_smb.isc.m1.totaly_not_p.adapters.api;

import fr.univ_smb.isc.m1.totaly_not_p.application.ComicsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class ComicsController {
    
    private final ComicsService comicsService;
    
    public ComicsController(ComicsService comicsService) {
        this.comicsService = comicsService;
    }
    
    
    @GetMapping(value="/api/comics")
    public List<String> comics() {
        return comicsService.allComics()
        .stream()
        .map(p-> p.toString())
        .collect(toList());
    }
}
