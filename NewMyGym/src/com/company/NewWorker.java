package com.company;

public class NewWorker extends Worker{
    public NewWorker(String name, int workmonths, String password){
        super(name, workmonths, password);
    }
    //להעביר את הפונקציה לסניף?
    /*public VeteranWorker promoteWorker(){
        if()
        VeteranWorker newVeteran = new VeteranWorker(this);
        this = null;
        return newVeteran;
    }*/
}
