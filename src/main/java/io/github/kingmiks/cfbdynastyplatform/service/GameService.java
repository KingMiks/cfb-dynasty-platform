package io.github.kingmiks.cfbdynastyplatform.service;

import org.springframework.stereotype.Service;

import io.github.kingmiks.cfbdynastyplatform.model.Game;
import io.github.kingmiks.cfbdynastyplatform.model.Season;
import io.github.kingmiks.cfbdynastyplatform.repository.GameRepository;

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository){
        this.gameRepository = gameRepository;
    }
    public Game createGame(int week, Season season){
        Game game = new Game(week, season);
        return gameRepository.save(game);
    }
    public Game getGame(Long id){
        return gameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Game does not exist."));
    }
}
