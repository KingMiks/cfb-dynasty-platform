package io.github.kingmiks.cfbdynastyplatform.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import io.github.kingmiks.cfbdynastyplatform.model.Team;
import io.github.kingmiks.cfbdynastyplatform.service.*;

@Controller
public class HomeController {
    private final TeamService teamService;

    public HomeController(TeamService teamService){
        this.teamService = teamService;
    }

    @GetMapping("/")
    public String homePage(Model model){
        model.addAttribute("pageTitle", "Dashboard");
        model.addAttribute("team",teamService.getTeam());
        return "homepage";
    }
}
