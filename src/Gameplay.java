import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private int[] snakexlength = new int[750];
    private int[] snakeylength = new int[750];

    private boolean right = false;
    private boolean left = false;
    private boolean up = false;
    private boolean down = false;

    private ImageIcon rightmouth;
    private ImageIcon leftmouth;
    private ImageIcon upmouth;
    private ImageIcon downmouth;
    private ImageIcon snakeimage;

    private int[] enemyxpos= {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,
            600,625,650,675,700,725,750,775,800,825,850};

    private int[] enemyypos = {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,
            600,625};

    private ImageIcon enemyimage;

    private Random random = new Random();

    private int xpos = random.nextInt(34);
    private int ypos = random.nextInt(23);

    private Timer timer;
    private int delay = 100;

    private int lengthofsnake = 3;
    private int moves = 0;
    private int score = 0;
    private boolean gameOver = false;
    private int code;

    private ImageIcon titleImage;

    public Gameplay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        timer = new Timer(delay,this);
        timer.start();
        spawnEnemy();
    }

    public void spawnEnemy(){
        int zhar = random.nextInt(7);
        if(zhar == 3){
            enemyimage = new ImageIcon("C:\\Users\\ma9so\\OneDrive\\Bureau\\Java\\SnakeGame\\src\\asset\\banana.jpg");
            code = 0;
        } else {
            enemyimage = new ImageIcon("C:\\Users\\ma9so\\OneDrive\\Bureau\\Java\\SnakeGame\\src\\asset\\apple.jpg");
            code = 1;
        }
        xpos = random.nextInt(34);
        ypos = random.nextInt(23);
    }

    public void paint(Graphics g){

        if(moves == 0){
            snakexlength[0] = 100;
            snakexlength[1] = 75;
            snakexlength[2] = 50;

            snakeylength[0] = 100;
            snakeylength[1] = 100;
            snakeylength[2] = 100;

            SoundPlayer.playLooped("C:\\Users\\ma9so\\OneDrive\\Bureau\\Java\\SnakeGame\\src\\asset\\ingame.wav");
        }


        //border of title image
        g.setColor(Color.WHITE);
        g.drawRect(24,10,851,55);

        titleImage = new ImageIcon("C:\\Users\\ma9so\\OneDrive\\Bureau\\Java\\SnakeGame\\src\\asset\\snaketitlee.png");
        titleImage.paintIcon(this,g,25,11);
        //border of gameplay
        g.setColor(Color.WHITE);
        g.drawRect(24,74,851,577);
        g.setColor(Color.BLACK);
        g.fillRect(25,75,850,575);

        //score board
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial",Font.PLAIN,14));
        g.drawString("Score: " +score,780,30);
        g.drawString("Length: " +lengthofsnake,780,50);

        rightmouth = new ImageIcon("C:\\Users\\ma9so\\OneDrive\\Bureau\\Java\\SnakeGame\\src\\asset\\right.png");
        rightmouth.paintIcon(this,g,snakexlength[0],snakeylength[0]);

        for(int i = 0 ; i < lengthofsnake ; i++){
            if( i == 0 && right){
                rightmouth = new ImageIcon("C:\\Users\\ma9so\\OneDrive\\Bureau\\Java\\SnakeGame\\src\\asset\\right.png");
                rightmouth.paintIcon(this,g,snakexlength[i],snakeylength[i]);
            }
            if( i == 0 && left){
                leftmouth = new ImageIcon("C:\\Users\\ma9so\\OneDrive\\Bureau\\Java\\SnakeGame\\src\\asset\\left.png");
                leftmouth.paintIcon(this,g,snakexlength[i],snakeylength[i]);
            }
            if( i == 0 && up){
                upmouth = new ImageIcon("C:\\Users\\ma9so\\OneDrive\\Bureau\\Java\\SnakeGame\\src\\asset\\up.png");
                upmouth.paintIcon(this,g,snakexlength[i],snakeylength[i]);
            }
            if( i == 0 && down){
                downmouth = new ImageIcon("C:\\Users\\ma9so\\OneDrive\\Bureau\\Java\\SnakeGame\\src\\asset\\down.png");
                downmouth.paintIcon(this,g,snakexlength[i],snakeylength[i]);
            }

            if(i != 0){
                snakeimage = new ImageIcon("C:\\Users\\ma9so\\OneDrive\\Bureau\\Java\\SnakeGame\\src\\asset\\snakebody.png");
                snakeimage.paintIcon(this,g,snakexlength[i],snakeylength[i]);
            }
        }

        //
        if (enemyimage != null) {
            enemyimage.paintIcon(this, g, enemyxpos[xpos], enemyypos[ypos]);
        }

        if(enemyxpos[xpos] == snakexlength[0] && enemyypos[ypos] == snakeylength[0] ){
            if(code == 1){
                SoundPlayer.playSound("C:\\Users\\ma9so\\OneDrive\\Bureau\\Java\\SnakeGame\\src\\asset\\eat.wav");
                score = score + 10;
            } else {
                SoundPlayer.playSound("C:\\Users\\ma9so\\OneDrive\\Bureau\\Java\\SnakeGame\\src\\asset\\specialeat.wav");
                score = score + 25;
            }

            lengthofsnake++;
            spawnEnemy();
        }

        for (int j = 1; j < lengthofsnake; j++){
            if(snakexlength[j] == snakexlength[0] && snakeylength[j] == snakeylength[0] ){
                right= false;
                left = false;
                up = false;
                down = false;
                gameOver = true;
                SoundPlayer.stopLooped();
                SoundPlayer.playSound("C:\\Users\\ma9so\\OneDrive\\Bureau\\Java\\SnakeGame\\src\\asset\\gameovertrom.wav");

                g.setColor(Color.RED);
                g.setFont(new Font("arial",Font.BOLD,50));
                g.drawString("Game Over !",300,300);
                g.setColor(Color.WHITE);
                g.setFont(new Font("arial",Font.BOLD,20));
                g.drawString("Press Space to RESTART",327,340);

            }
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!gameOver) {
            if (right) {
                for (int i = lengthofsnake - 1; i >= 0; i--) {
                    snakeylength[i + 1] = snakeylength[i];
                }
                for (int i = lengthofsnake - 1; i >= 0; i--) {
                    if (i == 0) {
                        snakexlength[i] = snakexlength[i] + 25;
                    } else {
                        snakexlength[i] = snakexlength[i - 1];
                    }
                    if (snakexlength[i] > 850) {
                        snakexlength[i] = 25;
                    }
                }
                repaint();
            }

            if (left) {
                for (int i = lengthofsnake - 1; i >= 0; i--) {
                    snakeylength[i + 1] = snakeylength[i];
                }
                for (int i = lengthofsnake - 1; i >= 0; i--) {
                    if (i == 0) {
                        snakexlength[i] = snakexlength[i] - 25;
                    } else {
                        snakexlength[i] = snakexlength[i - 1];
                    }
                    if (snakexlength[i] < 25) {
                        snakexlength[i] = 850;
                    }
                }
                repaint();
            }

            if (up) {
                for (int i = lengthofsnake - 1; i >= 0; i--) {
                    snakexlength[i + 1] = snakexlength[i];
                }
                for (int i = lengthofsnake - 1; i >= 0; i--) {
                    if (i == 0) {
                        snakeylength[i] = snakeylength[i] - 25;
                    } else {
                        snakeylength[i] = snakeylength[i - 1];
                    }
                    if (snakeylength[i] < 75) {
                        snakeylength[i] = 625;
                    }
                }
                repaint();
            }

            if (down) {
                for (int i = lengthofsnake - 1; i >= 0; i--) {
                    snakexlength[i + 1] = snakexlength[i];
                }
                for (int i = lengthofsnake - 1; i >= 0; i--) {
                    if (i == 0) {
                        snakeylength[i] = snakeylength[i] + 25;
                    } else {
                        snakeylength[i] = snakeylength[i - 1];
                    }
                    if (snakeylength[i] > 625) {
                        snakeylength[i] = 75;
                    }
                }
                repaint();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                moves++;
                if (!left) {
                    right = true;
                } else {
                    right = false;
                    left = true;
                }
                up = false;
                down = false;
            }

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                moves++;
                if (!right) {
                    left = true;
                } else {
                    left = false;
                    right = true;
                }
                up = false;
                down = false;
            }

            if (e.getKeyCode() == KeyEvent.VK_UP) {
                moves++;
                if (!down) {
                    up = true;
                } else {
                    up = false;
                    down = true;
                }
                right = false;
                left = false;
            }

            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                moves++;
                if (!up) {
                    down = true;
                } else {
                    down = false;
                    up = true;
                }
                right = false;
                left = false;
            }
        }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                gameOver = false;
                lengthofsnake = 3;
                moves = 0;
                score = 0;
                repaint();
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
