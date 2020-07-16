package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

public class Gui {
    public static String selectedEmployeeType = null;
    public static String selectedEmployeeName = null;
    public static boolean loginSuccess = false;

    public static void main(String args[]){

        while (loginSuccess == false) {
            loginPanel();
            if (loginSuccess == true) {
                System.out.println("in login success =true");
                actionsPanel(selectedEmployeeType, selectedEmployeeName);
            }
        }
        System.out.println("after loginPanel");
        //}
        //while (loginSuccess == false)
            if(loginSuccess == true){
                System.out.println("in login success =true");
                actionsPanel(selectedEmployeeType, selectedEmployeeName);
            }

    }

    public static void loginPanel(){
        String[] branchList = {"Holon", "Tel-Aviv", "Raanana", "Jerusalem"};
        JFrame frame = new JFrame("My First GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        JLabel mainLabel = new JLabel("Welcome to myGym system!");
        mainLabel.setFont(new Font("Serif", Font.BOLD, 20));
        mainLabel.setBounds(130, 10, 300, 50);
        frame.add(mainLabel);

        JLabel branchLabel = new JLabel("pick a branch");
        JComboBox branchBox = new JComboBox(branchList);
        branchLabel.setBounds(50, 80, 100, 50);
        branchBox.setBounds(200, 80, 100, 50);
        frame.add(branchLabel);
        frame.add(branchBox);

        JLabel nameLabel = new JLabel("Employee name");
        JTextField nameTf = new JTextField(20);
        nameLabel.setBounds(50, 180 ,100, 40);
        nameTf.setBounds(200, 180, 100, 40);
        frame.add(nameLabel);
        frame.getContentPane().add(nameTf);

        JLabel passLabel = new JLabel("Enter password");
        JTextField passTf = new JTextField(20);
        passLabel.setBounds(50, 270 ,100, 40);
        passTf.setBounds(200, 270, 100, 40);
        frame.add(passLabel);
        frame.getContentPane().add(passTf);

        JButton login = new JButton("login");
        frame.getContentPane().add(login);
        login.setBounds(200, 360, 100, 50);
        frame.setLayout(null);
        frame.setVisible(true);

        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selecetedBranch = branchBox.getSelectedItem().toString();
                String employeeName = nameTf.getText();
                String employeePass = passTf.getText();
                String isSuccess = null;
                try {
                    isSuccess = loginTrial(selecetedBranch, employeeName, employeePass);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, isSuccess);
                System.out.println(" before false");
                frame.setVisible(false);
                System.out.println(" after false");

            }
        });
        return;
    }

    public static String loginTrial(String branch, String name, String pass) throws UnknownHostException, IOException {
        Socket connection = new Socket("localhost", 5000);
        OutputStream os = connection.getOutputStream();
        DataOutputStream  dos = new DataOutputStream(os);
        InputStream is = connection.getInputStream();
        DataInputStream dis = new DataInputStream(is);
        dos.writeUTF(branch);
        dos.writeUTF(name);
        dos.writeUTF(pass);
        String answer = dis.readUTF();
        selectedEmployeeType = dis.readUTF();//add it to the if, akso in the main
        if (answer.equals("found")){
            loginSuccess = true;
            selectedEmployeeName = name;
        }
        return answer;
    }

    public static void actionsPanel(String selectedEmployeeType, String selectedEmployeeName){
        System.out.println(" in actions panel");
        JFrame actionsFrame = new JFrame("Welcome "+selectedEmployeeName+" !");
        JButton signNew,endSub, managerActions, logoff,button4;
        signNew = new JButton("sign new client");
        endSub = new JButton("end subscription");
        managerActions = new JButton("manager actions");
        logoff = new JButton("log off ");
        button4 = new JButton("button 5");
        actionsFrame.add(signNew);
        actionsFrame.add(endSub);
        actionsFrame.add(managerActions);
        actionsFrame.add(logoff);
        actionsFrame.add(button4);
        actionsFrame.setLayout(new GridLayout(2,3));
        actionsFrame.setSize(600,600);
        System.out.println(" before true");
        actionsFrame.setVisible(true);
        System.out.println("after true");
        managerActions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedEmployeeType.equals("manager")) {
                    actionsFrame.setVisible(false);
                    managerActionsPanel(selectedEmployeeType,selectedEmployeeName);
                }
                else{
                    JOptionPane.showMessageDialog(null,"you're not a manager!");
                }
            }
        });
    }

    public void promoteEmployeePanel(String selectedEmployeeType, String selectedEmployeeName){
        if(selectedEmployeeType.equals("BranchManager")){
            JFrame frame = new JFrame("My First GUI");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500,500);
            JLabel employeeNameLabel = new JLabel("Enter employee name");
            JTextField nameTf = new JTextField(20);
            employeeNameLabel.setBounds(50, 270 ,100, 40);
            nameTf.setBounds(200, 270, 100, 40);
            frame.add(employeeNameLabel);
            frame.getContentPane().add(nameTf);
        }
        else{
            JOptionPane.showMessageDialog(null,"only a manager can promote employee!");
        }

    }

    public static void managerActionsPanel(String selectedEmployeeType, String selectedEmployeeName){
        JFrame managerActionsFrame = new JFrame("Welcome to manager actions window!");
        JButton promoteEmployee, attendanceReport, DataReport, goBack,button4;
        promoteEmployee = new JButton("promote Employee");
        attendanceReport = new JButton("see who's in the gym");
        DataReport = new JButton("get data report");
        goBack = new JButton("go back");
        button4 = new JButton("button 5");
        managerActionsFrame.add(promoteEmployee);
        managerActionsFrame.add(attendanceReport);
        managerActionsFrame.add(DataReport);
        managerActionsFrame.add(goBack);
        managerActionsFrame.add(button4);
        managerActionsFrame.setLayout(new GridLayout(2,3));
        managerActionsFrame.setSize(600,600);
        managerActionsFrame.setVisible(true);
    }

}
