package io.github.kingmiks.cfbdynastyplatform.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.kingmiks.cfbdynastyplatform.model.Team;
import io.github.kingmiks.cfbdynastyplatform.model.Season;
import io.github.kingmiks.cfbdynastyplatform.model.ClassYear;
import io.github.kingmiks.cfbdynastyplatform.model.Position;
import io.github.kingmiks.cfbdynastyplatform.model.Player;
import io.github.kingmiks.cfbdynastyplatform.model.RosterSpot;


@SpringBootTest
@Transactional
public class RosterSpotRepositoryIntegrationTest {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private RosterSpotRepository rosterSpotRepository;
    @Autowired
    private SeasonRepository seasonRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void shouldSaveAndRetrieveRosterSpot() {
        Team team = new Team("Ashburn Panthers");
        teamRepository.save(team);

        Season season = new Season(2026, team);
        seasonRepository.save(season);

        Player player = new Player("Jimu", "Myung", "Ashburn, VA");
        playerRepository.save(player);

        RosterSpot spot = new RosterSpot(player, season, Position.QB, 2, ClassYear.FR, false);
        rosterSpotRepository.save(spot);
        Optional<RosterSpot> result = rosterSpotRepository.findById(spot.getID());
        RosterSpot retrievedSpot = result.orElseThrow();

        assertEquals(2, retrievedSpot.getJerseyNumber());
        assertEquals(Position.QB, retrievedSpot.getPosition());
        assertEquals(player, retrievedSpot.getPlayer());

    }

    @Test
    public void shouldRejectDuplicatePlayerInSameSeason() {
        Team team = teamRepository.save(new Team("Ashburn Panthers"));

        Season season = seasonRepository.save(new Season(2026, team));

        Player player = playerRepository.save(
                new Player("Jimu", "Myung", "Ashburn, VA"));

        RosterSpot firstSpot = new RosterSpot(
                player, season, Position.QB, 2, ClassYear.FR, false);

        rosterSpotRepository.saveAndFlush(firstSpot);

        RosterSpot duplicateSpot = new RosterSpot(
                player, season, Position.QB, 7, ClassYear.FR, false);

        assertThrows(
                DataIntegrityViolationException.class,
                () -> rosterSpotRepository.saveAndFlush(duplicateSpot));
    }
}
