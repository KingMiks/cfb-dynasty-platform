package io.github.kingmiks.cfbdynastyplatform.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import io.github.kingmiks.cfbdynastyplatform.service.GameService;
import io.github.kingmiks.cfbdynastyplatform.service.SeasonService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.ArgumentCaptor;
import java.util.List;
import io.github.kingmiks.cfbdynastyplatform.dto.GameScoreUpdate;
import static org.mockito.Mockito.doThrow;
import static org.mockito.ArgumentMatchers.anyList;

@WebMvcTest(ScheduleController.class)
public class ScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GameService gameService;

    @MockitoBean
    private SeasonService seasonService;

    @Test
    public void recordResultUpdatesGameAndRedirects() throws Exception {
        mockMvc.perform(post("/schedule/result")
                .param("gameId", "4")
                .param("ourScore", "31")
                .param("opponentScore", "27"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/schedule"));
        verify(gameService).recordGameResult(4L, 31, 27);
    }

    @Test
    public void recordResultShowsErrorWhenTied() throws Exception {
        when(gameService.recordGameResult(4L, 31, 31))
                .thenThrow(new IllegalStateException("Game cannot end in a tie."));
        mockMvc.perform(post("/schedule/result")
                .param("gameId", "4")
                .param("ourScore", "31")
                .param("opponentScore", "31"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/schedule"))
                .andExpect(flash().attribute("errorMessage", "Game cannot end in a tie."));
        verify(gameService).recordGameResult(4L, 31, 31);

    }

    @Test
    public void recordResultShowsErrorWhenTiedAfterUpdate() throws Exception {
        doThrow(new IllegalStateException("Game cannot end in a tie."))
                .when(gameService)
                .updateGameScores(anyList());
        mockMvc.perform(post("/schedule/results")
                .param("gameScoreUpdates[0].gameId", "4")
                .param("gameScoreUpdates[0].ourScore", "31")
                .param("gameScoreUpdates[0].opponentScore", "31"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/schedule"))
                .andExpect(flash().attribute("errorMessage", "Game cannot end in a tie."));

    }

    @Test
    public void recordResultShowsErrorWhenNegativeScoreisAdded() throws Exception {
        doThrow(new IllegalArgumentException("Scores must not be negative."))
                .when(gameService)
                .updateGameScores(anyList());
        mockMvc.perform(post("/schedule/results")
                .param("gameScoreUpdates[0].gameId", "4")
                .param("gameScoreUpdates[0].ourScore", "-31")
                .param("gameScoreUpdates[0].opponentScore", "31"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/schedule"))
                .andExpect(flash().attribute("errorMessage", "Scores must not be negative."));

    }

    @Test
    public void recordResultsBindsMultipleGameScoreUpdates() throws Exception {

        mockMvc.perform(post("/schedule/results")
                .param("gameScoreUpdates[0].gameId", "1")
                .param("gameScoreUpdates[0].ourScore", "24")
                .param("gameScoreUpdates[0].opponentScore", "10")
                .param("gameScoreUpdates[1].gameId", "2")
                .param("gameScoreUpdates[1].ourScore", "14")
                .param("gameScoreUpdates[1].opponentScore", "7"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/schedule"));
        ArgumentCaptor<List<GameScoreUpdate>> captor = ArgumentCaptor.forClass(List.class);

        verify(gameService).updateGameScores(captor.capture());

        List<GameScoreUpdate> updates = captor.getValue();

        assertEquals(2, updates.size());
        assertEquals(1L, updates.get(0).getGameId());
        assertEquals(24, updates.get(0).getOurScore());
        assertEquals(10, updates.get(0).getOpponentScore());

        assertEquals(2L, updates.get(1).getGameId());
        assertEquals(14, updates.get(1).getOurScore());
        assertEquals(7, updates.get(1).getOpponentScore());
    }
}
