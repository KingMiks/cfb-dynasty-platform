package io.github.kingmiks.cfbdynastyplatform.repository;

import io.github.kingmiks.cfbdynastyplatform.model.Game;
import io.github.kingmiks.cfbdynastyplatform.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    
    public List<Game> findBySeasonOrderByWeekAsc(Season season);
}
