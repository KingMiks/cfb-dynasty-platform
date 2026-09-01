package io.github.kingmiks.cfbdynastyplatform.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import io.github.kingmiks.cfbdynastyplatform.model.Season;

public interface SeasonRepository extends JpaRepository<Season, Long>{
    
}
