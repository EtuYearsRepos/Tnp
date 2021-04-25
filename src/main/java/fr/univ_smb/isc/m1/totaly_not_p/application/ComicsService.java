package fr.univ_smb.isc.m1.totaly_not_p.application;

import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.Comic;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.ComicsRepository;

import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.UserRepository;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.User;

import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.ComicDTO;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.ComicSimpleDTO;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashSet;

import java.util.List;
import java.util.Optional;

@Service
public class ComicsService {

    @Resource
    private ComicsRepository comicRepository;

    @Resource
    private UserRepository userRepository;

    @Transactional
    public ComicDTO addComic(ComicDTO dto) {
        Comic c = new Comic();
        mapDtoToEntity(dto, c);
        Comic savedComic = comicRepository.save(c);
        return mapEntityToDto(savedComic);
    }

    public List<ComicSimpleDTO> getAllComics() {
        List<ComicSimpleDTO> comicDTOs = new ArrayList<>();
        List<Comic> comics = comicRepository.findAll();
        comics.stream().forEach(comic -> {
            if (comic != null) {
                ComicSimpleDTO dto = mapEntityToSimpleDto(comic);
                comicDTOs.add(dto);
            }
        });
        return comicDTOs;
    }

    @Transactional
    public ComicDTO updateComic(Long id, ComicDTO dto) {
        Comic c = comicRepository.getOne(id);
        c.getSubscribers().clear();
        mapDtoToEntity(dto, c);
        Comic comic = comicRepository.save(c);
        return mapEntityToDto(comic);
    }

    public ComicDTO getComic(Long id) {
        Comic c = comicRepository.getOne(id);
        return mapEntityToDto(c);
    }

    public String deleteComic(Long id) {
        Optional<Comic> c = comicRepository.findById(id);
        if (c.isPresent()) {
            c.get().removeSubscribers();
            comicRepository.deleteById(c.get().getId());
            return "Deleted comic: " + c.get().getTitle();
        }
        return null;
    }

    private void mapDtoToEntity(ComicDTO dto, Comic c) {
        c.setTitle(dto.getTitle());
        c.setPublisher(dto.getPublisher());
        c.setArtist(dto.getArtist());
        c.setWriter(dto.getWriter());
        c.setPublicationDate(dto.getPublicationDate());
        c.setSummary(dto.getSummary());
        c.setIssues(dto.getIssues());
        c.setStatus(dto.getStatus());
        // c.setThumbnail(new byte[]{});
        if (c.getSubscribers() == null) {
            c.setSubscribers(new HashSet<>());
        }
    }

    private ComicDTO mapEntityToDto(Comic c) {
        ComicDTO responseDTO = new ComicDTO();
        responseDTO.setTitle(c.getTitle());
        responseDTO.setId(c.getId());
        responseDTO.setPublisher(c.getPublisher());
        responseDTO.setWriter(c.getWriter());
        responseDTO.setArtist(c.getArtist());
        responseDTO.setStatus(c.getStatus());
        responseDTO.setSummary(c.getSummary());
        responseDTO.setSubNb(c.getSubNb());
        responseDTO.setPublicationDate(c.getPublicationDate());
        responseDTO.setIssues(c.getIssues());
        return responseDTO;
    }

    private ComicSimpleDTO mapEntityToSimpleDto(Comic c) {
        ComicSimpleDTO responseDTO = new ComicSimpleDTO();
        responseDTO.setTitle(c.getTitle());
        responseDTO.setId(c.getId());
        responseDTO.setSubNb(c.getSubNb());
        responseDTO.setPublicationDate(c.getPublicationDate());
        return responseDTO;
    }

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
                    comicDTOs.add(mapEntityToSimpleDto(c));
                }
            }
            
        }
        return comicDTOs;
    }

    public List<Comic> allComics() {
        return comicRepository.findAll();
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public ComicsRepository getRepo()
    {
        return comicRepository;
    }

    public Page<Comic> comicsPageRange(int page, int range) {
        return comicRepository.findAll(PageRequest.of(page, range));
    }

    public Comic findById(long id) {
        return comicRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid comic Id:" + id));
    }

    //get Comics by Keywords
    public List<Comic> findByKeyword(String keyword)
    {
        return comicRepository.findByKeyword(keyword);
    }

    //get Comics by Keywords with Page
    public List<Comic> findByKeywordPage(String keyword, int page, int range)
    {
        Pageable pageable = PageRequest.of(page, range);
        return comicRepository.findByKeyword(keyword, pageable);
    }

}