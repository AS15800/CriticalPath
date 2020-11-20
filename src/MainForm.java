import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm {
    public static void main(String[] args) {
        MainForm mainForm = new MainForm();
    }

    public MainForm(){
        JFrame frame = new JFrame("JVM Programming Language"); // declare frame variable
        frame.setSize(new Dimension(700, 750)); // size of frame
        frame.setVisible(true); // set frame to visible
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close program

        JButton criticalPathBtn = new JButton("Kotlin Critical Path");
        criticalPathBtn.setPreferredSize(new Dimension(100,100));
        frame.add(criticalPathBtn, BorderLayout.NORTH);
        criticalPathBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CriticalPath.main(null);
            }
        });
    }
}
