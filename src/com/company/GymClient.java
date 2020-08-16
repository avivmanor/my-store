package com.company;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

enum clientType {VIP , REGULAR , ONETIME}

public class GymClient {
    private String name;
    private clientType type;
    private String phone;
    boolean isIn=false;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public GymClient(String name, clientType type, String phone){
        this.name = name;
        this.type = type;
        this.phone = phone;
        this.isIn = false;
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

    public DBObject toDBObject (){
        return new BasicDBObject("name", this.getName())
                .append("clientType", this.getType())
                .append("phone",this.getPhone())
                .append("isIn",false);
    }
}
