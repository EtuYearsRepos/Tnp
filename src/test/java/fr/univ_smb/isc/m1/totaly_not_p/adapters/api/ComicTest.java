package fr.univ_smb.isc.m1.totaly_not_p.adapters.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.Comic;

public class ComicTest {

    @Test
    public void testGetTitle() throws NoSuchFieldException, IllegalAccessException
    {
        final Comic comicTest = new Comic();

        comicTest.setTitle("title");

        final Field field = comicTest.getClass().getDeclaredField("value");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(comicTest), "title");
    }
    
}
