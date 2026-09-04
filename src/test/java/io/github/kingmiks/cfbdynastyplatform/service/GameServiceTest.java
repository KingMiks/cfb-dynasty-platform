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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

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
}
