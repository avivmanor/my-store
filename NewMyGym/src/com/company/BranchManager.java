package com.company;

public class BranchManager extends Worker{

    public BranchManager(String name, int workmonths, String password){
        super(name, workmonths, password);
    }
    public void promoteWorker(Worker worker, Branch selectedBranch){
        if("NewWorker" != worker.getClass().toString()){
            //create exception
        }
        if(worker.workMonths>=6){
            VeteranWorker promoted = new VeteranWorker(worker);
            //add to the branchlist,delete the previous.
            selectedBranch.getEmployees().add(promoted);
            selectedBranch.getEmployees().remove(worker);
            worker = null;
        }
        else {
            //inform the user
        }
    }
}
