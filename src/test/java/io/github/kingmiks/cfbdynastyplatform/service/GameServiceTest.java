package io.github.kingmiks.cfbdynastyplatform.service;

import org.mockito.Mockito;

import io.github.kingmiks.cfbdynastyplatform.repository.GameRepository;
import io.github.kingmiks.cfbdynastyplatform.model.Team;
import io.github.kingmiks.cfbdynastyplatform.model.Season;
import io.github.kingmiks.cfbdynastyplatform.model.Game;
import io.github.kingmiks.cfbdynastyplatform.model.GameLocation;
import io.github.kingmiks.cfbdynastyplatform.model.GameResult;

import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;

public class GameServiceTest {
    
    @Test
    public void getGameReturnsExistingGame(){
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
    public void getGameWhenNoGameExists(){

        GameRepository gameRepository = Mockito.mock(GameRepository.class);

        GameService gameService = new GameService(gameRepository);

        when(gameRepository.findById(99L)).thenReturn(Optional.empty());


        assertThrows(
                IllegalArgumentException.class,
                () -> gameService.getGame(99L));
    }

    @Test
    public void createGameSavesGame(){
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
    public void checkIfGameResultIsCorrectWhenWeWin(){

        Team team = new Team("Ashburn Panthers");

        Season season = new Season(2026, team);

        Game game = new Game(1, season, "Wake Forest", GameLocation.HOME, 31, 24);

        GameResult result = game.getResult();

        assertEquals(GameResult.WIN, result);
        
    }
    @Test
    public void checkIfGameResultIsCorrectWhenLose(){

        Team team = new Team("Ashburn Panthers");

        Season season = new Season(2026, team);

        Game game = new Game(1, season, "Wake Forest", GameLocation.HOME, 24, 31);

        GameResult result = game.getResult();

        assertEquals(GameResult.LOSS, result);
        
    }

    @Test
    public void checkIfGameResultIsCorrectWhenWeTie(){

        Team team = new Team("Ashburn Panthers");

        Season season = new Season(2026, team);

        Game game = new Game(1, season, "Wake Forest", GameLocation.HOME, 31, 31);

        assertThrows(
                IllegalStateException.class,
                () -> game.getResult());
        
    }

    @Test
    public void checkIfGetSeasonScheduleIsCorrect(){
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
    public void checkIfHasGameForWeekHasGame(){
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
    public void checkIfHasGameForWeekHasNoGame(){
        GameRepository gameRepository = Mockito.mock(GameRepository.class);
        GameService gameService = new GameService(gameRepository);

        Team team = new Team("Ashburn Panthers");

        Season season = new Season(2026, team);

        when(gameRepository.findBySeasonAndWeek(season, 1)).thenReturn(Optional.empty());

        boolean result = gameService.hasGameForWeek(season, 1);

        assertFalse(result);
    }

    @Test
    public void checkIfGameResultIsCorrectWhenNotPlayed(){

        Team team = new Team("Ashburn Panthers");

        Season season = new Season(2026, team);

        Game game = new Game(1, season, "Wake Forest", GameLocation.HOME, null, null);

        GameResult result = game.getResult();

        assertEquals(GameResult.SCHEDULED, result);
    }
    @Test
    public void checkIfGameResultIsCorrectWhenWeOneIsNull(){

        Team team = new Team("Ashburn Panthers");

        Season season = new Season(2026, team);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Game(1, season, "Wake Forest", GameLocation.HOME, null, 31));
        
    }

    @Test
    public void checkIfGameResultIsRecorded(){

        Team team = new Team("Ashburn Panthers");

        Season season = new Season(2026, team);

        Game game = new Game(1, season, "Wake Forest", GameLocation.HOME, null, null);

        game.recordResult(31, 27);

        assertEquals(31, game.getOurScore());
        assertEquals(27, game.getOpponentScore());
        assertEquals(GameResult.WIN, game.getResult());
    }

    @Test
    public void recordGameResultUpdatesAndSavesGame(){

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

}

