package io.github.kingmiks.cfbdynastyplatform.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import java.util.Optional;

import io.github.kingmiks.cfbdynastyplatform.model.ClassYear;
import io.github.kingmiks.cfbdynastyplatform.model.RosterSpot;
import io.github.kingmiks.cfbdynastyplatform.repository.RosterSpotRepository;
import io.github.kingmiks.cfbdynastyplatform.model.Player;
import io.github.kingmiks.cfbdynastyplatform.model.Season;
import io.github.kingmiks.cfbdynastyplatform.model.Team;
import io.github.kingmiks.cfbdynastyplatform.model.Position;

@ExtendWith(MockitoExtension.class)
public class RosterServiceTest {

    @Mock
    private RosterSpotRepository rosterSpotRepository;

    @InjectMocks
    private RosterService rosterService;

    @Test
    public void shouldGetRosterSpotById() {
        // Arrange: Prepare our mock data
        Player player = new Player("Jimu", "Myung", "Ashburn, VA");
        Team team = new Team("Ashburn Panthers");
        Season season = new Season(2026, team);
        RosterSpot spot = new RosterSpot(
                player, season, Position.QB, 2, ClassYear.FR, false);
        // Arrange: Configure the repository mock
        when(rosterSpotRepository.findById(5L)).thenReturn(Optional.of(spot));

        // Act
        RosterSpot result = rosterService.getRosterSpot(5L);

        // Assert
        assertSame(spot, result);

    }

    @Test
    public void shouldThrowExceptionWhenRosterSpotIsNotFound() {
        // Arrange
        when(rosterSpotRepository.findById(5L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            rosterService.getRosterSpot(5L);
        });

    }
}
