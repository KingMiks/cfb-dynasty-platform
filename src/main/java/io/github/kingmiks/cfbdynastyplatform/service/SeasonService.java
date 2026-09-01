package io.github.kingmiks.cfbdynastyplatform.service;

import org.springframework.stereotype.Service;

import io.github.kingmiks.cfbdynastyplatform.repository.SeasonRepository;
import io.github.kingmiks.cfbdynastyplatform.model.Season;
import io.github.kingmiks.cfbdynastyplatform.model.Team;

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
}
