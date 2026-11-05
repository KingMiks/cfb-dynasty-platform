package io.github.kingmiks.cfbdynastyplatform.repository;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import io.github.kingmiks.cfbdynastyplatform.model.Player;

@SpringBootTest
@Transactional
class PlayerRepositoryIntegrationTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void shouldSaveAndRetrievePlayer() {
        Player player = new Player("Jimu", "Myung", "Ashburn, VA");
        playerRepository.save(player);
        Optional<Player> result = playerRepository.findById(player.getID());
        Player retrievedPlayer = result.orElseThrow();
        assertEquals("Jimu", retrievedPlayer.getFirstName());
        assertEquals("Myung", retrievedPlayer.getLastName());
    }
}