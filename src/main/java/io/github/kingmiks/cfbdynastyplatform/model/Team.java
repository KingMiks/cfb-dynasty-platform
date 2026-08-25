package io.github.kingmiks.cfbdynastyplatform.model;

public class Team {

    private String name;

    public Team(String name){
        setName(name);
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
}
