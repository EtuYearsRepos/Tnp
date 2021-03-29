package fr.univ_smb.isc.m1.totaly_not_p.application;

import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class ChuckFactsService {

    private final ChuckFactsRepository repository;

    public ChuckFactsService(ChuckFactsRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void initialize() {
        if (repository.findAll().isEmpty()) {
            repository.saveAndFlush(new ChuckFact("Chuck Norris can divide by zero."));
            repository.saveAndFlush(new ChuckFact("Chuck Norris once lost his wedding ring....since then it's been war in Middle Earth"));
        }
    }

    public List<ChuckFact> facts() {
        return repository.findAll();
    }

}