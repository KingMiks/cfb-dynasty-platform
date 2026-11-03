package io.github.kingmiks.cfbdynastyplatform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity 
public class Player {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String hometown;

    protected Player(){

    }

    public Player(String firstName, String lastName, String hometown){
        setPlayer(firstName, lastName, hometown);
    }

    public void setPlayer(String firstName, String lastName, String hometown){
        if((firstName == null || firstName.isBlank()) || (lastName == null || lastName.isBlank()) || (hometown == null || hometown.isBlank())){
            throw new IllegalArgumentException("First name, last name, and hometown must not be null or blank.");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.hometown = hometown;
    }

    public Long getID(){
        return id;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getHometown(){
        return hometown;
    }
}
