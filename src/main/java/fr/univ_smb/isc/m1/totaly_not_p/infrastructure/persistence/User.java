package fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.*;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "User_Comic", 
        joinColumns = { @JoinColumn(name = "user_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "comic_id") }
    )
    private Set<Comic> subscriptions = new HashSet<Comic>();

    public User() {
        // JPA
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String toString() {
        return firstName + " " + lastName;
    }

    public Set<Comic> getSubscriptions() {
        return subscriptions;
    }
 
    public void setSubscriptions(Set<Comic> subscriptions) {
        this.subscriptions = subscriptions;
    }
}