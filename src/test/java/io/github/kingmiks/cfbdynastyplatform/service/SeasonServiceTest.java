package io.github.kingmiks.cfbdynastyplatform.service;

import io.github.kingmiks.cfbdynastyplatform.model.Team;

import io.github.kingmiks.cfbdynastyplatform.model.Season;
import io.github.kingmiks.cfbdynastyplatform.repository.SeasonRepository;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class SeasonServiceTest {
    @Test
    void getSeasonReturnsExistingSeason() {
        SeasonRepository seasonRepository = Mockito.mock(SeasonRepository.class);

        SeasonService seasonService = new SeasonService(seasonRepository);

        Team team = new Team("Ashburn Panthers");

        Season season = new Season(2026, team);

        when(seasonRepository.findById(1L)).thenReturn(Optional.of(season));

        Season result = seasonService.getSeason(1L);

        assertEquals(2026, result.getYear());

    }

    @Test
    void getSeasonWhenNoSeasonExists() {
        SeasonRepository seasonRepository = Mockito.mock(SeasonRepository.class);

        SeasonService seasonService = new SeasonService(seasonRepository);

        when(seasonRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> seasonService.getSeason(99L));

    }

    @Test
    void createSeasonSavesSeason() {
        SeasonRepository seasonRepository = Mockito.mock(SeasonRepository.class);

        SeasonService seasonService = new SeasonService(seasonRepository);
        Team team = new Team("Ashburn panthers");
        Season season = new Season(2026, team);
        when(seasonRepository.save(Mockito.any(Season.class))).thenReturn(season);
        Season result = seasonService.createSeason(2026, team);

        assertEquals(season, result);
        assertEquals(2026, result.getYear());

    }
}
