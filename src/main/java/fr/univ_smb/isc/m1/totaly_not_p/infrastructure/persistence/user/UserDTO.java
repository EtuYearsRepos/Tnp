package fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.user;

import org.springframework.lang.NonNull;

public class UserDTO {
    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String matchingPassword;

    @NonNull
    private String email;
    

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String fn)
    {
        firstName = fn;
    }


    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String ln)
    {
        lastName = ln;
    }


    public String getUsername()
    {
        return username;
    }

    public void setUsername(String us)
    {
        username = us;
    }


    public String getPassword()
    {
        return password;
    }

    public void setPassword(String pw)
    {
        password = pw;
    }


    public String getMatchingPassword()
    {
        return matchingPassword;
    }

    public void setMatchingPassword(String mpw)
    {
        matchingPassword = mpw;
    }

    
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String em)
    {
        email = em;
    }

    public String toString()
    {
        return username + " with " + password + "  " + matchingPassword;
    }
}