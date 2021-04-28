package fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence;

import org.springframework.stereotype.Service;

import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.user.User;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.user.UserRepository;

import javax.annotation.PostConstruct;

@Service
class Initializer {

    private final ComicsRepository comicRepository;
    private final UserRepository userRepository;

    public Initializer(ComicsRepository comicRepository, UserRepository userRepository) {
        this.comicRepository = comicRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void initialize() {

        comicRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();

        userRepository.saveAndFlush(new User("user", "user@user.com", "password", "USER"));
        userRepository.saveAndFlush(new User("admin", "admin@admin.com", "admin", "ADMIN"));
    }

}
