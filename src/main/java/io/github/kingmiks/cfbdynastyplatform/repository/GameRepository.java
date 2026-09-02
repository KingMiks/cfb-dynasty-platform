package io.github.kingmiks.cfbdynastyplatform.repository;

import io.github.kingmiks.cfbdynastyplatform.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
    
}
