package fr.univ_smb.isc.m1.totaly_not_p.application;

import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.Comic;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.ComicsRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComicsService {

    private final ComicsRepository repository;

    public ComicsService(ComicsRepository repository) {
        this.repository = repository;
    }

    public List<Comic> allComics() {
        return repository.findAll();
    }

    public ComicsRepository getRepo()
    {
        return repository;
    }

    public Page<Comic> comicsPageRange(int page, int range) {
        return repository.findAll(PageRequest.of(page, range));
    }
}