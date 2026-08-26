package io.github.kingmiks.cfbdynastyplatform.service;
import io.github.kingmiks.cfbdynastyplatform.model.Team;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
    public Team getTeam() {
        return new Team("Ashburn Panthers");
    }
}
