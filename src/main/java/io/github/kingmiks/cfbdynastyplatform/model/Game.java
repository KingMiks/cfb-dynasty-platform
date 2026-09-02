package io.github.kingmiks.cfbdynastyplatform.model;

import jakarta.persistence.Entity;
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

    protected Game(){

    }

    public Game(int week, Season season){
        setGame(week, season);
    }
    public void setGame(int week, Season season){
        if (week <= 0 || season == null){
            throw new IllegalArgumentException("Season must not be null, week must be greater than 0.");
        }
        this.week = week;
        this.season = season;
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

}
