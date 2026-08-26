package io.github.kingmiks.cfbdynastyplatform.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Team(String name){
        setName(name);
    }
    protected Team(){

    }

    public void setName(String name){
        if (name == null || name.isBlank()){
            throw new IllegalArgumentException("Name cannot be null or whitespace.");
        }
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    public Long getId(){
        return id;
    }
}
