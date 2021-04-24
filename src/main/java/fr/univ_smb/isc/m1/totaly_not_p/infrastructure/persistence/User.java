package fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name="user_id")
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id = System.nanoTime();

    private String username;

    
    @ManyToMany(mappedBy="subscribers")
    private HashSet<Comic> subscriptions = new HashSet<Comic>();
    

    public User() {
        // JPA
    }

    public User(String username) {
        this.username = username;
    }

    @Id
    public Long getId() {
        return id;
    }

    @Id
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String toString() {
        return username;
    }

    
    public HashSet<Comic> getSubscriptions() {
        return subscriptions;
    }
 
    public void setSubscriptions(HashSet<Comic> subscriptions) {
        this.subscriptions = subscriptions;
    }
    
}