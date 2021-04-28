package fr.univ_smb.isc.m1.totaly_not_p.adapters.api;


import fr.univ_smb.isc.m1.totaly_not_p.application.ComicsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.ComicDTO;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.ComicSimpleDTO;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;


@RestController
public class ComicsController {

    @Autowired
    private ComicsService comicsService;

    @GetMapping(value="/api/comics")
    public ResponseEntity<List<ComicSimpleDTO>> getAllComics() {
        List<ComicSimpleDTO> comics = comicsService.getAllComics();
        return new ResponseEntity<>(comics, HttpStatus.OK);
    }

    @GetMapping(value="/api/comic/{id}")
    public ResponseEntity<ComicDTO> getComic(@PathVariable(name = "id") Long id) {
        ComicDTO dto = comicsService.getComic(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping(value="/admin/api/comic")
    public ResponseEntity<ComicDTO> createComic(@RequestBody ComicDTO comicDto) {
        ComicDTO dto = comicsService.addComic(comicDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping(value="/api/comic/{id}")
    public ResponseEntity<ComicDTO> updateComic(@PathVariable(name = "id") Long id, @RequestBody ComicDTO comicDTO) {
        ComicDTO dto = comicsService.updateComic(id, comicDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @DeleteMapping(value="/api/comic/{id}")
    public ResponseEntity<String> deleteComic(@PathVariable(name = "id") Long id) {
        String msg = comicsService.deleteComic(id);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
