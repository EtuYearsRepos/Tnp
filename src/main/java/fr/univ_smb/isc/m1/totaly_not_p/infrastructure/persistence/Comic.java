package fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

@Entity
public class Comic {

    @Id
    @Column(name="comic_id")
    private Long id = System.nanoTime();

    private String title;
    private String publisher;
    private String writer;
    private String artist;
    @Temporal(TemporalType.DATE)
    private Calendar date;
    private String status;
    private String summary;
    @Basic
    private ArrayList<String> issues = new ArrayList<>();
    private Integer subNb = 0;

    //@Column(name="thumbnail")
    //@JsonIgnore
    //private byte[] thumbnail;

    
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name="user_comic", joinColumns= {@JoinColumn(name="comic_id")},  inverseJoinColumns= {@JoinColumn(name="user_id")})
    private HashSet<User> subscribers = new HashSet<User>();
    

    public Comic() {
        // JPA
    }

    @Id
    public Long getId() {
        return id;
    }

    @Id
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getWriter() {
        return writer;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public void setIssues(ArrayList<String> issues) {
        this.issues = issues;
    }

    public ArrayList<String> getIssues() {
        return issues;
    }

    public Integer getSubNb() {
        return subNb;
    }

    //Shouldn't be used
    public void setSubNb(Integer subNb) {
        this.subNb = subNb;
    } 


    /*
    @Lob
    @Column(name = "THUMBNAIL", length = 1111111, nullable = true)
    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }
    */

    
    public HashSet<User> getSubscribers() {
        return subscribers;
    }
    public void setSubscribers(HashSet<User> subscribers) {
        this.subscribers = subscribers;
        this.subNb = subscribers.size();
    }

    public void addSubscriber(User user) {
        this.subscribers.add(user);
        user.getSubscriptions().add(this);
        this.subNb = subscribers.size();
    }

    public void removeSubscriber(User user) {
        this.subscribers.remove(user);
        user.getSubscriptions().remove(this);
        this.subNb = subscribers.size();
    }
    
    public void removeSubscribers() {
        for (User u : this.subscribers) {
            this.removeSubscriber(u);
        }
        this.subNb = subscribers.size();
    }

    public String toString() {
        
        /*
        String subs = new String();
        for (User u : subscribers) {
            subs += u.getUsername();
        }
        */
        
        return id + " : " + title + " by " + publisher;
    }
}