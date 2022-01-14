package FamilyTree;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.xml.transform.Result;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;

public class GUI extends JFrame implements ActionListener {
    private JLabel label;
    private JButton button;
    private JPanel panel;
    private JFrame frame;
    private Container c;
    private JLabel title;
    private JLabel name;
    private JTextField tname;
    private JLabel mno;
    private JTextField tmno;
    private JLabel gender;
    private JRadioButton male;
    private JRadioButton female;
    private ButtonGroup gengp;
    private JLabel dob;
    private JComboBox date;
    private JComboBox month;
    private JComboBox year;
    private JComboBox genderc;
    private JLabel add;
    private JTextArea tadd;
    private JCheckBox term;
    private JButton sub;
    private JButton pickaCh;
    private JTextArea tout;
    private JLabel res;
    private JTextArea resadd;
    private String genders[] = {"M","F"};
    private JLabel children ;
    public JComboBox childPick;
    String url = "jdbc:sqlite:famtree.db";
    JComboBox comboBox;
    private ArrayList<String> childList = new ArrayList<String>();


    void fillComboBox(){
        try{
            comboBox.removeAllItems();
            ArrayList<String> treeList = new ArrayList<String>();
            Connection conn = DriverManager.getConnection(url,"","");
            Statement cmd = conn.createStatement();
            ResultSet fetch=cmd.executeQuery("SELECT * FROM familyTrees order by ID asc");
            while(fetch.next()){
                treeList.add(fetch.getString("ID")+"-"+fetch.getString("name"));
            }
            fetch.close();
            cmd.close();
            conn.close();
            String[] treeArray = treeList.toArray(new String[treeList.size()]);
            comboBox.setModel(new DefaultComboBoxModel(treeArray));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public GUI() {





        JPanel jPanel = new JPanel();
        jPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(jPanel);
        jPanel.setLayout(null);




        JFrame f = new JFrame("Family Tree.Inc");
        JLabel la1, la2, la3;
        la1 = new JLabel("Welcome to Family Tree.Inc");
        la1.setBounds(70, 70, 200, 50);
        la2 = new JLabel("Can you select the necessary move?");
        la2.setBounds(70, 120, 200, 50);
        la3=new JLabel("Pick an existing tree to delete or view and edit.");
        la3.setBounds(360,120,300,50);
        f.add(la1);
        f.add(la2);
        f.add(la3);
        f.setSize(700, 700);
        f.setLayout(null);
        f.setVisible(true);

        comboBox = new JComboBox();
        comboBox.setBounds(430, 160, 150, 21);
        f.add(comboBox);
        fillComboBox();


       /* JLabel lblNewLabel_existingtree = new JLabel("Pick an existing tree to delete or view and edit.");
        lblNewLabel_existingtree.setBounds(401, 160, 400, 19);
        f.add(lblNewLabel_existingtree);*/



        JButton bt = new JButton("Create New Tree");
        bt.setBounds(70, 160, 155, 50);
        f.add(bt);


        JButton btt = new JButton("View Tree");
        btt.setBounds(70, 220, 155, 50);
        btt.addActionListener(this);
        f.add(btt);
        f.setLayout(null);
        f.setVisible(true);


        JButton bttn = new JButton("Delete Tree");
        bttn.setBounds(70, 280, 155, 50);
        bttn.addActionListener(this);
        f.add(bttn);
        f.setLayout(null);
        f.setVisible(true);

        JButton helpButton = new JButton("Help");
        helpButton.setBounds(70, 340, 155, 50);
        helpButton.addActionListener(this);
        f.add(helpButton);
        f.setLayout(null);
        f.setVisible(true);

        JButton madeBy = new JButton("Made By");
        madeBy.setBounds(70, 400, 155, 50);
        madeBy.addActionListener(this);
        f.add(madeBy);
        f.setLayout(null);
        f.setVisible(true);












        bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{

                    String name=JOptionPane.showInputDialog("Enter the name of your family tree:");
                    if(name.length()==0){
                        JOptionPane.showMessageDialog(null,"Bir isim girmelisiniz!");
                        return;
                    }
                    Connection conn = DriverManager.getConnection(url,"","");
                    Statement cmd = conn.createStatement();
                    ResultSet fetch = cmd.executeQuery("SELECT id from familyTrees WHERE name='"+name+"'");
                    if (fetch.next()){
                        JOptionPane.showMessageDialog(null,"Daha önce kullanılmamış bir isim girmelisiniz!");
                        return;
                    }
                    fetch.close();
                    JOptionPane.showMessageDialog(null,"INSERT INTO familyTrees VALUES(NULL,'"+name+"');");
                    cmd.executeUpdate("INSERT INTO familyTrees VALUES(NULL,'"+name+"');");
                    fetch=cmd.executeQuery("SELECT ID from familyTrees ORDER BY ID DESC");
                    fetch.next();
                    String lastid=fetch.getString("ID");
                    fetch.close();
                    cmd.close();
                    conn.close();
                    JFrame editor=new test(lastid);
                    editor.setVisible(true);
                    fillComboBox();
                }catch(Exception er){

                }

            }
        });

        btt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String treeId=comboBox.getSelectedItem().toString().split("-")[0];
                JFrame editor=new test(treeId);
                editor.setVisible(true);
            }
        });

        bttn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String toDelete=comboBox.getSelectedItem().toString().split("-")[0];
                int confirmation=JOptionPane.showConfirmDialog(null,"Are you sure you want to delete all of this family tree:"+toDelete);
                if (confirmation==0){
                    try{
                        Connection conn = DriverManager.getConnection(url,"","");
                        Statement cmd = conn.createStatement();
                        ResultSet fetch1;
                        Statement cmd1=conn.createStatement();
                        Statement cmd2=conn.createStatement();
                        cmd.executeUpdate("DELETE FROM familyTrees WHERE ID="+toDelete);
                        ResultSet fetch= cmd.executeQuery("SELECT ID FROM person where treeId="+toDelete);
                        while (fetch.next()){
                            fetch1=cmd2.executeQuery("SELECT ID from marriage WHERE femaleId="+fetch.getString("ID")+" OR maleId="+fetch.getString("ID"));
                            if (fetch1.next()){
                                cmd1.executeUpdate("DELETE FROM famTree WHERE marriageId="+fetch1.getString("ID"));
                            }
                            fetch1.close();
                            cmd1.executeUpdate("DELETE FROM marriage WHERE femaleId="+fetch.getString("ID")+" OR maleId="+fetch.getString("ID"));

                        }
                        fetch.close();
                        cmd.executeUpdate("DELETE FROM person WHERE treeId="+toDelete);
                        JOptionPane.showMessageDialog(null,"Selected familyTree is deleted.");
                        fillComboBox();
                    }catch(Exception err){

                    }

                }

            }
        });
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame fram = new JFrame("Family Tree.Inc");
                JLabel la1; JLabel la;
                JLabel la2;JLabel la3;
                JLabel la4;JLabel la5;
                JLabel la6;JLabel la7;
                JLabel la8;JLabel la9;
                JLabel la11;JLabel la12;
                JLabel la13;JLabel la14;
                JLabel la15;JLabel la19;
                JLabel la20;JLabel la21;
                JLabel la22;JLabel la23;
                la1 = new JLabel("By following these instrucitons you would have more efficient usage ==>\n");
                la = new JLabel("To Create a New Tree ==>");
                la2 = new JLabel("Select Create New Tree"+"\n");
                la3 = new JLabel("Fill the blanks with the related informations"+"\n");
                la4 = new JLabel("Push the Add Person button to submit the informations for a person"+"\n");
                la5 = new JLabel("After entering a few people's information"+"\n");
                la6 = new JLabel("According to the event (Marriage/Deaclare Child) select the true button"+"\n");
                la7 = new JLabel("To Declare a Marriage ==>"+"\n" );
                la8 = new JLabel("For marriage from the drop down list, you should select the couple,"+"\n");
                la9=  new JLabel("Push the Declare Marriage button"+"\n");
                la11 = new JLabel("To Declare a Child ==>"+"\n" );
                la12 = new JLabel("To add a child for a couple,"+"\n" );
                la13 = new JLabel("After the enter for childs' informations"+"\n" );
                la14 = new JLabel("Select the mom and dad and push the Submit button"+"\n" );
                la15 = new JLabel("And click OK");
                la19 =new JLabel("To Delete a Tree please follow these steps ==>");
                la20 =new JLabel("From the dropdown menu, select a tree to delete"+"\n");
                la21 =new JLabel("Click Delete Tree button"+"\n ");
                la22 =new JLabel("For further questions please send an e-mail to ");
                la23 = new JLabel("ferda.gunes@ieu.edu.tr / tugay.sarsilmaz@ieu.edu.tr / selin.doga@ieu.edu.tr");
                la1.setBounds(70, -10, 550, 250);
                la.setBounds(70,20,550,250);
                la2.setBounds(70,40,550,250);
                la3.setBounds(70,60,250,250);
                la4.setBounds(70,80,550,250);
                la5.setBounds(70,100,550,250);
                la6.setBounds(70,120,550,250);
                la7.setBounds(70,140,550,250);
                la8.setBounds(70,160,550,250);
                la9.setBounds(70,180,550,250);
                la11.setBounds(70,200,550,250);
                la12.setBounds(70,220,550,250);
                la13.setBounds(70,240,550,250);
                la14.setBounds(70,260,550,250);
                la15.setBounds(70,280,550,250);
                la19.setBounds(70,300,550,250);
                la20.setBounds(70,320,550,250);
                la21.setBounds(70,340,550,250);
                la22.setBounds(70,400,550,250);
                la23.setBounds(70,420,550,250);
                fram.add(la1);fram.add(la);
                fram.add(la2);fram.add(la3);
                fram.add(la4);fram.add(la5);
                fram.add(la6);fram.add(la7);
                fram.add(la8);fram.add(la9);
                fram.add(la11);fram.add(la12);
                fram.add(la13);fram.add(la14);
                fram.add(la15);fram.add(la19);
                fram.add(la20);fram.add(la21);
                fram.add(la22);fram.add(la23);
                fram.setSize(700, 700);
                fram.setLayout(null);
                fram.setVisible(true);}
        });
        madeBy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame fram = new JFrame("Family Tree.Inc");
                JLabel la1;JLabel la2;JLabel la3;JLabel la4;
                la1 = new JLabel("Ferda Güneş");
                la2 = new JLabel("Tugay Şevki Sarsılmaz");
                la3 = new JLabel("Selin Doğa Orhan");
                la4 = new JLabel("Aytuğ Han");
                la1.setBounds(70, 70, 250, 250);
                la2.setBounds(70,90,250,250);
                la3.setBounds(70,110,250,250);
                la4.setBounds(70,130,250,250);
                fram.add(la1);fram.add(la2);fram.add(la3);fram.add(la4);
                fram.setSize(700, 700);
                fram.setLayout(null);
                fram.setVisible(true);}
        });


    }

    public static void main(String args[]) {
        new GUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}



