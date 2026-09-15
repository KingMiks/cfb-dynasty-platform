package io.github.kingmiks.cfbdynastyplatform.dto;

import java.util.List;
import java.util.ArrayList;

public class ScheduleUpdateForm {
    private List<GameScoreUpdate> gameScoreUpdates = new ArrayList<>();

    public void setGameScoreUpdates(List<GameScoreUpdate> gameScoreUpdates){
        this.gameScoreUpdates = gameScoreUpdates;
    }
    public List<GameScoreUpdate> getGameScoreUpdates(){
        return gameScoreUpdates;
    }
}
