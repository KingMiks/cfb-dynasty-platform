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
}
