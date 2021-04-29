package fr.univ_smb.isc.m1.totaly_not_p.application;

import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.Comic;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.ComicsRepository;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.user.User;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.user.UserDTO;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.user.UserRepository;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.ComicSimpleDTO;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashSet;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Resource
    private ComicsRepository comicRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private ComicsService comicsService;

    private final static String ANONYMOUS_USER = "anonymousUser";

    @Transactional
    public Boolean addComicSubscriptionToUser(Long id) {
        Comic c = comicRepository.getOne(id);
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (username != null && !username.equals(ANONYMOUS_USER) && c != null) {
            System.out.println("Adding comic " + id + " to user " + username);
            User user = userRepository.findByUsername(username);
            user.addSubscription(c);

            Optional<Comic> optComic = comicRepository.findById(c.getId());
            
            if (optComic.isPresent())
            {
                Comic comic = optComic.get();
                comic.addSubscriber(user);
                userRepository.saveAndFlush(user);
            }
            else
            {
                return false;
            }

        } else {
            return false;
        }

        return true;
    }

    @Transactional
    public Boolean removeComicSubscriptionFromUser(Long id) {
        Comic c = comicRepository.getOne(id);
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (username != null && !username.equals(ANONYMOUS_USER) && c != null) {
            System.out.println("Removing comic " + id + " from user " + username);
            User user = userRepository.findByUsername(username);
            
            Optional<Comic> optComic = comicRepository.findById(c.getId());
            
            if (optComic.isPresent())
            {
                user.removeSubscription(c);
                userRepository.saveAndFlush(user);
                Comic comic = optComic.get();
                comic.removeSubscriber(user);
            }
            else
            {
                return false;
            }
            
        } else {
            return false;
        }

        return true;
    }

    @Transactional
    public List<ComicSimpleDTO> getUserSubscriptions() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ComicSimpleDTO> comicDTOs = new ArrayList<>();
        if(username != null && !username.equals(ANONYMOUS_USER)) {
            User user = userRepository.findByUsername(username);
            HashSet<Comic> comics = user.getSubscriptions();
            
            for (Comic c : comics) {
                if (c != null) {
                    comicDTOs.add(comicsService.mapEntityToSimpleDto(c));
                }
            }
            
        }
        return comicDTOs;
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public void save(UserDTO userDTO)
    {
        userRepository.saveAndFlush(new User(userDTO.getUsername(), userDTO.getEmail(), userDTO.getPassword(), "USER"));
    }

    public void validate(Object o, Errors errors) {
        UserDTO user = (UserDTO) o;

        final String SUsername = "username";

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, SUsername, "NotEmpty");
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue(SUsername, "Size.userForm.username");
        }
        if (userRepository.findByUsername(user.getUsername()) != null) {
            errors.rejectValue(SUsername, "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }
        
        if (!user.getMatchingPassword().equals(user.getPassword())) {
            errors.rejectValue("matchingPassword", "Diff.userForm.passwordConfirm");
        }
    }

} 