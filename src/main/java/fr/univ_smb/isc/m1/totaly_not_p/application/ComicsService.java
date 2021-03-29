package fr.univ_smb.isc.m1.totaly_not_p.application;

import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.HashSet;
import java.util.List;

@Service
public class ComicsService {

    private final ComicsRepository comicRepository;
    private final UserRepository userRepository;

    public ComicsService(ComicsRepository comicsRepository, UserRepository userRepository) {
        this.comicRepository = comicsRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void initialize() {
        if (comicRepository.findAll().isEmpty() && userRepository.findAll().isEmpty()) {
            Comic c1 = new Comic("BD 1", "AUTEUR 1");
            Comic c2 = new Comic("BD 2", "AUTEUR 2");

            User u = new User("Tristan", "Delapierre");
            HashSet<Comic> comics = new HashSet<Comic>();
            comics.add(c1);
            comics.add(c2);
            HashSet<User> users = new HashSet<User>();
            users.add(u);
            c1.setSubscribers(users);
            c2.setSubscribers(users);
            u.setSubscriptions(comics);
            comicRepository.saveAndFlush(c1);
            comicRepository.saveAndFlush(c2);
            userRepository.saveAndFlush(u);
        }
    }

    public List<Comic> allComics() {
        return comicRepository.findAll();
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

}