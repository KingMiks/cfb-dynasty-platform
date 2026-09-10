package io.github.kingmiks.cfbdynastyplatform.service;

import org.springframework.stereotype.Service;

import io.github.kingmiks.cfbdynastyplatform.repository.SeasonRepository;
import io.github.kingmiks.cfbdynastyplatform.model.Season;
import io.github.kingmiks.cfbdynastyplatform.model.Team;
import java.util.List;

@Service
public class SeasonService {
    private final SeasonRepository seasonRepository;

    public SeasonService(SeasonRepository seasonRepository){
        this.seasonRepository = seasonRepository;
    }
    public Season createSeason(int year, Team team){
        Season season = new Season(year, team);
        return seasonRepository.save(season);
    }

    public Season getSeason(Long id){
        return seasonRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Season does not exist."));
    }

    public Season getCurrentSeason(){
        List<Season> seasons = seasonRepository.findAll();
        if (seasons.isEmpty()){
            throw new IllegalStateException("Season doesn't exist.");
        }
        if (seasons.size() > 1){
            throw new IllegalStateException("Cannot determine current season when more than one season exists.");
        }
        return seasons.get(0);
    }

    public boolean hasSeason(){
        return seasonRepository.count() > 0;
    }
}
