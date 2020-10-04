package com.company;


import com.mongodb.*;
import com.mongodb.client.model.Projections;

import javax.management.Query;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import com.mongodb.client.*;
import com.mongodb.client.model.Projections;

import static com.mongodb.client.model.Projections.*;
import org.bson.Document;
import static javax.management.Query.eq;

public class Main {

    public static Branch selectedBranch ;
    public static Worker selectedWorker;
    public static Branch[] branches = new Branch[4];
    public static boolean loginSucceded = false;
    public static MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
    public static DB database = mongoClient.getDB("MyGym");
    public static DBCollection collection = database.getCollection("GymClients");

    public static void main(String[] args) throws IOException, ClassNotFoundException {
	// write your code here
        int funcNum;
        //1-holon,2-tel aviv,3-raanana,4-jerusalem
        BranchManager holonManager = new BranchManager("Avi Cohen", 22, "123456");
        branches[0] = new Branch(holonManager);
        branches[0].getEmployees().add(new NewWorker("Eli Neve",7,"234567"));
        branches[0].getEmployees().add(holonManager);
        //holonManager.promoteWorker(branches[0].findEmployeeByName("Eli Neve"),branches[0]);
        //branches[0].printEmployees();
        //default:
        //selectedBranch = branches[0];
        while(true){     //loginSucceded == false
            ServerSocket ss = new ServerSocket(4000);
            Socket connection = ss.accept();
            InputStream is = connection.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            funcNum = is.read();
            ss.close();
            connection.close();
            switch (funcNum){
                case 1:
                    loginFunction();
                    break;
                case 2:
                    signNewClient();
                    break;
                case 3:
                    endSub();
                    break;
                case 41:
                    promoteEmployee();
                    break;
                case 44:
                    addEmployeeToBranch();
                    break;
            }

        }
    }

    public static void loginFunction() throws IOException {
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
            selectedWorker = selectedBranch.findEmployeeByName(name);
            if(answer.equals("found")){
                dos.writeUTF(selectedWorker.getClass().toString());
            }
            ss.close();
            connection.close();
        }

    public static void promoteEmployee() throws IOException {
        ServerSocket ss = new ServerSocket(4100);
        Socket connection = ss.accept();
        InputStream is = connection.getInputStream();
        OutputStream os = connection.getOutputStream();
        DataInputStream dis = new DataInputStream(is);
        DataOutputStream dos = new DataOutputStream(os);
        String name = dis.readUTF();
        Worker workerToPromote = selectedBranch.findEmployeeByName(name);
        System.out.println(workerToPromote.getClass().toString());
        String answer = ((BranchManager)selectedWorker).promoteWorker(workerToPromote,selectedBranch);
        dos.writeUTF(answer);
        System.out.println(selectedBranch.findEmployeeByName(name).getClass().toString());
        ss.close(); //close also the ss
    }

    public static void addEmployeeToBranch() throws IOException {
        ServerSocket ss = new ServerSocket(4400);
        Socket connection = ss.accept();
        InputStream is = connection.getInputStream();
        OutputStream os = connection.getOutputStream();
        DataInputStream dis = new DataInputStream(is);
        DataOutputStream dos = new DataOutputStream(os);
        String name = dis.readUTF();
        String password = dis.readUTF();
        selectedBranch.addEmployee(name,password);
        if(selectedBranch.findEmployeeByName(name).getClass().toString().equals("class com.company.NewWorker"))
            dos.writeUTF("succeeded");
        else dos.writeUTF("failed");
        System.out.println(selectedBranch.findEmployeeByName(name).getClass().toString());
        ss.close(); //close also the ss
    }

    public static void endSub() throws IOException {
        ServerSocket ss = new ServerSocket(3000);
        Socket connection = ss.accept();
        InputStream is = connection.getInputStream();
        OutputStream os = connection.getOutputStream();
        DataInputStream dis = new DataInputStream(is);
        DataOutputStream dos = new DataOutputStream(os);
        String name = dis.readUTF();
        String phone = dis.readUTF();
        DBObject query = new BasicDBObject("name",name).append("phone",phone);
        collection.remove(query);
        Cursor cursor = collection.find(query);
        if(collection.find(query).one() == null){
            dos.writeUTF("succeeded");
        }
        else dos.writeUTF("failed");
        //System.out.println(selectedBranch.findEmployeeByName(name).getClass().toString());
        ss.close(); //close also the ss
    }

    public static void signNewClient()throws IOException{
        ServerSocket ss = new ServerSocket(2000);
        Socket connection = ss.accept();
        InputStream is = connection.getInputStream();
        OutputStream os = connection.getOutputStream();
        DataInputStream dis = new DataInputStream(is);
        DataOutputStream dos = new DataOutputStream(os);
        String name = dis.readUTF();
        String type = dis.readUTF();
        String phone = dis.readUTF();
        collection.insert(toDBObject(name,type,phone));
        //collection.insert(doc);
        DBObject query = new BasicDBObject("name", name).append("phone",phone);
        //DBCursor cursor = collection.find(query);

        //collection.find({name: "D"});
        //collection.find({name,"a"});
        if(collection.find(query).one() != null){
            dos.writeUTF("succeeded");
        }
        else dos.writeUTF("failed");
        System.out.println(selectedBranch.findEmployeeByName(name).getClass().toString());
        ss.close(); //close also the ss
    }

    public static DBObject toDBObject (String name, String type, String phone){
        return new BasicDBObject("name", name)
                .append("clientType", type)
                .append("phone",phone)
                .append("isIn",false);
    }
    //public static DBObject toDBObject (String name, String type, String phone) {

    //}

}

