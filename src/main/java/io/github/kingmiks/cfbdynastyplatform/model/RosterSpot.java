package io.github.kingmiks.cfbdynastyplatform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;

@Entity
public class RosterSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;
    @ManyToOne
    @JoinColumn(name = "season_id", nullable = false)
    private Season season;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Position position;
    private int jerseyNumber;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClassYear classYear;
    private boolean redshirt;

    protected RosterSpot() {

    }

    public RosterSpot(Player player, Season season, Position position, int jerseyNumber, ClassYear classYear,
            boolean redshirt) {
        if (player == null || season == null || position == null
                || jerseyNumber < 0 || jerseyNumber > 99 || classYear == null) {
            throw new IllegalArgumentException(
                    "Player, position, class year and season must not be null. Jersey number must be between 0 and 99.");
        }
        this.player = player;
        this.season = season;
        this.position = position;
        this.jerseyNumber = jerseyNumber;
        this.classYear = classYear;
        this.redshirt = redshirt;
    }

    public Long getID() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public Season getSeason() {
        return season;
    }

    public Position getPosition() {
        return position;
    }

    public int getJerseyNumber() {
        return jerseyNumber;
    }

    public ClassYear getClassYear() {
        return classYear;
    }

    public boolean isRedshirt() {
        return redshirt;
    }

}
