package fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence;

import javax.persistence.*;

import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "Comic")
public class Comic {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String author;
    private byte[] thumbnail;

    @ManyToMany(mappedBy = "subscriptions")
    private Set<User> subscribers = new HashSet<User>();

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

    @Lob
    @Column(name = "THUMBNAIL", length = 1111111, nullable = true)
    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Set<User> getSubscribers() {
        return subscribers;
    }
    public void setSubscribers(Set<User> subscribers) {
        this.subscribers = subscribers;
    }

    public String toString() {
        return title + " by " + author;
    }
}