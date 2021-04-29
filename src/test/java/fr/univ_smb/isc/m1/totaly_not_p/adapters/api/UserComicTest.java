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

class UserComicTest {

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
        assertEquals("a", c1.getTitle());
        assertEquals("b", c1.getPublisher());
        assertEquals("c", c1.getWriter());
        assertEquals("d", c1.getArtist());
        assertEquals("e", c1.getPublicationDate());
        assertEquals("f", c1.getStatus());
        assertEquals("g", c1.getSummary());
        assertEquals(0, c1.getSubscribers().size());
    }

    @Test
    void addRemoveSubscriptionTest()
    {
        User u1 = new User("MichelForever", "Michel@Forever.fr", "ForeverMorever", "USER");
        Comic c1 = new Comic("a", "b", "c", "d", "e", "f", "g");

        assertEquals(0, u1.getSubscriptions().size());
        assertEquals(0, c1.getSubNb());

        u1.addSubscription(c1);

        assertEquals(1, u1.getSubscriptions().size());
        assertEquals(1, c1.getSubNb());

        u1.removeSubscription(c1);

        assertEquals(0, u1.getSubscriptions().size());
        assertEquals(0, c1.getSubNb());
    }

    /*
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
    */
}
