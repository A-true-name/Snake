import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class SnakeGame extends JFrame {
    static final int WIDTH = 1016; //Window size.
    static final int HEIGHT = 1022; //Window size.

    private GamePanel gamePanel;
    private JButton startButton;

    public SnakeGame() {
        setTitle("Snake Game");
        setSize(WIDTH, HEIGHT); // set window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel = new GamePanel();
        add(gamePanel);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu instructionsMenu = new JMenu("Instructions");
        menuBar.add(instructionsMenu);

        JMenuItem Instructions = new JMenuItem("Instructions");
        Instructions.addActionListener(e -> JOptionPane.showMessageDialog(this,
                    "Use arrow or wasd keys to move the snake.\n" +
                    "Eat food to grow and gain score.\n" +
                    "Avoid colliding with the walls and the snake's own body.\n" +
                    "Press 'P' to pause the game and 'R' to resume.",
                    "Instructions",
                    JOptionPane.INFORMATION_MESSAGE));
        instructionsMenu.add(Instructions);


        startButton = new JButton("Start Game");
        startButton.setBounds(WIDTH / 2 - 75, HEIGHT / 2 - 15, 150, 30);
        startButton.setFocusable(false);
        startButton.addActionListener(e -> {
                    gamePanel.startGame();
                    startButton.setVisible(false);
            });
        gamePanel.setLayout(null);
        gamePanel.add(startButton);

        setVisible(true);
    }

    public static void main(String[] args) {
        new SnakeGame();
    }

    public class GamePanel extends JPanel implements ActionListener {

        static final int WIDTH = 1000;  // Used for every part of the code that needs WIDTH apart from the initial window.
        static final int HEIGHT = 960; // Used for every part of the code that needs HEIGHT apart from the initial window.
        static final int CELL_SIZE = 40; // Size of a square inside the game.
        static final int NUMBER_OF_UNITS = (WIDTH * HEIGHT) / (CELL_SIZE * CELL_SIZE);

        // hold x and y coordinates for body parts of the snake.
        final int x[] = new int[NUMBER_OF_UNITS];
        final int y[] = new int[NUMBER_OF_UNITS];

        private int bodyParts = 3;//Starting length of snake.

        //  food dimensions.
        int length = 10;
        int Score;
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
            restartButton.setBounds(WIDTH / 2 - 50, HEIGHT / 2 + 50, 100, 30);  // position of the button.
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

            //startGame(); //Starts game when window is opened.
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
            restartButton.setVisible(false);
            newFood();
            //bodyParts = 3;
            running = true;//Runs the game when true
            timer = new Timer(movespeed, this);// Speed of th9e game.
            timer.start();
            repaint();
            started = true;

        }

        public void restartGame() {// Sets all the values to the default value
            bodyParts = 3; 
            Score = 0;
            direction = 'R';
            running = true;
            started = true;
            StartingsnakePosition();
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
                g.drawString("Score: " + Score, (WIDTH - metrics.stringWidth("Score: " + Score)) / 2, g.getFont().getSize()); //Draws the score.
            } else {
                if (started == true) { //Once game has been started gameover screen is available.

                    gameOver(g);}

            }
        }

        public void gameOver(Graphics g) {
            running = false;

            g.setColor(Color.white);
            g.setFont(new Font("Comic Sans", Font.BOLD, 75));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Game Over", (WIDTH - metrics.stringWidth("Game Over")) / 2, HEIGHT / 2); //Draws the game over screen when you collide with edge or snake body.

            g.setColor(Color.white); //Sets score colour
            g.setFont(new Font("Comic Sans", Font.BOLD, 40)); //Gets font
            metrics = getFontMetrics(g.getFont()); 
            g.drawString("Score: " + Score, (WIDTH - metrics.stringWidth("Score: " + Score)) / 2, HEIGHT / 2 + g.getFont().getSize());//Draws the score and counts up when food eaten.

            bodyParts = 3; 
            StartingsnakePosition();

            restartButton.setVisible(true);  // show the restart button
        }

        public void pauseGame() {
            running = false;
            if (timer != null) {
                timer.stop();
            }
        }

        public void resumeGame() {
            running = true;
            if (timer != null) {
                timer.start();
            }
        }

        public void move() {// Makes the segment of the snake follow the segment infront of it untill it reaches the head which you controll.
            for (int i = bodyParts; i > 0; i--) {
                x[i] = x[i - 1];
                y[i] = y[i - 1];
            }

            switch (direction) { //Moves in a grid.
                case 'U':
                    y[0] = y[0] - CELL_SIZE; //Moves snake up.
                    break;
                case 'D':
                    y[0] = y[0] + CELL_SIZE; //Moves snake down.
                    break;
                case 'L':
                    x[0] = x[0] - CELL_SIZE; //Moves snake Left.
                    break;
                case 'R':
                    x[0] = x[0] + CELL_SIZE; //Moves snake Right.
                    break;
            }
        }

        public void Food() {
            if ((x[0] == foodX) && (y[0] == foodY)) {
                bodyParts++;//Adds body part.
                Score++; //Adds 1 point of score.
                newFood(); //Spawns new food.
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
                    case KeyEvent.VK_A:

                        if (direction != 'R') {
                            direction = 'L';

                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:

                        if (direction != 'L') {
                            direction = 'R';
                        }
                        break;
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:

                        if (direction != 'D') {
                            direction = 'U';
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:

                        if (direction != 'U') {
                            direction = 'D';
                        }
                        break;
                    case KeyEvent.VK_P: // Press p to trigger pause Construstor.
                        pauseGame();
                        break;
                    case KeyEvent.VK_R: //Press R to trigger the resume function.
                        resumeGame();
                        break;

                }
            }

        }
    }

}


