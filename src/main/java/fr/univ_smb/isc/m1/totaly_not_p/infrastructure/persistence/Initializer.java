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

        userRepository.saveAndFlush(new User("user", "password", "USER"));

        /*if (repository.findAll().isEmpty()) {
            for (int i =0; i < 102; i++)
                repository.saveAndFlush(new Comic("BD " + i, "AUTEUR " + i));

            repository.saveAndFlush(new Comic("Samère", "bidule"));
            repository.saveAndFlush(new Comic("bite", "FemmeSa"));
        }*/
    }

}
