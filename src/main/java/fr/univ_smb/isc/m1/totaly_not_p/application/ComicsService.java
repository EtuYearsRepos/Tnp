package fr.univ_smb.isc.m1.totaly_not_p.application;

import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.Comic;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.ComicsRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Comic findById(long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid comic Id:" + id));
    }

    //get Comics by Keywords
    public List<Comic> findByKeyword(String keyword)
    {
        return repository.findByKeyword(keyword);
    }

    //get Comics by Keywords with Page
    public List<Comic> findByKeywordPage(String keyword, int page, int range)
    {
        Pageable pageable = PageRequest.of(page, range);
        return repository.findByKeyword(keyword, pageable);
    }

}