package FamilyTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener {
    private JLabel label;
    private JButton button;
    private JPanel panel;
    private JFrame frame;


    public GUI() {

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




        JButton btt = new JButton("Merge Trees");
        btt.setBounds(70, 220, 155, 50);
        f.add(btt);
        f.setLayout(null);
        f.setVisible(true);

        JButton bttn = new JButton("Delete Tree");
        bttn.setBounds(70, 280, 155, 50);
        f.add(bttn);
        f.setLayout(null);
        f.setVisible(true);
    }

    public static void main(String args[]) {
        new GUI();

    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}


    /*
public class GUI implements ActionListener {
    // basit temel bir ui olarak yazdım, ağaç yapısı yok

    private JLabel label;
    private JButton button;
    private JPanel panel;
    private JFrame frame;




    public GUI() {
        frame = new JFrame();
        button = new JButton("button");
        button.addActionListener(this);
        label = new JLabel("");
    }


    public static void main(String[] args) {
        new GUI();

    }

    // buralar değişebilir
    // buraya butona basılınca bir aksiyon gerçekleştirmesi için eklemiştim ama eklemedim
    @Override
    public void actionPerformed(ActionEvent newTree ) {


    }


}
*/