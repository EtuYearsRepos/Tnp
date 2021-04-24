package fr.univ_smb.isc.m1.totaly_not_p.application;

import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComicsService {

    @Resource
    private ComicsRepository comicRepository;

    @Resource
    private UserRepository userRepository;

    /*
     * public ComicsService(ComicsRepository comicsRepository, UserRepository
     * userRepository) { this.comicRepository = comicsRepository;
     * this.userRepository = userRepository; }
     */

    @PostConstruct
    public void initialize() {

        comicRepository.deleteAll();
        userRepository.deleteAll();
    }

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

    public List<Comic> allComics() {
        return comicRepository.findAll();
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

}