package io.github.kingmiks.cfbdynastyplatform.service;

import org.mockito.Mockito;

import io.github.kingmiks.cfbdynastyplatform.repository.GameRepository;
import io.github.kingmiks.cfbdynastyplatform.model.Team;
import io.github.kingmiks.cfbdynastyplatform.model.Season;
import io.github.kingmiks.cfbdynastyplatform.model.Game;

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

        Game game = new Game(1, season);

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

        Game game = new Game(1, season);

        when(gameRepository.save(Mockito.any(Game.class))).thenReturn(game);

        Game result = gameService.createGame(1, season);

        assertEquals(game, result);
        assertEquals(1, result.getWeek());

    }
}
