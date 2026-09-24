package io.github.kingmiks.cfbdynastyplatform.service;

import org.mockito.Mockito;

import io.github.kingmiks.cfbdynastyplatform.repository.GameRepository;
import io.github.kingmiks.cfbdynastyplatform.model.Team;
import io.github.kingmiks.cfbdynastyplatform.model.Season;
import io.github.kingmiks.cfbdynastyplatform.model.Game;
import io.github.kingmiks.cfbdynastyplatform.model.GameLocation;
import io.github.kingmiks.cfbdynastyplatform.model.GameResult;
import io.github.kingmiks.cfbdynastyplatform.dto.GameScoreUpdate;

import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class GameServiceTest {

    @Test
    public void getGameReturnsExistingGame() {
        GameRepository gameRepository = Mockito.mock(GameRepository.class);

        GameService gameService = new GameService(gameRepository);

        Team team = new Team("Ashburn Panthers");

        Season season = new Season(2026, team);

        Game game = new Game(1, season, "Wake Forest", GameLocation.HOME, 31, 24);

        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        Game result = gameService.getGame(1L);

        assertEquals(1, result.getWeek());
    }

    @Test
    public void getGameWhenNoGameExists() {

        GameRepository gameRepository = Mockito.mock(GameRepository.class);

        GameService gameService = new GameService(gameRepository);

        when(gameRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> gameService.getGame(99L));
    }

    @Test
    public void createGameSavesGame() {
        GameRepository gameRepository = Mockito.mock(GameRepository.class);

        GameService gameService = new GameService(gameRepository);

        Team team = new Team("Ashburn Panthers");

        Season season = new Season(2026, team);

        Game game = new Game(1, season, "Wake Forest", GameLocation.HOME, 31, 24);

        when(gameRepository.save(Mockito.any(Game.class))).thenReturn(game);

        Game result = gameService.createGame(1, season, "Wake Forest", GameLocation.HOME, 31, 34);

        assertEquals(game, result);
        assertEquals(1, result.getWeek());
        assertEquals("Wake Forest", result.getOpponent());
        assertEquals(GameLocation.HOME, result.getLocation());

    }

    @Test
    public void checkIfGameResultIsCorrectWhenWeWin() {

        Team team = new Team("Ashburn Panthers");

        Season season = new Season(2026, team);

        Game game = new Game(1, season, "Wake Forest", GameLocation.HOME, 31, 24);

        GameResult result = game.getResult();

        assertEquals(GameResult.WIN, result);

    }

    @Test
    public void checkIfGameResultIsCorrectWhenLose() {

        Team team = new Team("Ashburn Panthers");

        Season season = new Season(2026, team);

        Game game = new Game(1, season, "Wake Forest", GameLocation.HOME, 24, 31);

        GameResult result = game.getResult();

        assertEquals(GameResult.LOSS, result);

    }

    @Test
    public void checkIfGameResultIsCorrectWhenWeTie() {

        Team team = new Team("Ashburn Panthers");

        Season season = new Season(2026, team);

        Game game = new Game(1, season, "Wake Forest", GameLocation.HOME, 31, 31);

        assertThrows(
                IllegalStateException.class,
                () -> game.getResult());

    }

    @Test
    public void checkIfGetSeasonScheduleIsCorrect() {
        GameRepository gameRepository = Mockito.mock(GameRepository.class);

        GameService gameService = new GameService(gameRepository);

        Team team = new Team("Ashburn Panthers");

        Season season = new Season(2026, team);

        Game game = new Game(1, season, "Wake Forest", GameLocation.HOME, 31, 24);
        Game game2 = new Game(2, season, "Clemson", GameLocation.AWAY, 34, 21);

        when(gameRepository.findBySeasonOrderByWeekAsc(season)).thenReturn(List.of(game, game2));

        List<Game> result = gameService.getSeasonSchedule(season);

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getWeek());
        assertEquals(2, result.get(1).getWeek());

    }

    @Test
    public void checkIfHasGameForWeekHasGame() {
        GameRepository gameRepository = Mockito.mock(GameRepository.class);
        GameService gameService = new GameService(gameRepository);

        Team team = new Team("Ashburn Panthers");

        Season season = new Season(2026, team);

        Game game = new Game(1, season, "Wake Forest", GameLocation.HOME, 31, 24);

        when(gameRepository.findBySeasonAndWeek(season, 1)).thenReturn(Optional.of(game));

        boolean result = gameService.hasGameForWeek(season, 1);

        assertTrue(result);
    }

    @Test
    public void checkIfHasGameForWeekHasNoGame() {
        GameRepository gameRepository = Mockito.mock(GameRepository.class);
        GameService gameService = new GameService(gameRepository);

        Team team = new Team("Ashburn Panthers");

        Season season = new Season(2026, team);

        when(gameRepository.findBySeasonAndWeek(season, 1)).thenReturn(Optional.empty());

        boolean result = gameService.hasGameForWeek(season, 1);

        assertFalse(result);
    }

    @Test
    public void checkIfGameResultIsCorrectWhenNotPlayed() {

        Team team = new Team("Ashburn Panthers");

        Season season = new Season(2026, team);

        Game game = new Game(1, season, "Wake Forest", GameLocation.HOME, null, null);

        GameResult result = game.getResult();

        assertEquals(GameResult.SCHEDULED, result);
    }

    @Test
    public void checkIfGameResultIsCorrectWhenWeOneIsNull() {

        Team team = new Team("Ashburn Panthers");

        Season season = new Season(2026, team);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Game(1, season, "Wake Forest", GameLocation.HOME, null, 31));

    }

    @Test
    public void checkIfGameResultIsRecorded() {

        Team team = new Team("Ashburn Panthers");

        Season season = new Season(2026, team);

        Game game = new Game(1, season, "Wake Forest", GameLocation.HOME, null, null);

        game.recordResult(31, 27);

        assertEquals(31, game.getOurScore());
        assertEquals(27, game.getOpponentScore());
        assertEquals(GameResult.WIN, game.getResult());
    }

    @Test
    public void recordGameResultUpdatesAndSavesGame() {

        GameRepository gameRepository = Mockito.mock(GameRepository.class);

        GameService gameService = new GameService(gameRepository);

        Team team = new Team("Ashburn Panthers");

        Season season = new Season(2026, team);

        Game game = new Game(1, season, "Wake Forest", GameLocation.HOME, null, null);

        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        when(gameRepository.save(Mockito.any(Game.class))).thenReturn(game);

        Game result = gameService.recordGameResult(1L, 31, 27);

        assertEquals(31, result.getOurScore());
        assertEquals(27, result.getOpponentScore());
        assertEquals(GameResult.WIN, result.getResult());
        verify(gameRepository).save(game);

    }

    @Test
    public void checkIfGameResultStaysTheSameIfScoresDontChange() {

        GameRepository gameRepository = Mockito.mock(GameRepository.class);

        GameService gameService = new GameService(gameRepository);

        Team team = new Team("Ashburn Panthers");

        Season season = new Season(2026, team);

        Game game = new Game(1, season, "Wake Forest", GameLocation.HOME, 31, 27);

        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        Game result = gameService.recordGameResult(1L, 31, 27);

        assertEquals(31, result.getOurScore());
        assertEquals(27, result.getOpponentScore());
        assertEquals(GameResult.WIN, result.getResult());
        verify(gameRepository, never()).save(any(Game.class));

    }

    @Test
    public void checkIfRecordGameResultRejectsNullScores() {

        GameRepository gameRepository = Mockito.mock(GameRepository.class);

        GameService gameService = new GameService(gameRepository);

        Team team = new Team("Ashburn Panthers");

        Season season = new Season(2026, team);

        Game game = new Game(1, season, "Wake Forest", GameLocation.HOME, null, null);

        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        assertThrows(
                IllegalArgumentException.class,
                () -> gameService.recordGameResult(1L, null, null));
        verify(gameRepository, never()).save(any(Game.class));
    }

    @Test
    public void testIfGameScoreUpdateIsCorrect() {

        GameRepository gameRepository = Mockito.mock(GameRepository.class);

        GameService gameService = new GameService(gameRepository);

        Team team = new Team("Ashburn Panthers");

        Season season = new Season(2026, team);

        Game game = new Game(1, season, "Wake Forest", GameLocation.HOME, null, null);

        Game game3 = new Game(3, season, "Virginia Tech", GameLocation.NEUTRAL, null, null);

        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        when(gameRepository.findById(3L)).thenReturn(Optional.of(game3));
        when(gameRepository.save(Mockito.any(Game.class))).thenReturn(game, game3);

        GameScoreUpdate update1 = new GameScoreUpdate();
        update1.setGameId(1L);
        update1.setOurScore(27);
        update1.setOpponentScore(7);
        GameScoreUpdate update2 = new GameScoreUpdate();
        update2.setGameId(2L);
        update2.setOurScore(null);
        update2.setOpponentScore(null);
        GameScoreUpdate update3 = new GameScoreUpdate();
        update3.setGameId(3L);
        update3.setOurScore(35);
        update3.setOpponentScore(24);
        List<GameScoreUpdate> updates = List.of(update1, update2, update3);
        gameService.updateGameScores(updates);
        verify(gameRepository, never()).findById(2L);
        assertEquals(27, game.getOurScore());
        assertEquals(7, game.getOpponentScore());
        assertEquals(GameResult.WIN, game.getResult());
        assertEquals(35, game3.getOurScore());
        assertEquals(24, game3.getOpponentScore());
        assertEquals(GameResult.WIN, game3.getResult());

    }

    @Test
    public void updateGameScoresRejectsIncompleteScore() {
        GameRepository gameRepository = Mockito.mock(GameRepository.class);

        GameService gameService = new GameService(gameRepository);

        GameScoreUpdate update = new GameScoreUpdate();
        update.setGameId(2L);
        update.setOurScore(27);
        update.setOpponentScore(null);
        List<GameScoreUpdate> updates = List.of(update);
        assertThrows(
                IllegalStateException.class,
                () -> gameService.updateGameScores(updates));
        verify(gameRepository, never()).findById(2L);
        verify(gameRepository, never()).save(Mockito.any(Game.class));
    }

    @Test
    public void updateGameScoresSkipsUnchangedGames() {
        GameRepository gameRepository = Mockito.mock(GameRepository.class);

        GameService gameService = new GameService(gameRepository);
        Team team = new Team("Ashburn Panthers");

        Season season = new Season(2026, team);

        Game game = new Game(1, season, "Wake Forest", GameLocation.HOME, 27, 7);

        Game game2 = new Game(2, season, "Virginia Tech", GameLocation.NEUTRAL, 31, 24);

        when(gameRepository.findById(1L))
                .thenReturn(Optional.of(game));

        when(gameRepository.findById(2L))
                .thenReturn(Optional.of(game2));
        GameScoreUpdate update1 = new GameScoreUpdate();
        update1.setGameId(1L);
        update1.setOurScore(27);
        update1.setOpponentScore(7);
        GameScoreUpdate update2 = new GameScoreUpdate();
        update2.setGameId(2L);
        update2.setOurScore(35);
        update2.setOpponentScore(24);
        List<GameScoreUpdate> updates = List.of(update1, update2);
        gameService.updateGameScores(updates);

        verify(gameRepository, times(1)).save(any(Game.class));
        assertEquals(27, game.getOurScore());
        assertEquals(7, game.getOpponentScore());
        assertEquals(GameResult.WIN, game.getResult());
        assertEquals(35, game2.getOurScore());
        assertEquals(24, game2.getOpponentScore());
        assertEquals(GameResult.WIN, game2.getResult());

    }

}
