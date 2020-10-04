package com.company;

public class BranchManager extends Worker{

    public BranchManager(String name, int workmonths, String password){
        super(name, workmonths, password);
    }
    public String promoteWorker(Worker worker, Branch selectedBranch){
        if(!("class com.company.NewWorker".equals(worker.getClass().toString()))){
            //create exception
            return "employee has already been promoted";
        }
        if(worker.workMonths>=6){
            VeteranWorker promoted = new VeteranWorker(worker);
            //add to the branchlist,delete the previous.
            selectedBranch.getEmployees().add(promoted);
            selectedBranch.getEmployees().remove(worker);
            worker = null;
            return "done";
        }
        else {
            //inform the user
            return "the employee doesnt have the right seniority";
        }
    }
}
