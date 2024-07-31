import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {
    
    static final int WIDTH = 500; //Window size.
    static final int HEIGHT = 500; //Window size.
    static final int CELL_SIZE = 20;
    static final int NUMBER_OF_UNITS = (WIDTH * HEIGHT) / (CELL_SIZE * CELL_SIZE);

    // hold x and y coordinates for body parts of the snake.
    final int x[] = new int[NUMBER_OF_UNITS];
    final int y[] = new int[NUMBER_OF_UNITS];
    
    // initial length of the snake and food dimensions.
    int length = 5;
    int foodEaten;
    int foodX;
    int foodY;
    
  

    char direction = 'D';
    boolean running = false;
    Random random;
    Timer timer;//Runs things such as game speed
    
   
    public GamePanel() {
        // Initialize components or start game logic
    }

    @Override
    public void actionPerformed(ActionEvent e) {//To fix public class GamePanel extends JPanel implements ActionListener {  not being abstract error.
        
    }
    

    // Other methods related to game logic can be added here

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
    }
            public void newFood() {
            foodX = random.nextInt((int) (WIDTH / CELL_SIZE)) * CELL_SIZE;
            foodY = random.nextInt((int) (HEIGHT / CELL_SIZE)) * CELL_SIZE;
}}