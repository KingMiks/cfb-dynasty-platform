package io.github.kingmiks.cfbdynastyplatform.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import io.github.kingmiks.cfbdynastyplatform.model.Team;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String homePage(Model model){
        model.addAttribute("pageTitle", "Dashboard");
        Team team = new Team("Ashburn Panthers");
        model.addAttribute("team", team);
        return "homepage";
    }
    
}
