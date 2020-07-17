package com.company;

import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
	// write your code here
        Branch[] branches = new Branch[4];
        //1-holon,2-tel aviv,3-raanana,4-jerusalem
        BranchManager holonManager = new BranchManager("Avi Cohen", 22, "123456");
        branches[0] = new Branch(holonManager);
        branches[0].getEmployees().add(new NewWorker("Eli Neve",7,"234567"));
        branches[0].getEmployees().add(holonManager);
        //holonManager.promoteWorker(branches[0].findEmployeeByName("Eli Neve"),branches[0]);
        //branches[0].printEmployees();
        //default:
        Branch selectedBranch = branches[0];
        Worker selecetedworker;

        ServerSocket ss = new ServerSocket(5000);
        Socket connection = ss.accept();
        InputStream is = connection.getInputStream();
        OutputStream os = connection.getOutputStream();
        DataInputStream dis = new DataInputStream(is);
        DataOutputStream dos = new DataOutputStream(os);
        //JSONObject details =(JSONObject) dis.readObject();
        String branch = dis.readUTF();
        String name = dis.readUTF();
        String pass = dis.readUTF();
        switch (branch) {
            case "Holon":
                selectedBranch = branches[0];
                break;
            case "Tel Aviv":
                selectedBranch = branches[1];
                break;
            case "Raanana":
                selectedBranch = branches[2];
                break;
            case "Jerusalem":
                selectedBranch = branches[3];
                break;
        }
        String answer = selectedBranch.checkPassword(name, pass);
        dos.writeUTF(answer);
        selecetedworker = selectedBranch.findEmployeeByName(name);
        if(answer.equals("found")){
            dos.writeUTF(selecetedworker.getClass().toString());
        }
        }

        public static void loginFunction(){
        }

    }

