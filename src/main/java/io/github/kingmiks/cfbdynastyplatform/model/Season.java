package io.github.kingmiks.cfbdynastyplatform.model;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int year;
    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    protected Season(){

    }

    public Season(int year, Team team){
        setSeason(year, team);
    }

    public void setSeason(int year, Team team){
        if (year <= 0 || team == null){
            throw new IllegalArgumentException("Team must not be null, year must be greater than 0");
        }
        this.year = year;
        this.team = team;
    }

    public Long getId(){
        return id;
    }

    public int getYear(){
        return year;
    }
    public Team getTeam(){
        return team;
    }
}
