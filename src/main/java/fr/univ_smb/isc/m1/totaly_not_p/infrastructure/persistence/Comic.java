package fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Comic {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String author;

    public Comic() {
        // JPA
    }

    public Comic(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String name)
    {
        author = name;
    }
    
    public String toString() {
        return title + " by " + author;
    }
}