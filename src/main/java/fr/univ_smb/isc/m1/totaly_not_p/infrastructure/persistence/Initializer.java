package fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
class Initializer {

    private final ComicsRepository repository;

    public Initializer(ComicsRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void initialize() {

        repository.deleteAllInBatch();

        if (repository.findAll().isEmpty()) {
            repository.saveAndFlush(new Comic("BD 1", "AUTEUR 1"));
            repository.saveAndFlush(new Comic("BD 2", "AUTEUR 2"));
            repository.saveAndFlush(new Comic("BD 3", "AUTEUR 3"));
        }
    }

}
