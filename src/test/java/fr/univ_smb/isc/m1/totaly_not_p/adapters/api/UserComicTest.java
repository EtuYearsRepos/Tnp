package fr.univ_smb.isc.m1.totaly_not_p.adapters.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univ_smb.isc.m1.totaly_not_p.application.ComicsService;
import fr.univ_smb.isc.m1.totaly_not_p.application.UserService;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.Comic;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.user.User;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.user.UserDTO;

public class UserComicTest {

    private ComicsService comicService;
    private UserService userService;

    @BeforeEach
    void init()
    {
        UserService userService = mock(UserService.class);
        ComicsService comicService = mock(ComicsService.class);
        this.comicService = comicService;
        this.userService = userService;
    }

    @Test
    void createUserTest()
    {
        User u1 = new User("MichelForever", "Michel@Forever.fr", "ForeverMorever", "USER");
        assertNotNull(u1);
        assertNotNull(u1.getId());
        
        assertEquals("MichelForever", u1.getUsername());
        assertEquals("Michel@Forever.fr", u1.getEmail());
        assertEquals("USER", u1.getRole());
        assertTrue(u1.getSubscriptions().size() == 0);
    }

    @Test
    void createComicTest()
    {
        Comic c1 = new Comic("a", "b", "c", "d", "e", "f", "g");
        assertNotNull(c1);
        assertNotNull(c1.getId());
        assertEquals(c1.getTitle(), "a");
        assertEquals(c1.getPublisher(), "b");
        assertEquals(c1.getWriter(), "c");
        assertEquals(c1.getArtist(), "d");
        assertEquals(c1.getPublicationDate(), "e");
        assertEquals(c1.getStatus(), "f");
        assertEquals(c1.getSummary(), "g");
        assertTrue(c1.getSubscribers().size() == 0);
    }

    @Test
    void addRemoveSubscriptionTest()
    {
        User u1 = new User("MichelForever", "Michel@Forever.fr", "ForeverMorever", "USER");
        Comic c1 = new Comic("a", "b", "c", "d", "e", "f", "g");

        assertTrue(u1.getSubscriptions().size() == 0);
        assertTrue(c1.getSubNb() == 0);

        u1.addSubscription(c1);

        assertTrue(u1.getSubscriptions().size() == 1);
        assertTrue(c1.getSubNb() == 1);

        u1.removeSubscription(c1);

        assertTrue(u1.getSubscriptions().size() == 0);
        assertTrue(c1.getSubNb() == 0);
    }

    @Test
    void insertUser()
    {
        User u1 = new User("MichelForever", "Michel@Forever.fr", "ForeverMorever", "USER");
        UserDTO dto = new UserDTO();
        dto.setUsername(u1.getUsername());
        dto.setPassword(u1.getPassword());
        dto.setEmail(u1.getEmail());
        userService.save(dto);

        assertTrue(userService.allUsers().size() == 1);

    }
}
