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
    private int ourScore;
    private int opponentScore;

    protected Game() {

    }

    public Game(int week, Season season, String opponent, GameLocation location, int ourScore, int opponentScore) {
        setGame(week, season, opponent, location, ourScore, opponentScore);
    }

    public void setGame(int week, Season season, String opponent,
            GameLocation location, int ourScore, int opponentScore) {
        if (week <= 0 || season == null
                || opponent == null || opponent.isBlank()
                || location == null || ourScore < 0
                || opponentScore < 0) {
            throw new IllegalArgumentException(
                    "Season, opponent, and location must not be null. " +
                            "Week must be greater than 0, opponent must not be blank, and scores must not be negative.");
        }
        this.week = week;
        this.season = season;
        this.opponent = opponent;
        this.location = location;
        this.ourScore = ourScore;
        this.opponentScore = opponentScore;

    }

    public Long getID() {
        return id;
    }

    public int getWeek() {
        return week;
    }

    public Season getSeason() {
        return season;
    }

    public String getOpponent() {
        return opponent;
    }

    public GameLocation getLocation() {
        return location;
    }

    public int getOurScore() {
        return ourScore;
    }

    public int getOpponentScore() {
        return opponentScore;
    }
    public GameResult getResult(){
        if (ourScore == opponentScore){
            throw new IllegalStateException("Game cannot end in a tie.");
        }
        if (ourScore > opponentScore){
            return GameResult.WIN;
        }
        return GameResult.LOSS;
    }

}
