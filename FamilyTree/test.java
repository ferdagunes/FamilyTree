package FamilyTree;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Scrollbar;
import java.awt.Choice;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

public class test extends JFrame {

    String [] genders = {"M", "F"};


    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    private JComboBox genderComboBox;
    private JTextField textField_2;
    private ArrayList<String> marriageM = new ArrayList<String>();
    private ArrayList<String> marriageF = new ArrayList<String>();
    private ArrayList<String> parentsL = new ArrayList<String>();
    private ArrayList<String> children = new ArrayList<String>();
    private String dates[]
            = { "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25",
            "26", "27", "28", "29", "30",
            "31" };
    private String months[]
            = { "Jan", "Feb", "Mar", "Apr",
            "May", "Jun", "July", "Aug",
            "Sep", "Oct", "Nov", "Dec" };
    private String years[]
            = { "1920","1921","1922","1923","1924","1925","1926","1927","1928","1929","1930","1931","1932","1932",
            "1933","1934","1935","1936","1937","1938","1939","1940","1941","1942","1943","1944","1945","1946","1947",
            "1948","1949","1950","1951","1952","1953","1954","1955","1956", "1957","1958","1959","1960","1961","1962",
            "1963","1964","1965","1966","1967","1968","1969","1970","1971","1972","1973","1974","1975","1976","1977",
            "1978","1979","1980","1981","1982","1983","1984","1985","1986","1987","1988","1989","1990","1991","1992",
            "1993","1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006",
            "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019","2020","2021" };


    JComboBox comboBox;
    JComboBox comboBox_1;
    JComboBox comboBox_2;
    JComboBox comboBox_3;
    private JFrame frame;
    JTree tree;
    String familySide="asc";


    public String treeId;
    String url = "jdbc:sqlite:famtree.db";
    /**
     * Launch the application.
     */

    void renderFamilyTree() {
        try {
            frame.remove(tree);
        }catch(Exception e){

        }
        try {
            String dede="0";
            Connection conn = DriverManager.getConnection(url,"","");
            Statement cmd = conn.createStatement();
            ResultSet fetch  = cmd.executeQuery("SELECT ID,gender FROM person WHERE gender='M' AND treeId="+this.treeId+" order by id "+familySide);
            if(!fetch.next()){
                return;
            }
            while(fetch.next()) {
                Statement cmd1 = conn.createStatement() ;
                ResultSet fetch1=cmd1.executeQuery("SELECT * FROM famTree WHERE parentOf="+fetch.getString("ID"));
                if(!fetch1.next()) {
                    Statement cmd2=conn.createStatement();
                    ResultSet fetch2=cmd2.executeQuery("SELECT * FROM marriage WHERE maleId="+fetch.getString("ID"));
                    if(!fetch2.next()){
                        continue;
                    }

                    Statement cmd3=conn.createStatement();
                    ResultSet fetch3=cmd3.executeQuery("SELECT * FROM famTree WHERE parentOf="+fetch2.getString("femaleId"));
                    if(!fetch3.next()) {
                        dede=fetch.getString("ID");
                        cmd1.close();
                        fetch1.close();
                        cmd2.close();
                        fetch2.close();
                        cmd3.close();
                        fetch3.close();
                        fetch.close();
                        cmd.close();
                        break;
                    }
                }

            }
            if(dede.equals("0")){
                JOptionPane.showMessageDialog(null, "More people needed!");
                return;
            }
            String label="";
            fetch  = cmd.executeQuery("SELECT name FROM person WHERE ID="+dede);
            fetch.next();
            label+=fetch.getString("name")+" - ";
            fetch.close();
            fetch  = cmd.executeQuery("SELECT person.name FROM person, marriage WHERE treeId="+this.treeId+" AND marriage.femaleId=person.ID AND marriage.maleId="+dede);
            fetch.next();
            label+=fetch.getString("name");
            fetch.close();
            DefaultMutableTreeNode nodeDede= new DefaultMutableTreeNode(label);
            tree=new JTree(nodeDede);

            tree.setBounds(502, 250, 475, 350);
            this.getContentPane().add(tree);
            cmd.close();
            conn.close();
            createtree(dede,nodeDede);
            for (int i = 0; i < tree.getRowCount(); i++) {
                tree.expandRow(i);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }



    void createtree(String parentid,DefaultMutableTreeNode parentNode) {
        try {
            Connection conn = DriverManager.getConnection(url,"","");
            Statement cmd = conn.createStatement();
            ResultSet fetch  = cmd.executeQuery("SELECT gender FROM person WHERE treeId="+this.treeId+" AND ID="+parentid);
            fetch.next();
            String genderText="";
            if(fetch.getString("gender").equals("M")) {
                genderText="maleId";
            }else {
                genderText="femaleId";
            }
            fetch.close();

            fetch  = cmd.executeQuery("SELECT ID FROM marriage WHERE "+genderText+"="+parentid);
            if(!fetch.next()) {
                return;
            }
            String marriageid=fetch.getString("ID");
            fetch.close();
            fetch=cmd.executeQuery("SELECT parentOf FROM famTree WHERE marriageId="+marriageid);
            while(fetch.next()) {

                Statement cmd1 = conn.createStatement();
                ResultSet fetch1  = cmd1.executeQuery("SELECT * FROM person WHERE treeId="+this.treeId+" AND ID="+fetch.getString("parentOf"));
                fetch1.next();
                String label=fetch1.getString("name");
                String id=fetch1.getString("ID");
                genderText="";
                String selectGenderText="";
                if(fetch1.getString("gender").equals("M")) {
                    genderText="maleId";
                    selectGenderText="femaleId";
                }else {
                    genderText="femaleId";
                    selectGenderText="maleId";
                }
                fetch1.close();
                fetch1=cmd1.executeQuery("SELECT person.name FROM marriage, person WHERE person.treeId="+this.treeId+" AND marriage."+selectGenderText+"=person.ID AND marriage."+genderText+"="+id);
                if(fetch1.next()) {
                    label+=" - "+fetch1.getString("name");
                }
                fetch1.close();
                DefaultMutableTreeNode node= new DefaultMutableTreeNode(label);
                parentNode.add(node);

                createtree(id,node);


            }
            conn.close();
        }catch(Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    void comboBoxUpdate(){
        marriageM = new ArrayList<String>();
        marriageF = new ArrayList<String>();
        children = new ArrayList<String>();
        parentsL = new ArrayList<String>();
        comboBox.removeAllItems();
        comboBox_1.removeAllItems();
        comboBox_2.removeAllItems();
        comboBox_3.removeAllItems();
        try{
            Connection conn = DriverManager.getConnection(url, "", "");
            Statement stChild = conn.createStatement();
            String sql ="SELECT * FROM person WHERE treeId="+this.treeId+" AND gender ='F' " ;
            ResultSet rs  = stChild.executeQuery(sql);
            while(rs.next()) {
                Statement cmd=conn.createStatement();
                ResultSet fetch=cmd.executeQuery("SELECT ID from marriage WHERE femaleId="+rs.getString("ID"));
                if(!fetch.next()){
                    marriageF.add(rs.getString("ID")+"-"+ rs.getString("name")+"-"+ rs.getString("gender")/*+"  "+ rs.getString("bDate")*/ );
                }
                fetch.close();
                cmd.close();
            }
            rs.close();
            String[] marriageFemArray = marriageF.toArray(new String[marriageF.size()]);
            comboBox.setModel(new DefaultComboBoxModel(marriageFemArray));

            stChild = conn.createStatement();
            sql ="SELECT * FROM person WHERE treeId="+this.treeId+" AND gender ='M' " ;
             rs  = stChild.executeQuery(sql);
            while(rs.next()) {
                Statement cmd=conn.createStatement();
                ResultSet fetch=cmd.executeQuery("SELECT ID from marriage WHERE maleId="+rs.getString("ID"));
                if (!fetch.next()){
                    marriageM.add(rs.getString("ID")+"-"+ rs.getString("name")+"-"+ rs.getString("gender")/*+"  "+ rs.getString("bDate")*/ );
                }
                fetch.close();
                cmd.close();

            }
            rs.close();
            String[] marriageMenArray = marriageM.toArray(new String[marriageM.size()]);
            comboBox_1.setModel(new DefaultComboBoxModel(marriageMenArray));

            Statement st = conn.createStatement();
            sql ="SELECT * FROM marriage";
            rs  = st.executeQuery(sql);

            while(rs.next()) {
                Statement stSec = conn.createStatement();
                ResultSet rsSec = stSec.executeQuery("SELECT name,treeId FROM person WHERE ID ="+rs.getString("maleId"));
                rsSec.next();
                if(!rsSec.getString("treeId").equals(this.treeId)){
                    continue;
                }
                String label = rs.getString("ID")+ "-"+ rsSec.getString("name");
                rsSec.close();
                rsSec = stSec.executeQuery("SELECT name FROM person WHERE ID ="+rs.getString("femaleId"));
                rsSec.next();
                label += "-"+rsSec.getString("name");
                rsSec.close();
                parentsL.add(label);

            }
            rs.close();
            String[] parents = parentsL.toArray(new String[parentsL.size()]);
            comboBox_2.setModel(new DefaultComboBoxModel(parents));

            st = conn.createStatement();
            sql ="SELECT * FROM person WHERE treeId="+this.treeId;
            rs  = st.executeQuery(sql);
            while(rs.next()) {
                Statement cmd=conn.createStatement();
                ResultSet fetch=cmd.executeQuery("SELECT ID from famTree WHERE parentOf="+rs.getString("ID"));
                if(!fetch.next()){
                    children.add(rs.getString("ID")+"-"+rs.getString("name"));
                }
                fetch.close();
                cmd.close();
            }
            rs.close();
            st.close();
            conn.close();
            String[] childArray = children.toArray(new String[children.size()]);
            comboBox_3.setModel(new DefaultComboBoxModel(childArray));

        }catch(Exception e){

        }


    }



    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    test frame = new test("0");
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    /**
     * Create the frame.
     */
    public test(String treeId) {
        this.treeId=treeId;
        Connection conn=null;
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 650);



    /*

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnNewMenu = new JMenu("File");
        menuBar.add(mnNewMenu);

        JMenu mnNewMenu_1 = new JMenu("Create New");
        mnNewMenu.add(mnNewMenu_1);

        JMenu mnNewMenu_2 = new JMenu("View Tree");
        mnNewMenu.add(mnNewMenu_2);

        JMenu mnNewMenu_3 = new JMenu("Delete Tree" );
        mnNewMenu.add(mnNewMenu_3);

        JMenu mnNewMenu_4 = new JMenu("About");
        menuBar.add(mnNewMenu_4);

        JMenu mnNewMenu_5 = new JMenu("Help");
        menuBar.add(mnNewMenu_5);
        */

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);


        JLabel lblNewLabel = new JLabel("Name:");
        lblNewLabel.setBounds(10, 35, 45, 19);
        contentPane.add(lblNewLabel);





        textField = new JTextField();
        textField.setBounds(60, 35, 170, 19);
        contentPane.add(textField);
        textField.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("Birth Date(d/m/y):");
        lblNewLabel_2.setBounds(380, 35, 100, 19);
        contentPane.add(lblNewLabel_2);

        JComboBox comboBox_4 = new JComboBox(dates);
        comboBox_4.setBounds(500, 35, 40, 19);
        contentPane.add(comboBox_4);

        JComboBox comboBox_5 = new JComboBox(months);
        comboBox_5.setBounds(550, 35, 60, 19);
        contentPane.add(comboBox_5);

        JComboBox comboBox_6 = new JComboBox(years);
        comboBox_6.setBounds(620, 35, 60, 19);
        contentPane.add(comboBox_6);

        DefaultListModel<String> l = new DefaultListModel<>();

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 250, 475, 350);
        contentPane.add(scrollPane);
        JTextArea textArea = new JTextArea();
        scrollPane.setViewportView(textArea);

        JLabel lblNewLabel_bride = new JLabel("Bride:");
        lblNewLabel_bride.setBounds(10, 100, 100, 19);
        contentPane.add(lblNewLabel_bride);


        comboBox = new JComboBox();
        comboBox.setBounds(60, 100, 200, 21);
        contentPane.add(comboBox);

        JLabel lblNewLabel_groom = new JLabel("Groom:");
        lblNewLabel_groom.setBounds(300, 100, 100, 19);
        contentPane.add(lblNewLabel_groom);

        comboBox_1 = new JComboBox();
        comboBox_1.setBounds(360, 100, 200, 21);
        contentPane.add(comboBox_1);

        JLabel lblNewLabel_child = new JLabel("Child:");
        lblNewLabel_child.setBounds(300, 145, 100, 19);
        contentPane.add(lblNewLabel_child);


        comboBox_3 = new JComboBox();
        comboBox_3.setBounds(360, 145, 200, 21);
        contentPane.add(comboBox_3);

        JLabel lblNewLabel_couple = new JLabel("Couple:");
        lblNewLabel_couple.setBounds(10, 145, 100, 19);
        contentPane.add(lblNewLabel_couple);


        comboBox_2 = new JComboBox();
        comboBox_2.setBounds(60, 145, 200, 21);
        contentPane.add(comboBox_2);

        comboBoxUpdate();
        renderFamilyTree();

        JButton btnNewButton = new JButton("Add Person");
        btnNewButton.setBounds(824, 34, 150, 21);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = textField.getText();
                String bdate = comboBox_4.getSelectedItem().toString()+"-"+
                                comboBox_5.getSelectedItem().toString()+"-"+
                                 comboBox_6.getSelectedItem().toString();
                String gender = genderComboBox.getSelectedItem().toString();
                try{
                    Connection conn = DriverManager.getConnection(url,"","");
                    Statement st = conn.createStatement() ;
                    st.executeUpdate("INSERT INTO person (treeId, name, gender, bdate)"+
                            "VALUES ("+treeId+",'"+name+"', '"+gender+"','"+bdate+"')");

                    textArea.setText(" ");

                    String sql ="SELECT * FROM person WHERE treeId="+treeId ;
                        ResultSet rs  = st.executeQuery(sql);
                        while(rs.next()) {
                            textArea.append(rs.getString("ID")+"  "+ rs.getString("name")+"  "+ rs.getString("gender")+"  "+ rs.getString("bDate")+"\n");
                        }
                            rs.close();
                            st.close();
                            conn.close();
                }
                catch(SQLException f)
                {
                    JOptionPane.showMessageDialog(null, f.getMessage());
                }
                comboBoxUpdate();
                renderFamilyTree();
            }
        });
        contentPane.add(btnNewButton);

        JLabel lblNewLabel_1 = new JLabel("Gender:");
        lblNewLabel_1.setBounds(250, 35, 65, 19);
        contentPane.add(lblNewLabel_1);

        genderComboBox = new JComboBox(genders);
        genderComboBox.setBounds(310, 35, 50, 19);
        contentPane.add(genderComboBox);

        /*
        textField_2 = new JTextField();
        textField_2.setBounds(370, 35, 120, 19);
        contentPane.add(textField_2);
        textField_2.setColumns(10);

         */

        JButton btnNewButton_5 = new JButton("Export as PNG");
        btnNewButton_5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String fileName=JOptionPane.showInputDialog("Enter file name:");
                if (fileName.length()==0){
                    JOptionPane.showMessageDialog(null,"You must enter a vaild filename");
                    return;
                }
                if (fileName.contains("/") || fileName.contains("\\") || fileName.contains(":") || fileName.contains("*") || fileName.contains("\"") || fileName.contains("<") || fileName.contains(">") || fileName.contains("|")){
                    JOptionPane.showMessageDialog(null,"You must enter a vaild filename");
                    return;
                }
                BufferedImage img = new BufferedImage(tree.getWidth(), tree.getHeight(), BufferedImage.TYPE_INT_RGB);
                tree.paint(img.getGraphics());
                File outputfile = new File("c://users/"+System.getenv("USERNAME")+"/desktop/"+fileName+".png");
                try {
                    ImageIO.write(img, "png", outputfile);
                    JOptionPane.showMessageDialog(null,"Screenshot is saved successfully.");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnNewButton_5.setBounds(730, 220, 120, 21);
        contentPane.add(btnNewButton_5);






        JButton btnNewButton_1 = new JButton("Declare a Marriage");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection conn = DriverManager.getConnection(url, "", "");
                    Statement stMarriage = conn.createStatement();

                    String selectedFem = comboBox.getSelectedItem().toString();
                    String selectedMale = comboBox_1.getSelectedItem().toString();

                    String[] femData = selectedFem.split("-");
                    String femaleId = femData[0];

                    String[] maleData = selectedMale.split("-");
                    String maleId = maleData[0];



                    String sql ="INSERT INTO marriage (maleId, femaleId) VALUES ('"+maleId+"', '"+ femaleId+"')" ;
                    stMarriage.executeUpdate(sql);
                    comboBoxUpdate();
                    stMarriage.close();
                    conn.close();

                }
                catch (SQLException f){
                    JOptionPane.showMessageDialog(null, f.getMessage());
                }
                renderFamilyTree();
            }
        });
        btnNewButton_1.setBounds(824, 100, 150, 21);
        contentPane.add(btnNewButton_1);


        /*
        JButton btnNewButton_2 = new JButton("Refresh");
        btnNewButton_2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                renderFamilyTree();

            }
        });
        btnNewButton_2.setBounds(885, 220, 90, 21);
        contentPane.add(btnNewButton_2);
        */
        JButton btnNewButton_3 = new JButton("Switch Side");
        btnNewButton_3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                if (familySide == "asc") {
                    familySide = "desc";
                } else {
                    familySide = "asc";
                }
                renderFamilyTree();
            }
        });
        btnNewButton_3.setBounds(855, 220, 120, 21);
        contentPane.add(btnNewButton_3);

        JButton btnNewButton_4 = new JButton("Declare a Child");
        btnNewButton_4.setBounds(824, 145, 150, 21);
        contentPane.add(btnNewButton_4);
        btnNewButton_4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection conn = DriverManager.getConnection(url, "", "");
                    Statement st = conn.createStatement();
                    String marriageID = comboBox_2.getSelectedItem().toString().split("-")[0];
                    String childId = comboBox_3.getSelectedItem().toString().split("-")[0];



                    String sql ="INSERT INTO famTree (marriageId, parentOf) VALUES("+marriageID+","+childId+" )";
                    st.executeUpdate(sql);
                    st.close();
                    conn.close();
                    comboBoxUpdate();
                }
                catch (Exception f){

                    JOptionPane.showMessageDialog(null, f.getMessage());
                }
                renderFamilyTree();
            }

        });


    }

}
