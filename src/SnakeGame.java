import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    Tile food;
    Random random;

    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;

    SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;

        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<>();

        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 1;

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // draw grid
        g.setColor(Color.darkGray);
        for (int i = 0; i <= boardWidth / tileSize; i++) {
            g.drawLine(i * tileSize, 0, i * tileSize, boardHeight);
            g.drawLine(0, i * tileSize, boardWidth, i * tileSize);
        }

        // score
        g.setFont(new Font("Arial", Font.PLAIN, 16)); 
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("Game Over! Score: " + snakeBody.size() + "  (Press SPACE to restart)", tileSize - 10, tileSize);
        } else {
            g.setColor(Color.white);
            g.drawString("Score: " + snakeBody.size(), tileSize - 10, tileSize);
        }

        // draw food
        g.setColor(Color.red);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);

        // draw snake head
        g.setColor(Color.green);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        // draw snake body
        for (Tile snakePart : snakeBody) {
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }
    }

    public void placeFood() {
        int x = random.nextInt(boardWidth / tileSize);
        int y = random.nextInt(boardHeight / tileSize);
        food = new Tile(x, y);
    }

    public boolean collision(Tile t1, Tile t2) {
        return t1.x == t2.x && t1.y == t2.y;
    }

    public void move() {
        // move body
        for (int i = snakeBody.size() - 1; i > 0; i--) {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }
        if (snakeBody.size() > 0) {
            snakeBody.get(0).x = snakeHead.x;
            snakeBody.get(0).y = snakeHead.y;
        }

        // eat food
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        // move head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        // check collisions
        for (Tile part : snakeBody) {
            if (collision(snakeHead, part)) {
                gameOver = true;
            }
        }

        // wall collision
        if (snakeHead.x < 0 || snakeHead.x * tileSize >= boardWidth
                || snakeHead.y < 0 || snakeHead.y * tileSize >= boardHeight) {
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            move();
            repaint();
        } else {
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }

        // restart if game over
        if (gameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
            resetGame();
        }
    }

    public void resetGame() {
        snakeHead = new Tile(5, 5);
        snakeBody.clear();
        velocityX = 0;
        velocityY = 1;
        placeFood();
        gameOver = false;
        gameLoop.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
