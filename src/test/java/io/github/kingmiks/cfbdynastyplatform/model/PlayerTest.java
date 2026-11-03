package io.github.kingmiks.cfbdynastyplatform.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class PlayerTest {
    @Test 
    public void shouldCreateValidPlayer(){
        Player player = new Player("Jimu", "Myung", "Ashburn, VA");

        assertEquals("Jimu", player.getFirstName());
        assertEquals("Myung", player.getLastName());
        assertEquals("Ashburn, VA", player.getHometown());
        
    }
    @Test 
    public void shouldRejectBlankFirstName(){
        assertThrows(
                IllegalArgumentException.class,
                () -> new Player("", "Myung", "Ashburn, VA"));
    }
}
