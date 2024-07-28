import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.awt.FlowLayout;  
import javax.swing.JButton;  
import javax.swing.JFrame;  
import javax.swing.JLabel;  
import javax.swing.JPanel; 
import java.awt.Frame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;




abstract class GamePanel extends JPanel implements ActionListener{
    public static void main(String s[]) {  
    JFrame frame = new JFrame(); //Makes the frame.
        JPanel panel = new JPanel();  
        panel.setLayout(new FlowLayout());  
        JLabel label = new JLabel();  
        JButton button = new JButton();  
        button.setText("Button"); //Adds a button.
        panel.add(label);  
        panel.add(button);  
        frame.add(panel);  
        frame.setSize(960, 540);  
        frame.setLocationRelativeTo(null);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setVisible(true);

}
    
    
    static final int WIDTH = 960;
    static final int HEIGHT = 540;
    
    
    int length = 5;
    boolean running = false;
    
    
}