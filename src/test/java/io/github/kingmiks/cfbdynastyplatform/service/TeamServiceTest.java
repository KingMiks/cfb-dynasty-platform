package io.github.kingmiks.cfbdynastyplatform.service;

import io.github.kingmiks.cfbdynastyplatform.model.Team;
import io.github.kingmiks.cfbdynastyplatform.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TeamServiceTest {

    @Test
    void getTeamReturnsExistingTeam() {

        TeamRepository teamRepository = Mockito.mock(TeamRepository.class);

        TeamService teamService = new TeamService(teamRepository);

        Team team = new Team("Ashburn Panthers");

        when(teamRepository.findAll()).thenReturn(List.of(team));

        Team result = teamService.getTeam();

        assertEquals("Ashburn Panthers", result.getName());
    }

    @Test
    void getTeamThrowsWhenNoTeamExists() {

        TeamRepository teamRepository = Mockito.mock(TeamRepository.class);

        TeamService teamService = new TeamService(teamRepository);

        when(teamRepository.findAll()).thenReturn(List.of());

        assertThrows(
                IllegalStateException.class,
                () -> teamService.getTeam());

    }
    @Test
    void getTeamThrowsWhenMultipleTeamsExist(){
        TeamRepository teamRepository = Mockito.mock(TeamRepository.class);

        TeamService teamService = new TeamService(teamRepository);

        Team team = new Team("Ashburn Panthers");
        Team team2 = new Team("Virginia Tech");

        when(teamRepository.findAll()).thenReturn(List.of(team, team2));

        assertThrows(
                IllegalStateException.class,
                () -> teamService.getTeam());
    }
}