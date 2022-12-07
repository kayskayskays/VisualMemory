package main;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static JLabel pointCounter = new JLabel("0");
    public static JButton submit = new JButton("Submit");

    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Visual Memory");

        GamePanel gamePanel = new GamePanel();

        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }

}