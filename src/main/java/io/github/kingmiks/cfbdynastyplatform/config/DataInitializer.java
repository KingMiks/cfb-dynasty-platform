package io.github.kingmiks.cfbdynastyplatform.config;
import org.springframework.stereotype.Component;

import io.github.kingmiks.cfbdynastyplatform.service.TeamService;

import org.springframework.boot.CommandLineRunner;

@Component
public class DataInitializer implements CommandLineRunner{

    private final TeamService teamService;

    public DataInitializer(TeamService teamService){
        this.teamService = teamService;
    }

    @Override
    public void run(String... args) throws Exception {
        if(!teamService.hasTeams()){
            teamService.createTeam("Ashburn Panthers");
        }
    }
}
