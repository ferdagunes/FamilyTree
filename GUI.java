package FamilyTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener {
    // basit temel bir ui olarak yazdım, ağaç yapısı yok

    private JLabel label;
    private JButton button;
    private JPanel panel;
    private JFrame frame;

    public GUI() {
        frame = new JFrame();
        button = new JButton("bir buton");
        button.addActionListener(this);
        label = new JLabel("label");

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        panel.setLayout(new GridLayout());
        panel.add(button);
        panel.add(label);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        new GUI();

    }

    // buralar değişebilir
    // buraya butona basılınca bir aksiyon gerçekleştirmesi için eklemiştim ama eklemedim
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
