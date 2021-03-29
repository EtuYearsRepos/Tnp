package fr.univ_smb.isc.m1.totaly_not_p.application;

import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class ComicsService {

    private final ComicsRepository repository;

    public ComicsService(ComicsRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void initialize() {
        if (repository.findAll().isEmpty()) {
            repository.saveAndFlush(new Comic("BD 1", "AUTEUR 1"));
            repository.saveAndFlush(new Comic("BD 2", "AUTEUR 2"));
        }
    }

    public List<Comic> allComics() {
        return repository.findAll();
    }

}