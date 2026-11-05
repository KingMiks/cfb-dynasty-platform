package io.github.kingmiks.cfbdynastyplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.kingmiks.cfbdynastyplatform.model.Player;

public interface PlayerRepository extends JpaRepository<Player, Long>{
    
}
