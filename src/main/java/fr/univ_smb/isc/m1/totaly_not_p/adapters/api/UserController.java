package fr.univ_smb.isc.m1.totaly_not_p.adapters.api;


import fr.univ_smb.isc.m1.totaly_not_p.application.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.ComicSimpleDTO;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value="/api/users")
    public ResponseEntity<List<String>> getAllUsers() {
        
        List<String> usernames = new ArrayList<>();
        userService.allUsers().forEach(user -> 
            usernames.add(user.getUsername())
        );
        return new ResponseEntity<>(usernames, HttpStatus.OK);
    }

    @GetMapping(value="api/user/subscribe/{id}")
    public ResponseEntity<Boolean> subscribe(@PathVariable(name = "id") Long id) {
        userService.addComicSubscriptionToUser(id);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("/comic/" + id)).build();
    }

    @GetMapping(value="api/user/unsubscribe/{id}/{from}")
    public ResponseEntity<Boolean> unsubscribe(@PathVariable(name = "id") Long id, @PathVariable(name = "from") String from) {
        userService.removeComicSubscriptionFromUser(id);

        if (from.equals("profile"))
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("/profile")).build();

        else if (from.equals("comic"))
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("/comic/" + id)).build();

        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }
    
    @GetMapping(value="api/user/subscriptions")
    public ResponseEntity<List<ComicSimpleDTO>> getSubscriptions() {
        List<ComicSimpleDTO> comics = userService.getUserSubscriptions();
        return new ResponseEntity<>(comics, HttpStatus.OK);
    }
}
