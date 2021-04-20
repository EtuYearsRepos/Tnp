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
            for (int i =0; i < 102; i++)
                repository.saveAndFlush(new Comic("BD " + i, "AUTEUR " + i));
        }
    }

}
