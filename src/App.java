import javax.swing.*;

public class App {
    public static void main(String[] args) {
        int boardSize = 600; // square game board

        JFrame frame = new JFrame("🐍 Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        SnakeGame snakeGame = new SnakeGame(boardSize, boardSize);
        frame.setContentPane(snakeGame);
        frame.pack();                    
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);

        snakeGame.requestFocus();          
        
    }
}

