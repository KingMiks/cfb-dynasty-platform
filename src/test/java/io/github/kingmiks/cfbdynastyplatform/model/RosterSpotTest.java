package io.github.kingmiks.cfbdynastyplatform.model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RosterSpotTest {
    @Test
    public void shouldRejectInvalidJerseyNumber(){
        Player player = new Player("Jimu", "Myung", "Ashburn,VA");
        Season season = mock(Season.class);
        assertThrows(
            IllegalArgumentException.class,
            () -> new RosterSpot(player, season, Position.QB, 100, ClassYear.FR, false)

        );
    }
    @Test
    public void shouldCreateValidRosterSpot(){
        Player player = new Player("Jimu", "Myung", "Ashburn,VA");
        Season season = mock(Season.class);
        RosterSpot spot = new RosterSpot(player, season, Position.QB, 2, ClassYear.FR, false);
        assertEquals(player, spot.getPlayer());
        assertEquals(season, spot.getSeason());
        assertEquals(Position.QB, spot.getPosition());
        assertEquals(2, spot.getJerseyNumber());
        assertEquals(ClassYear.FR, spot.getClassYear());
        assertFalse(spot.isRedshirt());

    }
}
