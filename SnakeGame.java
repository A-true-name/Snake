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
        this.setBackground(Color.black);
        frame.setSize(WIDTH, HEIGHT); //setś window size
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new SnakeGame();
    }

    public class GamePanel extends JPanel implements ActionListener {

        static final int WIDTH = 1000; //Window size.
        static final int HEIGHT = 1000; //Window size.
        static final int CELL_SIZE = 40; // Size of a square inside the game.
        static final int NUMBER_OF_UNITS = (WIDTH * HEIGHT) / (CELL_SIZE * CELL_SIZE);

        // hold x and y coordinates for body parts of the snake.
        final int x[] = new int[NUMBER_OF_UNITS];
        final int y[] = new int[NUMBER_OF_UNITS];

        private int bodyParts = 6;//Starting length of snake.

        //  food dimensions.
        int length = 10;
        int foodEaten;
        int foodX;//Horizontal.(Up)
        int foodY;//Vertical.(Side to side)
        char direction = 'R';

        //char direction = 'D';
        boolean running = false; 
        Random random; // Randomizer such as having food spawn in random places.
        Timer timer;//Runs things such as game speed

        public GamePanel() {
            random = new Random();
            this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
            //this.setBackground(Color.black);
            this.setFocusable(true); //Allows for GamePanel for keyboard inouts.
            this.addKeyListener(new MyKeyAdapter());

            startGame(); 

        }


        public void startGame() {
            newFood();
            running = true;//Runs the game when true
            timer = new Timer(75, this);// Speed of the game.
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
                g.drawString("Score: " + foodEaten, (WIDTH - metrics.stringWidth("Score: " + foodEaten)) / 2, g.getFont().getSize());
            } else {
                //gameOver(g);
            }
        }

        public void move() {// Makes the segment of the snake follow the segment infront of it untill it reaches the head which you controll.
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
        
        public void Collisions() {
            System.out.println(" Collision" );
            for (int i = bodyParts; i > 0; i--) {
                if ((x[0] == x[i]) && (y[0] == y[i])) {
                    running = false;
                    System.out.println(" Collision" );
                }
            }

            if (x[0] < 0) {
                running = false;
                System.out.println(" Collision" );
            }

            if (x[0] > WIDTH) {
                running = false;
                System.out.println(" Collision" );
            }

            if (y[0] < 0) {
                running = false;
                System.out.println(" Collision" );
              
            }

            if (y[0] > HEIGHT) {
                running = false;
              System.out.println(" Collision" );
            }

            if (!running) {
                timer.stop();
                System.out.println(" Collision" );
            }
        }

        public void newFood() {//Spawns food randomely within the playing area in only spawning on the rigded lines that the snake can travel on.
            foodX = random.nextInt((int) (WIDTH / CELL_SIZE)) * CELL_SIZE;
            foodY = random.nextInt((int) (HEIGHT / CELL_SIZE)) * CELL_SIZE;

        }

        @Override
        public void actionPerformed(ActionEvent e) {// Fixes  public class GamePanel extends JPanel implements ActionListener { having to be abstract.
            if (running) {
                Collisions();

                move();
                
                
            }
            repaint();//Constantly updates the game              
        }
        public class MyKeyAdapter extends KeyAdapter {
            @Override
            public void keyPressed(KeyEvent e) {
                // Allows you to change the direction of the snake but prevents you from turning directly back on yourself. 
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
