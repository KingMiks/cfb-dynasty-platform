package io.github.kingmiks.cfbdynastyplatform.repository;

import io.github.kingmiks.cfbdynastyplatform.model.Game;
import io.github.kingmiks.cfbdynastyplatform.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    
    public List<Game> findBySeasonOrderByWeekAsc(Season season);
    public Optional<Game> findBySeasonAndWeek(Season season, int week);
}
