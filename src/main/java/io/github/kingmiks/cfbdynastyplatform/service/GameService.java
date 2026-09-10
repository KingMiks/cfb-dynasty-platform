package io.github.kingmiks.cfbdynastyplatform.service;

import org.springframework.stereotype.Service;
import java.util.List;
import io.github.kingmiks.cfbdynastyplatform.model.Game;
import io.github.kingmiks.cfbdynastyplatform.model.GameLocation;
import io.github.kingmiks.cfbdynastyplatform.model.Season;
import io.github.kingmiks.cfbdynastyplatform.repository.GameRepository;

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game createGame(int week, Season season, String opponent, GameLocation location, int ourScore, int opponentScore) {
        Game game = new Game(week, season, opponent, location, ourScore, opponentScore);
        return gameRepository.save(game);
    }

    public Game getGame(Long id) {
        return gameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Game does not exist."));
    }

    public List<Game> getSeasonSchedule(Season season){
        return gameRepository.findBySeasonOrderByWeekAsc(season);
    }
    public boolean hasGames(){
        return gameRepository.count() > 0;
    }
    public boolean hasGameForWeek(Season season, int week){
        return gameRepository.findBySeasonAndWeek(season, week).isPresent();
    }
}
