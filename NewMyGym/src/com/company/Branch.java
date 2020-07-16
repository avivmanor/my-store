package com.company;

import java.util.Iterator;
import java.util.LinkedList;

public class Branch {
    protected BranchManager manager;
    protected LinkedList<Worker>employees = new LinkedList<Worker>();

    public Branch(BranchManager manager){
        this.manager = manager;
    }

    public void printEmployees(){
        for(int i=0;i<employees.size();i++){
            System.out.println(employees.get(i).toString()+"\n");
        }

    }

    public BranchManager getManager() {
        return manager;
    }

    public void setManager(BranchManager manager) {
        this.manager = manager;
    }

    public LinkedList<Worker> getEmployees() {
        return employees;
    }

    public void setEmployees(LinkedList<Worker> employees) {
        this.employees = employees;
    }

    public Worker findEmployeeByName(String name){
        /*Iterator<Worker>it = employees.iterator();
        while(it.hasNext()){
            if(it.next().getName() == name) return it.next();
            System.out.println(it.toString());
        }*/
        for(int j=0; j<employees.size();j++){
            if(employees.get(j).getName().equals(name)){
                System.out.println("finally");
                return employees.get(j);
            }
        }
        return null;
    }

    public String checkPassword(String name, String pass){
        Worker worker = findEmployeeByName(name);
        System.out.println(worker);
        if(worker.equals(null)) return "worker doesnt exist";
        if(worker.getPassword().equals(pass)){
            System.out.println("found");
            return "found";
        }
        return "incorrect password";
    }
}
