package FamilyTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

    private ArrayList<String> childList = new ArrayList<String>();
    private String dates[]
            = { "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25",
            "26", "27", "28", "29", "30",
            "31" };
    private String months[]
            = { "Jan", "feb", "Mar", "Apr",
            "May", "Jun", "July", "Aug",
            "Sup", "Oct", "Nov", "Dec" };
    private String years[]
            = { "1920","1921","1922","1923","1924","1925","1926","1927","1928","1929","1930","1931","1932","1932",
            "1933","1934","1935","1936","1937","1938","1939","1940","1941","1942","1943","1944","1945","1946","1947",
            "1948","1949","1950","1951","1952","1953","1954","1955","1956", "1957","1958","1959","1960","1961","1962",
            "1963","1964","1965","1966","1967","1968","1969","1970","1971","1972","1973","1974","1975","1976","1977",
            "1978","1979","1980","1981","1982","1983","1984","1985","1986","1987","1988","1989","1990","1991","1992",
            "1993","1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006",
            "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019","2020","2021" };


    public GUI() {
        var menuBar = new JMenuBar();
        var fileMenu = new JMenu("File");

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
        f.setLayout(null);
        f.setVisible(true);

        var new_tree = new JMenuItem(bt.getIcon());
        fileMenu.add(new_tree);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        menuBar.setVisible(true);

        JButton btt = new JButton("Merge Trees");
        btt.setBounds(70, 220, 155, 50);
        btt.addActionListener(this);
        f.add(btt);
        f.setLayout(null);
        f.setVisible(true);

        var merge_trees = new JMenuItem(btt.getIcon());
        fileMenu.add(merge_trees);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        menuBar.setVisible(true);

        JButton bttn = new JButton("Delete Tree");
        bttn.setBounds(70, 280, 155, 50);
        bttn.addActionListener(this);
        f.add(bttn);
        f.setLayout(null);
        f.setVisible(true);

        var delete_trees = new JMenuItem(bttn.getIcon());
        fileMenu.add(delete_trees);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        menuBar.setVisible(true);

        bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setTitle("FAMILY TREE");
                setBounds(300, 90, 900, 600);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setResizable(false);

                c = getContentPane();
                c.setLayout(null);

                title = new JLabel("Please fill the personal informations");
                title.setFont(new Font("Arial", Font.PLAIN, 15));
                title.setSize(300, 30);
                title.setLocation(300, 30);
                c.add(title);

                name = new JLabel("Name");
                name.setFont(new Font("Arial", Font.PLAIN, 18));
                name.setSize(100, 20);
                name.setLocation(100, 100);
                c.add(name);

                tname = new JTextField();
                tname.setFont(new Font("Arial", Font.PLAIN, 15));
                tname.setSize(190, 20);
                tname.setLocation(200, 100);
                c.add(tname);

                mno = new JLabel("Mobile");
                mno.setFont(new Font("Arial", Font.PLAIN, 18));
                mno.setSize(100, 20);
                mno.setLocation(100, 150);
                c.add(mno);

                tmno = new JTextField();
                tmno.setFont(new Font("Arial", Font.PLAIN, 15));
                tmno.setSize(150, 20);
                tmno.setLocation(200, 150);
                c.add(tmno);

                gender = new JLabel("Gender");
                gender.setFont(new Font("Arial", Font.PLAIN, 18));
                gender.setSize(100, 20);
                gender.setLocation(100, 200);
                c.add(gender);

                genderc = new JComboBox(genders);
                genderc.setFont(new Font("Arial", Font.PLAIN, 15));
                genderc.setSize(50, 20);
                genderc.setLocation(200, 200);
                c.add(genderc);

                dob = new JLabel("DOB");
                dob.setFont(new Font("Arial", Font.PLAIN, 18));
                dob.setSize(100, 20);
                dob.setLocation(100, 250);
                c.add(dob);

                date = new JComboBox(dates);
                date.setFont(new Font("Arial", Font.PLAIN, 15));
                date.setSize(50, 20);
                date.setLocation(200, 250);
                c.add(date);

                month = new JComboBox(months);
                month.setFont(new Font("Arial", Font.PLAIN, 15));
                month.setSize(60, 20);
                month.setLocation(250, 250);
                c.add(month);

                year = new JComboBox(years);
                year.setFont(new Font("Arial", Font.PLAIN, 15));
                year.setSize(60, 20);
                year.setLocation(320, 250);
                c.add(year);

                children = new JLabel("Children:");
                children.setFont(new Font("Arial", Font.PLAIN, 18));
                children.setSize(100, 20);
                children.setLocation(100, 300);
                c.add(children);
                childList.add("0");
                String url = "jdbc:sqlite:famtree.db";
                Connection conn = null;
                try {
                    conn = DriverManager.getConnection(url, "", "");
                    JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");
                    Statement stChild = conn.createStatement();
                    String sql ="SELECT * FROM person" ;
                    ResultSet rs  = stChild.executeQuery(sql);
                    while(rs.next()) {

                        childList.add(rs.getString("ID")+"-"+ rs.getString("name")+"-"+ rs.getString("gender")/*+"  "+ rs.getString("bDate")*/ );
                       /*ArrayList<String> childId = new ArrayList<String>();
                        childId.add(rs.getString("ID"));*/
                    }

                }
                catch (SQLException f){

                }


                String[] childArray = childList.toArray(new String[childList.size()]);
                childPick = new JComboBox(childArray);
                childPick.setFont(new Font("Arial", Font.PLAIN, 15));
                childPick.setSize(150, 20);
                childPick.setLocation(200, 300);
                c.add(childPick);
                childPick.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                    }});

                pickaCh = new JButton("pick");
                pickaCh.setFont(new Font("Arial", Font.PLAIN, 15));
                pickaCh.setSize(70, 20);
                pickaCh.setLocation(360, 300);
                c.add(pickaCh);
                pickaCh.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String url = "jdbc:sqlite:famtree.db";
                        Connection conn = null;
                        try{
                            String name = tname.getText();
                            String bdate = date.getSelectedItem().toString()+"-"+ month.getSelectedItem().toString()+"-"+ year.getSelectedItem().toString();
                            String gender = genderc.getSelectedItem().toString();

                            conn = DriverManager.getConnection(url,"","");
                            JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");
                            String selectedValue = childPick.getSelectedItem().toString();
                            String[] childData = selectedValue.split("-");
                            String childId = childData[0];

                            Statement stPickCh = conn.createStatement() ;
                            stPickCh.executeUpdate("INSERT INTO famTree (name, gender, bdate, parentOf)"+
                                    "VALUES ('"+name+"', '"+gender+"','"+bdate+"','"+childId+"')");
                        }
                        catch (Exception f){}
                    }});


                /*

                DefaultListModel<String> childrenList = new DefaultListModel<>();
                JList<String> cList = new JList<>(childrenList);
                childPick = cList;
                childPick.setFont(new Font("Arial", Font.PLAIN, 15));
                childPick.setSize(200, 75);
                childPick.setLocation(200, 300);

                c.add(childPick);

                term = new JCheckBox("Accept Terms And Conditions.");
                term.setFont(new Font("Arial", Font.PLAIN, 15));
                term.setSize(250, 20);
                term.setLocation(150, 400);
                c.add(term);
                */
                sub = new JButton("Submit");
                sub.setFont(new Font("Arial", Font.PLAIN, 15));
                sub.setSize(100, 20);
                sub.setLocation(150, 450);
                sub.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        tout.setText("");
                        String name = tname.getText();
                        String bdate = date.getSelectedItem().toString()+"-"+ month.getSelectedItem().toString()+"-"+ year.getSelectedItem().toString();
                        String gender = genderc.getSelectedItem().toString();

                        String url = "jdbc:sqlite:famtree.db";
                        Connection conn = null;
                        try{
                            conn = DriverManager.getConnection(url,"","");
                            JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");
                            Statement stsec = conn.createStatement() ;
                            stsec.executeUpdate("INSERT INTO person (name, gender, bdate)"+
                                    "VALUES ('"+name+"', '"+gender+"','"+bdate+"')");
                            /*Statement stFamtree = conn.createStatement() ;
                            stFamtree.executeUpdate("INSERT INTO famTree (name, gender, bdate)"+
                                    "VALUES ('"+name+"', '"+gender+"','"+bdate+"')");*/
                            conn.close();
                        }
                        catch(Exception p)
                        {
                            JOptionPane.showMessageDialog(null, p.getMessage());
                        }


                        String sql ="SELECT  f.name AS 'f.name', f.gender AS 'f.gender' , p.name AS 'p.name'  FROM person p, famTree f WHERE p.ID = f.parentOf"  ;

                        try{
                            conn = DriverManager.getConnection(url,"","");
                            Statement st = conn.createStatement() ;
                            ResultSet rs  = st.executeQuery(sql);


                            while(rs.next()) {
                                /*
                                tout.append(rs.getString("ID")+"  "+ rs.getString("name")+"  "+ rs.getString("gender")+"  "+ rs.getString("bDate") + "\n");
                                */

                                JOptionPane.showMessageDialog(null, rs.getString("f.gender") );

                                if(rs.getString("f.gender").equals("F")){

                                    tout.append(rs.getString("f.name")+" is mother of "+ rs.getString("p.name")+ "\n");
                                }
                                else{
                                    tout.append(rs.getString("f.name")+" is father of "+ rs.getString("p.name")+ "\n");

                                }



                            }

                        }catch(Exception t) {
                            JOptionPane.showMessageDialog(null, t.getMessage());
                        }



                    }



                });
                c.add(sub);

                tout = new JTextArea();
                tout.setFont(new Font("Arial", Font.PLAIN, 15));
                tout.setSize(300, 400);
                tout.setLocation(500, 100);
                tout.setLineWrap(true);
                c.add(tout);

                res = new JLabel("");
                res.setFont(new Font("Arial", Font.PLAIN, 18));
                res.setSize(500, 25);
                res.setLocation(100, 500);
                c.add(res);

                resadd = new JTextArea();
                resadd.setFont(new Font("Arial", Font.PLAIN, 15));
                resadd.setSize(200, 75);
                resadd.setLocation(580, 175);
                resadd.setLineWrap(true);
                c.add(resadd);

                setVisible(true);
                f.setSize(600,600);
                f.setLayout(null);
                f.setVisible(true);
            }
        });

        btt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame fr = new JFrame("Family Tree.Inc");
                JLabel la1, la2;
                la1 = new JLabel("Please select the trees to merge.");
                la1.setBounds(70, 70, 250, 50);
                la2 = new JLabel("At a time only 2 trees can merge");
                la2.setBounds(70, 120, 200, 50);
                fr.add(la1);
                fr.add(la2);
                fr.setSize(700, 700);
                fr.setLayout(null);
                fr.setVisible(true);

            }
        });

        bttn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame fra = new JFrame("Family Tree.Inc");
                JLabel la1;
                la1 = new JLabel("Please select the tree to delete.");
                la1.setBounds(70, 70, 250, 50);
                fra.add(la1);
                fra.setSize(700, 700);
                fra.setLayout(null);
                fra.setVisible(true);


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


   
