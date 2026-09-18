package io.github.kingmiks.cfbdynastyplatform.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.kingmiks.cfbdynastyplatform.repository.GameRepository;
import io.github.kingmiks.cfbdynastyplatform.repository.SeasonRepository;
import io.github.kingmiks.cfbdynastyplatform.repository.TeamRepository;

import io.github.kingmiks.cfbdynastyplatform.model.Team;
import io.github.kingmiks.cfbdynastyplatform.model.Season;
import io.github.kingmiks.cfbdynastyplatform.model.Game;
import io.github.kingmiks.cfbdynastyplatform.model.GameLocation;
import io.github.kingmiks.cfbdynastyplatform.model.GameResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.util.List;
import io.github.kingmiks.cfbdynastyplatform.dto.GameScoreUpdate;

@SpringBootTest
public class GameServiceIntegrationTest {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private GameService gameService;

    @BeforeEach
    void cleanDatabaseBeforeTest() {
        gameRepository.deleteAll();
        seasonRepository.deleteAll();
        teamRepository.deleteAll();
    }

    @Test
    public void updateGameScoresRollsBackWhenOneUpdateFails() {
        Team team = new Team("Ashburn Panthers");
        Season season = new Season(2026, team);
        Game game = new Game(1, season, "Wake Forest", GameLocation.HOME, 21, 14);
        Game game2 = new Game(2, season, "Clemson", GameLocation.AWAY, 24, 17);
        teamRepository.save(team);
        seasonRepository.save(season);
        gameRepository.save(game);
        gameRepository.save(game2);
        GameScoreUpdate update1 = new GameScoreUpdate();
        update1.setGameId(game.getID());
        update1.setOurScore(28);
        update1.setOpponentScore(14);

        GameScoreUpdate update2 = new GameScoreUpdate();
        update2.setGameId(game2.getID());
        update2.setOurScore(31);
        update2.setOpponentScore(31);

        List<GameScoreUpdate> updates = List.of(update1, update2);
        assertThrows(IllegalStateException.class,
                () -> gameService.updateGameScores(updates));

        Game reloadedGame = gameRepository.findById(game.getID()).orElseThrow();
        assertEquals(21, reloadedGame.getOurScore());
        assertEquals(14, reloadedGame.getOpponentScore());
        assertEquals(GameResult.WIN, reloadedGame.getResult());
    }

    @AfterEach
    void cleanDatabaseAfterTest() {
        gameRepository.deleteAll();
        seasonRepository.deleteAll();
        teamRepository.deleteAll();
    }
}