package io.github.kingmiks.cfbdynastyplatform.config;

import org.springframework.stereotype.Component;

import io.github.kingmiks.cfbdynastyplatform.service.GameService;
import io.github.kingmiks.cfbdynastyplatform.service.SeasonService;
import io.github.kingmiks.cfbdynastyplatform.service.TeamService;
import io.github.kingmiks.cfbdynastyplatform.model.GameLocation;
import io.github.kingmiks.cfbdynastyplatform.model.Team;
import io.github.kingmiks.cfbdynastyplatform.model.Season;

import org.springframework.boot.CommandLineRunner;

@Component
public class DataInitializer implements CommandLineRunner {

    private final TeamService teamService;
    private final SeasonService seasonService;
    private final GameService gameService;

    public DataInitializer(TeamService teamService, SeasonService seasonService, GameService gameService) {
        this.teamService = teamService;
        this.seasonService = seasonService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!teamService.hasTeams()) {
            teamService.createTeam("Ashburn Panthers");
        }

        Team team = teamService.getTeam();

        if (!seasonService.hasSeason()) {
            seasonService.createSeason(2026, team);
        }
        Season season = seasonService.getCurrentSeason();
        if (!gameService.hasGameForWeek(season, 1)) {
            gameService.createGame(1, season, "Wake Forest", GameLocation.HOME, 21, 0);
        }
        if (!gameService.hasGameForWeek(season, 2)) {
            gameService.createGame(2, season, "Clemson", GameLocation.AWAY, 14, 7);
        }

        if (!gameService.hasGameForWeek(season, 7)) {
            gameService.createGame(7, season, "Ohio State", GameLocation.HOME, 24, 21);
        }
    }
}
