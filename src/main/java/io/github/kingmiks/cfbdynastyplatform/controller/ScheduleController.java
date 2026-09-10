package io.github.kingmiks.cfbdynastyplatform.controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.github.kingmiks.cfbdynastyplatform.service.GameService;
import io.github.kingmiks.cfbdynastyplatform.service.SeasonService;
import io.github.kingmiks.cfbdynastyplatform.model.Season;
import io.github.kingmiks.cfbdynastyplatform.model.Game;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;

@Controller 
public class ScheduleController {
    private final GameService gameService;
    private final SeasonService seasonService;

    public ScheduleController(GameService gameService, SeasonService seasonService){
        this.gameService = gameService;
        this.seasonService = seasonService;
    }
    
    @GetMapping("/schedule")
    public String schedulePage(Model model){
        Season season = seasonService.getCurrentSeason();
        List<Game> games = gameService.getSeasonSchedule(season);
        model.addAttribute("season", season);
        model.addAttribute("games", games);
        return "schedule";
    }
}
