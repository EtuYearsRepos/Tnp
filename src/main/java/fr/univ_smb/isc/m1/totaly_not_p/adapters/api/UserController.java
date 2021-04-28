package fr.univ_smb.isc.m1.totaly_not_p.adapters.api;


import fr.univ_smb.isc.m1.totaly_not_p.application.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.ComicSimpleDTO;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.user.UserDTO;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value="/api/users")
    public ResponseEntity<List<String>> getAllUsers() {
        
        List<String> usernames = new ArrayList<>();
        userService.allUsers().forEach(user -> {
            usernames.add(user.getUsername());
        });
        return new ResponseEntity<>(usernames, HttpStatus.OK);
    }

    @GetMapping(value="api/user/subscribe/{id}")
    public ResponseEntity<Boolean> subscribe(@PathVariable(name = "id") Long id) {
        Boolean success = userService.addComicSubscriptionToUser(id);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }

    @GetMapping(value="api/user/unsubscribe/{id}")
    public ResponseEntity<Boolean> unsubscribe(@PathVariable(name = "id") Long id) {
        Boolean success = userService.removeComicSubscriptionFromUser(id);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }
    
    @GetMapping(value="api/user/subscriptions")
    public ResponseEntity<List<ComicSimpleDTO>> getSubscriptions() {
        List<ComicSimpleDTO> comics = userService.getUserSubscriptions();
        return new ResponseEntity<>(comics, HttpStatus.OK);
    }
}
