package fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.user;

import java.util.HashSet;

import javax.persistence.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.Comic;

@Entity
@Table(name = "usertable")
@Transactional
public class User {

    @Id
    @Column(name="id")
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id = System.nanoTime();

    private String username;
    private String password;
    private String role;

    
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name="usertable_comic", joinColumns= {@JoinColumn(name="user_id")},  inverseJoinColumns= {@JoinColumn(name="comic_id")})
    private HashSet<Comic> subscriptions = new HashSet<Comic>();
    

    public User() {
        // JPA
    }

    public User(String username, String password, String role) {
        this.username = username;
        this.password = new BCryptPasswordEncoder().encode(password);
        this.role = role;
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

    public void addSubscription(Comic comic) {
        if (this.subscriptions.add(comic)) {
            comic.setSubNb(comic.getSubNb() + 1);
        };
    }

    public void removeSubscription(Comic comic) {

        for (Comic c : this.subscriptions) {
            if (c.getId().longValue() == comic.getId().longValue()) {
                this.subscriptions.remove(c);
                comic.setSubNb(comic.getSubNb() - 1);
                return;
            }
        }
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}