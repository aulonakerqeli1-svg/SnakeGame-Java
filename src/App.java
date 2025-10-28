import javax.swing.*;

public class App {
    public static void main(String[] args) {
        int boardSize = 600; // square game board

        JFrame frame = new JFrame("🐍 Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        SnakeGame snakeGame = new SnakeGame(boardSize, boardSize);
        frame.setContentPane(snakeGame); // vendos panelin si content pane
        frame.pack();                      // madhësia merret nga preferredSize i panelit
        frame.setLocationRelativeTo(null); // qendro në mes të ekranit
        frame.setVisible(true);

        snakeGame.requestFocus();          // siguro fokusin tek paneli
    }
}

