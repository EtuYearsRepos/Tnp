package fr.univ_smb.isc.m1.totaly_not_p.application;

import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.User;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.UsersRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UsersRepository repository;

    public UserService(UsersRepository repository) {
        this.repository = repository;
    }

    public List<User> allUsers() {
        return repository.findAll();
    }

    public UsersRepository getRepo()
    {
        return repository;
    }

    public Page<User>userPageRange(int page, int range) {
        return repository.findAll(PageRequest.of(page, range));
    }

    public User findById(long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }
}