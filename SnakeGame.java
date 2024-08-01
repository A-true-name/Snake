
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class SnakeGame extends JFrame {
    static final int WIDTH = 1000; //Window size.
    static final int HEIGHT = 1000; //Window size.
    public SnakeGame(){
        JFrame frame = new JFrame();
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.setSize(WIDTH, HEIGHT); //setś window size
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new SnakeGame();
    }

    public class GamePanel extends JPanel implements ActionListener {

        static final int WIDTH = 1000; //Window size.
        static final int HEIGHT = 1000; //Window size.
        static final int CELL_SIZE = 40;
        static final int NUMBER_OF_UNITS = (WIDTH * HEIGHT) / (CELL_SIZE * CELL_SIZE);

        // hold x and y coordinates for body parts of the snake.
        final int x[] = new int[NUMBER_OF_UNITS];
        final int y[] = new int[NUMBER_OF_UNITS];

        private int bodyParts = 6;//Starting length of snake.

        // initial length of the snake and food dimensions.
        int length = 10;
        int foodEaten;
        int foodX;
        int foodY;
        char direction = 'R';

        //char direction = 'D';
        boolean running = false;
        Random random;
        Timer timer;//Runs things such as game speed

        public GamePanel() {
            random = new Random();
            this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
            this.setBackground(Color.black);
            this.setFocusable(true);
            this.addKeyListener(new MyKeyAdapter());

            startGame(); 

        }

        public void startGame() {
            //newFood();
            running = true;
            timer = new Timer(75, this);
            timer.start();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            draw(g);
        }

        public void draw(Graphics g) {
            if (running) {
                g.setColor(Color.red);
                g.fillOval(foodX, foodY, CELL_SIZE, CELL_SIZE); //Paint food

                for (int i = 0; i < bodyParts; i++) {
                    if (i == 0) {
                        g.setColor(Color.green);// Setś colour of the head.
                        g.fillRect(x[i], y[i], CELL_SIZE, CELL_SIZE);
                    } else {
                        g.setColor(new Color(45, 180, 0)); // Sets colour for the rest of the snake.
                        g.fillRect(x[i], y[i], CELL_SIZE, CELL_SIZE);
                    }
                }

                g.setColor(Color.red);
                g.setFont(new Font("Ink Free", Font.BOLD, 40));
                FontMetrics metrics = getFontMetrics(g.getFont());
                //g.drawString("Score: " + foodEaten, (WIDTH - metrics.stringWidth("Score: " + foodEaten)) / 2, g.getFont().getSize());
            } else {
                //gameOver(g);
            }
        }

        public void move() {
            for (int i = bodyParts; i > 0; i--) {
                x[i] = x[i - 1];
                y[i] = y[i - 1];
            }

            switch (direction) { 
                case 'U':
                    y[0] = y[0] - CELL_SIZE;
                    break;
                case 'D':
                    y[0] = y[0] + CELL_SIZE;
                    break;
                case 'L':
                    x[0] = x[0] - CELL_SIZE;
                    break;
                case 'R':
                    x[0] = x[0] + CELL_SIZE;
                    break;
            }
        }

        public void newFood() {//Spawns food randomely within the playing area in only spawning on the rigded lines that the snake can travel on.
            foodX = random.nextInt((int) (WIDTH / CELL_SIZE)) * CELL_SIZE;
            foodY = random.nextInt((int) (HEIGHT / CELL_SIZE)) * CELL_SIZE;

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (running) {
                
                move();
                // checkFood();
                // checkCollisions();
            }
            repaint();              
        }
        public class MyKeyAdapter extends KeyAdapter {
            @Override
            public void keyPressed(KeyEvent e) {// Fixes  public class GamePanel extends JPanel implements ActionListener { having to be abstract.
                // Prevents you from going back on your self.
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (direction != 'R') {
                            direction = 'L';
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (direction != 'L') {
                            direction = 'R';
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if (direction != 'D') {
                            direction = 'U';
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (direction != 'U') {
                            direction = 'D';
                        }
                        break;
                }
            }
        }
    }

}
