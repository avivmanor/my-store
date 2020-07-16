package com.company;

public class VeteranWorker extends Worker {
    private int bonusCount;

    public VeteranWorker(String name, int workmonths, String password){
        super(name, workmonths, password);
        this.bonusCount = workmonths/6;
    }
    public VeteranWorker(Worker worker){
        super(worker);
        this.bonusCount = worker.workMonths/6;
    }
    public GymClient signNewClient(String name, clientType type){
        return new GymClient(name,type);
        //add to the branch clients list-to mongodb
    }
}
