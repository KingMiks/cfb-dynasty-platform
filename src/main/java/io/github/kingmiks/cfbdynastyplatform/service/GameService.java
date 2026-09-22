package io.github.kingmiks.cfbdynastyplatform.service;

import org.springframework.stereotype.Service;
import java.util.List;
import io.github.kingmiks.cfbdynastyplatform.model.Game;
import io.github.kingmiks.cfbdynastyplatform.model.GameLocation;
import io.github.kingmiks.cfbdynastyplatform.model.Season;
import io.github.kingmiks.cfbdynastyplatform.repository.GameRepository;
import io.github.kingmiks.cfbdynastyplatform.dto.GameScoreUpdate;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game createGame(int week, Season season, String opponent, GameLocation location, Integer ourScore,
            Integer opponentScore) {
        Game game = new Game(week, season, opponent, location, ourScore, opponentScore);
        return gameRepository.save(game);
    }

    public Game getGame(Long id) {
        return gameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Game does not exist."));
    }

    public List<Game> getSeasonSchedule(Season season) {
        return gameRepository.findBySeasonOrderByWeekAsc(season);
    }

    public boolean hasGames() {
        return gameRepository.count() > 0;
    }

    public boolean hasGameForWeek(Season season, int week) {
        return gameRepository.findBySeasonAndWeek(season, week).isPresent();
    }

    public Game recordGameResult(Long id, Integer ourScore, Integer opponentScore) {
        Game game = getGame(id);
        if (ourScore == null || opponentScore == null) {
            throw new IllegalArgumentException("Scores must not be null.");
        }
        if (Objects.equals(game.getOurScore(), ourScore) && Objects.equals(game.getOpponentScore(), opponentScore)) {
            return game;
        }
        game.recordResult(ourScore, opponentScore);
        return gameRepository.save(game);
    }

    @Transactional
    public void updateGameScores(List<GameScoreUpdate> updates) {
        for (GameScoreUpdate update : updates) {

            if (update.getOurScore() == null && update.getOpponentScore() == null) {
                continue;
            }
            if (update.getOurScore() == null || update.getOpponentScore() == null) {
                throw new IllegalStateException("Both score entries must have a score.");
            }
            recordGameResult(update.getGameId(), update.getOurScore(), update.getOpponentScore());
        }
    }
}
