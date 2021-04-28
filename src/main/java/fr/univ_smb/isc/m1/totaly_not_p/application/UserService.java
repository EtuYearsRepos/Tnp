package fr.univ_smb.isc.m1.totaly_not_p.application;

import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.Comic;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.ComicsRepository;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.user.User;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.user.UserDTO;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.user.UserRepository;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.ComicDTO;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.ComicSimpleDTO;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Transactional
    public Boolean addComicSubscriptionToUser(Long id) {
        Comic c = comicRepository.getOne(id);
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (username != null && !username.equals("anonymousUser") && c != null) {
            System.out.println("Adding comic " + id + " to user " + username);
            User user = userRepository.findByUsername(username);
            user.addSubscription(c);
            userRepository.saveAndFlush(user);
        } else {
            return false;
        }

        return true;
    }

    @Transactional
    public Boolean removeComicSubscriptionFromUser(Long id) {
        Comic c = comicRepository.getOne(id);
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (username != null && !username.equals("anonymousUser") && c != null) {
            System.out.println("Removing comic " + id + " from user " + username);
            User user = userRepository.findByUsername(username);
            user.removeSubscription(c);
            userRepository.saveAndFlush(user);
        } else {
            return false;
        }

        return true;
    }

    @Transactional
    public List<ComicSimpleDTO> getUserSubscriptions() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ComicSimpleDTO> comicDTOs = new ArrayList<>();
        if(username != null && !username.equals("anonymousUser")) {
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

    public String registerUser(UserDTO userDTO)
    {
        System.out.println(userDTO.toString());

        if (userDTO.getUsername().equals("anonymousUser") || userDTO.getUsername().equals(null)|| userDTO.getUsername().equals(""))
        {
            return "BAD USERNAME";
        }

        else
        {
            User u = userRepository.findByUsername(userDTO.getUsername());
            if (u != null)
            {
                return "USERNAME ALREADY EXISTS";
            }

            if (!userDTO.getPassword().equals(userDTO.getMatchingPassword()))
            {
                return "PASSWORD NOT MACHING";
            }

        }

        User u = userRepository.saveAndFlush(new User(userDTO.getUsername(), userDTO.getPassword(), "USER"));

        if (u.equals(null))
        {
            return "ERROR REGISTERING";
        }
        return "REGISTER SUCCESFULL";
    }

    public void save(UserDTO userDTO)
    {
        userRepository.saveAndFlush(new User(userDTO.getUsername(), userDTO.getPassword(), "USER"));
    }


    public void validate(Object o, Errors errors) {
        UserDTO user = (UserDTO) o;


        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (userRepository.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
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