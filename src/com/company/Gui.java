package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import static java.lang.Thread.sleep;

public class Gui {
    public static String selectedEmployeeType = null;
    public static String selectedEmployeeName = null;
    public static boolean loginSuccess = false;
    public static JComboBox branchBox;
    public static JTextField nameTf;
    public static JTextField passTf;
    public static JButton login;
    public static JFrame signFrame;
    public static String loginAnswer = "not found";

    public static void main(String args[]) throws IOException {

        /*Socket connection = new Socket("localhost", 5000);
        OutputStream os = connection.getOutputStream();
        DataOutputStream  dos = new DataOutputStream(os);
        os.write(1);*/
        while(!(loginAnswer.equals("found"))) {
            try {
                loginProcess();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(loginAnswer.equals("found")) {
            System.out.println("after, in found");
            JOptionPane.showMessageDialog(null, "login Succeded");
            signFrame.setVisible(false);
            actionsPanel();
        }
   }

   public static void loginProcess() throws InterruptedException, IOException {
        System.out.println("entered the function");
       Socket connection = new Socket("localhost", 4000);
       OutputStream os = connection.getOutputStream();
       os.write(1);
       connection.close();
       loginPanel();
       loginPressed();
       while(!(loginAnswer.equals("found"))){
           sleep(1000);
           if(loginAnswer.equals("worker doesnt exist")) {
               JOptionPane.showMessageDialog(null, "there is no such employee, try again.");
               loginAnswer = "not found";
               return;
           }
           if (loginAnswer.equals("incorrect password")){
               JOptionPane.showMessageDialog(null, "incorrect password");
               loginAnswer = "not found";
               return;
           }
       }
   }

    public static void loginPanel() {
        System.out.println(" in login panel");
        String[] branchList = {"Holon", "Tel-Aviv", "Raanana", "Jerusalem"};
        signFrame = new JFrame("My First GUI");
        signFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        signFrame.setSize(500, 500);
        JLabel mainLabel = new JLabel("Welcome to myGym system!");
        mainLabel.setFont(new Font("Serif", Font.BOLD, 20));
        mainLabel.setBounds(130, 10, 300, 50);
        signFrame.add(mainLabel);

        JLabel branchLabel = new JLabel("pick a branch");
        branchBox = new JComboBox(branchList);
        branchLabel.setBounds(50, 80, 100, 50);
        branchBox.setBounds(200, 80, 100, 50);
        signFrame.add(branchLabel);
        signFrame.add(branchBox);

        JLabel nameLabel = new JLabel("Employee name");
        nameTf = new JTextField(20);
        nameLabel.setBounds(50, 180, 100, 40);
        nameTf.setBounds(200, 180, 100, 40);
        signFrame.add(nameLabel);
        signFrame.getContentPane().add(nameTf);

        JLabel passLabel = new JLabel("Enter password");
        passTf = new JTextField(20);
        passLabel.setBounds(50, 270, 100, 40);
        passTf.setBounds(200, 270, 100, 40);
        signFrame.add(passLabel);
        signFrame.getContentPane().add(passTf);

        login = new JButton("login");
        signFrame.getContentPane().add(login);
        login.setBounds(200, 360, 100, 50);
        signFrame.setLayout(null);
        signFrame.setVisible(true);
        System.out.println(" finish login panel");
    }

public static void loginPressed(){
        System.out.println("in login pressed");
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selecetedBranch = branchBox.getSelectedItem().toString();
                String employeeName = nameTf.getText();
                String employeePass = passTf.getText();
                try {
                    loginAnswer = loginTrial(selecetedBranch, employeeName, employeePass);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        System.out.println("finish login press");
        return;
    }

    public static String loginTrial(String branch, String name, String pass) throws UnknownHostException, IOException {
        System.out.println("in loginTrial");
        Socket connection = new Socket("localhost", 5000);
        OutputStream os = connection.getOutputStream();
        DataOutputStream  dos = new DataOutputStream(os);
        InputStream is = connection.getInputStream();
        DataInputStream dis = new DataInputStream(is);
        dos.writeUTF(branch);
        dos.writeUTF(name);
        dos.writeUTF(pass);
        String answer = dis.readUTF();
        if (answer.equals("found")){
            selectedEmployeeType = dis.readUTF();//add it to the if, akso in the main
            System.out.println("this is the type:"+selectedEmployeeType);
            loginSuccess = true;
            selectedEmployeeName = name;
        }
        System.out.println("finish login trial");
        connection.close();
        return answer;
    }

    public static void actionsPanel(){
        System.out.println(" in actions panel");
        JFrame actionsFrame = new JFrame("Welcome "+selectedEmployeeName+" !");
        JButton signNew,endSub, managerActions, logoff,button4;
        signNew = new JButton("sign new client");//2
        endSub = new JButton("end subscription");//3
        managerActions = new JButton("manager actions");//4
        logoff = new JButton("log off ");//5
        button4 = new JButton("button 5");
        actionsFrame.add(signNew);
        actionsFrame.add(endSub);
        actionsFrame.add(managerActions);
        actionsFrame.add(logoff);
        actionsFrame.add(button4);
        actionsFrame.setLayout(new GridLayout(2,3));
        actionsFrame.setSize(600,600);
        actionsFrame.setVisible(true);
        managerActions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(selectedEmployeeType.toString());
                if(selectedEmployeeType.equals("class com.company.BranchManager")) {
                    actionsFrame.setVisible(false);
                    managerActionsPanel(selectedEmployeeType,selectedEmployeeName);
                }
                else{
                    JOptionPane.showMessageDialog(null,"you're not a manager!");
                }
            }
        });
        signNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionsFrame.setVisible(false);
                signNewClientPanel();
            }
        });
        System.out.println("finish login panel");
        return;
    }

    public static boolean promoteEmployeePanel(){
        final String[] promoteAnswer = new String[1];
        JFrame promoteFrame = new JFrame("promote employee");
        promoteFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        promoteFrame.setSize(500,500);
        JLabel employeeNameLabel = new JLabel("Enter employee name");
        JTextField employeeNameTf = new JTextField(20);
        employeeNameLabel.setBounds(50, 270 ,100, 40);
        employeeNameTf.setBounds(200, 270, 100, 40);
        JButton promoteButton = new JButton("promote");
        promoteButton.setBounds(200, 360, 100, 50);
        promoteFrame.getContentPane().add(employeeNameLabel);
        promoteFrame.getContentPane().add(employeeNameTf);
        promoteFrame.getContentPane().add(promoteButton);
        promoteFrame.setLayout(null);
        promoteFrame.setVisible(true);
            promoteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Socket connection = null;
                    Socket connection2 = null;
                    try {
                        connection = new Socket("localhost", 4000);
                        OutputStream os = connection.getOutputStream();
                        DataOutputStream  dos = new DataOutputStream(os);
                        os.write(41);
                        connection.close();
                        connection2 = new Socket("localhost", 4100);
                        OutputStream os2 = connection2.getOutputStream();
                        DataOutputStream  dos2 = new DataOutputStream(os2);
                        InputStream is2 = connection2.getInputStream();
                        DataInputStream dis2 = new DataInputStream(is2);
                        dos2.writeUTF(employeeNameTf.getText());
                        promoteAnswer[0] = dis2.readUTF();
                        JOptionPane.showMessageDialog(null,promoteAnswer[0] );
                        connection.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    if(promoteAnswer[0]  == "done"){
                        promoteFrame.setVisible(false);
                    }
                }
            });
        if(promoteAnswer[0]  == "done") return true;
        return false;
        }


    public static void managerActionsPanel(String selectedEmployeeType, String selectedEmployeeName){
        JFrame managerActionsFrame = new JFrame("Welcome to manager actions window!");
        JButton promoteEmployee, attendanceReport, DataReport, goBack,addEmployee,fireEmployee;
        promoteEmployee = new JButton("promote Employee");//41
        attendanceReport = new JButton("see who's in the gym");//42
        DataReport = new JButton("get data report");//43
        addEmployee = new JButton("add new employee to staff");//44
        fireEmployee = new JButton("fire employee");//45
        goBack = new JButton("go back");//46
        managerActionsFrame.add(promoteEmployee);
        managerActionsFrame.add(attendanceReport);
        managerActionsFrame.add(DataReport);
        managerActionsFrame.add(addEmployee);
        managerActionsFrame.add(fireEmployee);
        managerActionsFrame.add(goBack);
        managerActionsFrame.setLayout(new GridLayout(2,3));
        managerActionsFrame.setSize(600,600);
        managerActionsFrame.setVisible(true);
        promoteEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerActionsFrame.setVisible(false);
                int num=0;
                promoteEmployeePanel();
                System.out.println("in this point");
            }
        });
        //managerActionsFrame.setVisible(true);
        addEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerActionsFrame.setVisible(false);
                addEmployeeToStaff();
            }
        });
    }

    public static void addEmployeeToStaff() {
        final String[] promoteAnswer = new String[1];
        JFrame aadFrame = new JFrame("Add Employee to Staff");
        aadFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        aadFrame.setSize(500, 500);
        JLabel mainLabel = new JLabel("enter name and choose password!");
        mainLabel.setFont(new Font("Serif", Font.BOLD, 20));
        mainLabel.setBounds(130, 10, 300, 50);
        aadFrame.add(mainLabel);

        JLabel nameLabel = new JLabel("New employee name");
        JTextField nameTf = new JTextField(20);
        nameLabel.setBounds(50, 180, 100, 40);
        nameTf.setBounds(200, 180, 100, 40);
        aadFrame.add(nameLabel);
        aadFrame.getContentPane().add(nameTf);

        JLabel passLabel = new JLabel("Enter password");
        JTextField passTf = new JTextField(20);
        passLabel.setBounds(50, 270, 100, 40);
        passTf.setBounds(200, 270, 100, 40);
        aadFrame.add(passLabel);
        aadFrame.getContentPane().add(passTf);

        JButton add = new JButton("add new Employee");
        aadFrame.getContentPane().add(add);
        add.setBounds(200, 360, 100, 50);
        aadFrame.setLayout(null);
        aadFrame.setVisible(true);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Socket connection = null;
                Socket connection2 = null;
                try {
                    connection = new Socket("localhost", 4000);
                    OutputStream os = connection.getOutputStream();
                    DataOutputStream  dos = new DataOutputStream(os);
                    os.write(44);
                    connection.close();
                    connection2 = new Socket("localhost", 4400);
                    OutputStream os2 = connection2.getOutputStream();
                    DataOutputStream  dos2 = new DataOutputStream(os2);
                    InputStream is2 = connection2.getInputStream();
                    DataInputStream dis2 = new DataInputStream(is2);
                    dos2.writeUTF(nameTf.getText());
                    dos2.writeUTF(passTf.getText());
                    promoteAnswer[0] = dis2.readUTF();
                    JOptionPane.showMessageDialog(null,promoteAnswer[0] );
                    connection.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }

    public static void signNewClientPanel (){
        final String[] promoteAnswer = new String[1];
        String[] typeList = {"VIP","REGULAR","ONETIME"};
        JFrame signFrame = new JFrame("Sign New Client");
        signFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        signFrame.setSize(500, 500);
        JLabel mainLabel = new JLabel("Enter the new client's details!");
        mainLabel.setFont(new Font("Serif", Font.BOLD, 20));
        mainLabel.setBounds(130, 10, 300, 50);
        signFrame.add(mainLabel);

        JLabel nameLabel = new JLabel("Client name");
        JTextField nameTf = new JTextField(20);
        nameLabel.setBounds(50, 180, 100, 40);
        nameTf.setBounds(250, 180, 100, 40);
        signFrame.add(nameLabel);
        signFrame.getContentPane().add(nameTf);

        JLabel typeLabel = new JLabel("Enter subscription type");
        JComboBox typeBox = new JComboBox(typeList);
        typeLabel.setBounds(50, 80, 180, 50);
        typeBox.setBounds(250, 80, 100, 50);
        signFrame.add(typeLabel);
        signFrame.getContentPane().add(typeBox);

        JLabel phoneLabel = new JLabel("Enter phone number");
        JTextField phoneTf = new JTextField(20);
        phoneLabel.setBounds(50, 270, 150, 40);
        phoneTf.setBounds(250, 270, 100, 40);
        signFrame.add(phoneLabel);
        signFrame.getContentPane().add(phoneTf);

        JButton sign = new JButton("sign up");
        sign.setBounds(200, 360, 100, 50);
        signFrame.getContentPane().add(sign);
        signFrame.setLayout(null);
        signFrame.setVisible(true);

        sign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Socket connection = null;
                Socket connection2 = null;
                try {
                    connection = new Socket("localhost", 4000);
                    OutputStream os = connection.getOutputStream();
                    DataOutputStream  dos = new DataOutputStream(os);
                    os.write(2);
                    connection.close();
                    connection2 = new Socket("localhost", 2000);
                    OutputStream os2 = connection2.getOutputStream();
                    DataOutputStream  dos2 = new DataOutputStream(os2);
                    InputStream is2 = connection2.getInputStream();
                    DataInputStream dis2 = new DataInputStream(is2);
                    dos2.writeUTF(nameTf.getText());
                    dos2.writeUTF(typeBox.getSelectedItem().toString());
                    dos2.writeUTF(phoneTf.getText());
                    promoteAnswer[0] = dis2.readUTF();
                    JOptionPane.showMessageDialog(null,promoteAnswer[0] );
                    connection.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });


    }
}

