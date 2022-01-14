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
        JLabel la1, la2;
        la1 = new JLabel("Welcome to Family Tree.Inc");
        la1.setBounds(70, 70, 200, 50);
        la2 = new JLabel("Can you select the necessary move?");
        la2.setBounds(70, 120, 200, 50);
        f.add(la1);
        f.add(la2);
        f.setSize(700, 700);
        f.setLayout(null);
        f.setVisible(true);




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

        comboBox = new JComboBox();
        comboBox.setBounds(400, 220, 150, 21);
        f.add(comboBox);
        fillComboBox();



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


    }

    public static void main(String args[]) {
        new GUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}



