package io.github.kingmiks.cfbdynastyplatform.service;
import io.github.kingmiks.cfbdynastyplatform.model.Team;
import io.github.kingmiks.cfbdynastyplatform.repository.TeamRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }
    public Team getTeam() {
        List<Team> teams = teamRepository.findAll();

        if (teams.isEmpty()){
            throw new IllegalStateException("No teams exists.");
        }
        return teams.get(0);
    }
    public Team createTeam(String name){
        Team team = new Team(name);
        return teamRepository.save(team);
    }

    public boolean hasTeams(){
        return teamRepository.count() > 0;
    }

}
