package com.company;

enum clientType {VIP , REGULAR , ONETIME}

public class GymClient {
    private String name;
    private clientType type;
    boolean isIn=false;

    public GymClient(String name, clientType type){
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public clientType getType() {
        return this.type;
    }

    public void setType(clientType type) {
        this.type = type;
    }

    public boolean isIn() {
        return this.isIn;
    }

    public void setIn() {
        this.isIn = true;
    }
    public void setOut(){
        this.isIn = false;
    }
}
