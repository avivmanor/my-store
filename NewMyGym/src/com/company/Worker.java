package com.company;

public abstract class Worker {
    protected String name;
    protected int workMonths;
    protected String password;
    //להוסיף תאריך התחלת עבודה כך שיתעדכן לבד?

    public Worker(String name, int workmonths, String password){
        this.name = name;
        this.workMonths = workmonths;
        this.password = password;
    }
    public Worker(Worker worker){
        this.name = worker.name;
        this.workMonths = worker.workMonths;
        this.password = worker.password;
    }

    @Override
    public String toString() {
        return "Worker{" + this.getClass()+
                ", name='" + name + '\'' +
                ", workMonths=" + workMonths +
                ", password='" + password + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWorkMonths() {
        return workMonths;
    }

    public void setWorkMonths(int workMonths) {
        this.workMonths = workMonths;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
