package io.github.kingmiks.cfbdynastyplatform.service;

import org.springframework.stereotype.Service;

import io.github.kingmiks.cfbdynastyplatform.repository.RosterSpotRepository;
import io.github.kingmiks.cfbdynastyplatform.model.RosterSpot;

@Service 
public class RosterService {
    
    private final RosterSpotRepository rosterSpotRepository;

    public RosterService(RosterSpotRepository rosterSpotRepository){
        this.rosterSpotRepository = rosterSpotRepository;

    }

    public RosterSpot getRosterSpot(Long id){
        if(id == null){
            throw new IllegalArgumentException("Id cannot be null.");
        }
        return rosterSpotRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Roster spot not found with id: " + id));
    }
}
