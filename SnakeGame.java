import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class SnakeGame extends JFrame {
    static final int WIDTH = 1016; //Window size.
    static final int HEIGHT = 1036; //Window size.
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

        static final int WIDTH = 1000;  // Used for every part of the code that needs WIDTH apart from the initial window.
        static final int HEIGHT = 1000; // Used for every part of the code that needs HEIGHT apart from the initial window.
        static final int CELL_SIZE = 40; // Size of a square inside the game.
        static final int NUMBER_OF_UNITS = (WIDTH * HEIGHT) / (CELL_SIZE * CELL_SIZE);

        // hold x and y coordinates for body parts of the snake.
        final int x[] = new int[NUMBER_OF_UNITS];
        final int y[] = new int[NUMBER_OF_UNITS];

        private int bodyParts = 3;//Starting length of snake.

        //  food dimensions.
        int length = 10;
        int foodEaten;
        int foodX;//Horizontal.(Up)
        int foodY;//Vertical.(Side to side)
        char direction = 'R'; //Starts the snake moving right.
        int movespeed = 120 ;//Move speed of the snake.
        
        boolean startgame = false;
    

        //char direction = 'D';
        boolean started = false; // To track if the game has started with the first move
        boolean running = false; //RUns the updating features of the game when true. 
        Random random; // Randomizer such as having food spawn in random places.
        Timer timer;//Runs things such as game speed
        private JButton restartButton;//Button to restart game. 

        public GamePanel() {
            random = new Random();
            this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
            this.setBackground(Color.black);
            this.setFocusable(true); //Allows for GamePanel for keyboard inouts.
            this.addKeyListener(new MyKeyAdapter());

            restartButton = new JButton("Restart");
            restartButton.setBounds(WIDTH / 2 - 50, HEIGHT / 2 + 50, 100, 30);  // position the button
            restartButton.setFocusable(false);
            restartButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        restartGame();
                    }
                });
            this.add(restartButton);
            restartButton.setVisible(false);  // hide the button initially

            StartingsnakePosition();

            startGame(); 

        }

        public void StartingsnakePosition() {
            int startX = WIDTH / 2 / CELL_SIZE * CELL_SIZE; // Align to the grid
            int startY = HEIGHT / 2 / CELL_SIZE * CELL_SIZE; // Align to the grid

            for (int i = 0; i < bodyParts; i++) {
                x[i] = startX - i * CELL_SIZE;
                y[i] = startY;
            }
        }

        public void startGame() {
            newFood();
            running = true;//Runs the game when true
            timer = new Timer(movespeed, this);// Speed of the game.
            timer.start();
        }

        public void restartGame() {// Sets all the values to the default value
            bodyParts = 3; 
            foodEaten = 0;
            direction = 'R';
            running = true;
            started = false;
            StartingsnakePosition();
            //x[0] = 0; //Would respawn snake at 0 0 but not needed anymore.
            //y[0] = 0;
            newFood();
            restartButton.setVisible(false);
            if (timer != null) {
                timer.stop();
            }
            timer.restart();
            repaint();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            draw(g);
        }

        public void draw(Graphics g) {
            if (running) {
                g.setColor(Color.orange); //Setś food colour
                g.fillOval(foodX, foodY, CELL_SIZE, CELL_SIZE); //Paint food

                for (int i = 0; i < bodyParts; i++) {// The entire snake
                    if (i == 0) {
                        g.setColor(Color.white);// Setś colour of the head.
                        g.fillRect(x[i], y[i], CELL_SIZE, CELL_SIZE);
                    } else {
                        g.setColor(new Color(180, 180, 180)); // Sets colour for the rest of the snake.
                        g.fillRect(x[i], y[i], CELL_SIZE, CELL_SIZE);
                    }
                }

                g.setColor(Color.white);
                g.setFont(new Font("Comic Sans", Font.BOLD, 40));
                FontMetrics metrics = getFontMetrics(g.getFont());
                g.drawString("Score: " + foodEaten, (WIDTH - metrics.stringWidth("Score: " + foodEaten)) / 2, g.getFont().getSize()); //Draws the score.
            } else {
                gameOver(g);// Triggered when collisons happen to stop game.
            }
        }

        public void gameOver(Graphics g) {
            
            g.setColor(Color.white);
            g.setFont(new Font("Comic Sans", Font.BOLD, 75));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Game Over", (WIDTH - metrics.stringWidth("Game Over")) / 2, HEIGHT / 2); //Draws the game over screen when you collide with edge or snake body.

            g.setColor(Color.white); //Sets score colour
            g.setFont(new Font("Comic Sans", Font.BOLD, 40)); //Gets font
            metrics = getFontMetrics(g.getFont()); 
            g.drawString("Score: " + foodEaten, (WIDTH - metrics.stringWidth("Score: " + foodEaten)) / 2, HEIGHT / 2 + g.getFont().getSize());//Draws the score and counts up when food eaten.

            restartButton.setVisible(true);  // show the restart button
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

        public void Food() {
            if ((x[0] == foodX) && (y[0] == foodY)) {
                bodyParts++;
                foodEaten++;
                newFood();
            }
        }

        public void Collisions() {
            for (int i = bodyParts; i > 0; i--) {
                if ((x[0] == x[i]) && (y[0] == y[i])) {//If you hit a body part of the snake it triggers the collision method
                    running = false;
                    //System.out.println(" Collision" );
                }
            }

            if (x[0] < 0) {
                running = false; // Collisions for the left side of the screen
                //System.out.println(" Collision" );
            }

            if (x[0] >= WIDTH) { // Collisions for the right side of the screen
                running = false;
                //System.out.println(" Collision" );
            }

            if (y[0] < 0) {
                running = false; // Collisions for the top of the screen
                //System.out.println(" Collision" );
            }

            if (y[0] >= HEIGHT) { // Collisions for the bottom of the screen
                running = false;
                //System.out.println(" Collision" );
            }
            if (!running) {
                timer.stop();
            
            }
        }

        public void newFood() {//Spawns food randomely within the playing area in only spawning on the rigded lines that the snake can travel on.
            foodX = random.nextInt((int) (WIDTH / CELL_SIZE)) * CELL_SIZE;
            foodY = random.nextInt((int) (HEIGHT / CELL_SIZE)) * CELL_SIZE;

        }

        @Override
        public void actionPerformed(ActionEvent e) {// Fixes  public class GamePanel extends JPanel implements ActionListener { having to be abstract.
            if (running) {
                move();
                Food();
                Collisions();

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



