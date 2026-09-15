package io.github.kingmiks.cfbdynastyplatform.dto;

public class GameScoreUpdate {
    private Long gameId;
    private Integer ourScore;
    private Integer opponentScore;


    public void setGameId(Long gameId){
        this.gameId = gameId;
    }
    public void setOurScore(Integer ourScore){
        this.ourScore = ourScore;
    }
    public void setOpponentScore(Integer opponentScore){
        this.opponentScore = opponentScore;
    }
    public Long getGameId(){
        return gameId;
    }
    public Integer getOurScore(){
        return ourScore;
    }
    public Integer getOpponentScore(){
        return opponentScore;
    }
}
