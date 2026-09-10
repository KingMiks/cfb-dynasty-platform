package io.github.kingmiks.cfbdynastyplatform.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import io.github.kingmiks.cfbdynastyplatform.model.Game;
import io.github.kingmiks.cfbdynastyplatform.model.GameLocation;
import io.github.kingmiks.cfbdynastyplatform.model.Season;
import io.github.kingmiks.cfbdynastyplatform.model.Team;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Optional;

import java.util.List;

@DataJpaTest
public class GameRepositoryTest {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private SeasonRepository seasonRepository;

    @Test
    public void shouldReturnSeasonGamesOrderedByWeek() {

        Team team = new Team("Ashburn Panthers");
        teamRepository.save(team);

        Season season = new Season(2026, team);
        seasonRepository.save(season);

        Game game = new Game(10, season, "Virginia Tech", GameLocation.NEUTRAL, 20, 0);
        gameRepository.save(game);

        Game game2 = new Game(1, season, "Wake Forest", GameLocation.HOME, 34, 21);
        gameRepository.save(game2);

        Game game3 = new Game(7, season, "Ohio State", GameLocation.HOME, 40, 21);
        gameRepository.save(game3);

        List<Game> result = gameRepository.findBySeasonOrderByWeekAsc(season);
        Optional<Game> weekSevenGame = gameRepository.findBySeasonAndWeek(season, 7);

        assertTrue(weekSevenGame.isPresent());
        assertEquals(7, weekSevenGame.get().getWeek());

        assertEquals(3, result.size());
        assertEquals(1, result.get(0).getWeek());
        assertEquals(7, result.get(1).getWeek());
        assertEquals(10, result.get(2).getWeek());

    }

}