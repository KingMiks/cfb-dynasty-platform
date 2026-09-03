package io.github.kingmiks.cfbdynastyplatform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int week;
    @ManyToOne
    @JoinColumn(name = "season_id", nullable = false)
    private Season season;
    private String opponent;
    @Enumerated(EnumType.STRING)
    private GameLocation location;

    protected Game(){

    }

    public Game(int week, Season season, String opponent, GameLocation location){
        setGame(week, season, opponent, location);
    }
    public void setGame(int week, Season season, String opponent, GameLocation location){
        if (week <= 0 || season == null
            || opponent == null || opponent.isBlank()
            || location == null
        ){
            throw new IllegalArgumentException("Season, opponent, and location must not be null, week must be greater than 0, opponent must not be blank.");
        }
        this.week = week;
        this.season = season;
        this.opponent = opponent;
        this.location = location;
        
    }
    public Long getID(){
        return id;
    }
    public int getWeek(){
        return week;
    }
    public Season getSeason(){
        return season;
    }
    public String getOpponent(){
        return opponent;
    }
    public GameLocation getLocation(){
        return location;
    }

}
