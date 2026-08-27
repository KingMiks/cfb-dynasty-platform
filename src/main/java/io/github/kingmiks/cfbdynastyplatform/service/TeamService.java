package io.github.kingmiks.cfbdynastyplatform.service;
import io.github.kingmiks.cfbdynastyplatform.model.Team;
import io.github.kingmiks.cfbdynastyplatform.repository.TeamRepository;

import org.springframework.stereotype.Service;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }
    public Team getTeam() {
        return teamRepository.findById(1L).orElseThrow();
    }
    public Team createTeam(String name){
        Team team = new Team(name);
        return teamRepository.save(team);
    }

    public boolean hasTeams(){
        return teamRepository.count() > 0;
    }

}
