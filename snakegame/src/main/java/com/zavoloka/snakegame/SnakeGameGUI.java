package com.zavoloka.snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class SnakeGameGUI extends JFrame implements ActionListener, KeyListener {
    private static final int GRID_SIZE = 20;
    private static final int TILE_SIZE = 30;
    private static final int GAME_SPEED = 200;

    private Timer timer;
    private int snakeLength;
    private int[] snakeX, snakeY;
    private int foodX, foodY;
    private boolean isGameOver;
    private Direction direction;

    private final BufferedImage bufferImage;

    public SnakeGameGUI() {
        setTitle("Snake Game");
        setSize(GRID_SIZE * TILE_SIZE, GRID_SIZE * TILE_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        bufferImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        initGame();
        initUI();
    }

    private void initGame() {
        snakeX = new int[GRID_SIZE * GRID_SIZE];
        snakeY = new int[GRID_SIZE * GRID_SIZE];
        snakeLength = 1;
        isGameOver = false;
        direction = Direction.RIGHT;

        spawnFood();

        timer = new Timer(GAME_SPEED, this);
    }

    private void initUI() {
        addKeyListener(this);
        setFocusable(true);
    }

    private void spawnFood() {
        foodX = (int) (Math.random() * GRID_SIZE);
        foodY = (int) (Math.random() * GRID_SIZE);

        for (int i = 0; i < snakeLength; i++) {
            if (foodX == snakeX[i] && foodY == snakeY[i]) {
                spawnFood();
                return;
            }
        }
    }

    private void move() {
        for (int i = snakeLength - 1; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }

        switch (direction) {
            case UP:
                snakeY[0]--;
                break;
            case DOWN:
                snakeY[0]++;
                break;
            case LEFT:
                snakeX[0]--;
                break;
            case RIGHT:
                snakeX[0]++;
                break;
        }

        checkCollision();
        checkFood();
    }

    private void checkCollision() {
        if (snakeX[0] < 0 || snakeX[0] >= GRID_SIZE || snakeY[0] < 0 || snakeY[0] >= GRID_SIZE) {
            isGameOver = true;
        }

        for (int i = 1; i < snakeLength; i++) {
            if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                isGameOver = true;
                break;
            }
        }

        if (isGameOver) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Game Over!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void checkFood() {
        if (snakeX[0] == foodX && snakeY[0] == foodY) {
            snakeLength++;
            spawnFood();
        }
    }

    private void paintSnake(Graphics g) {
        for (int i = 0; i < snakeLength; i++) {
            if (i == 0) {
                g.setColor(Color.BLUE);
            } else {
                g.setColor(Color.YELLOW);
            }
            g.fillRect(snakeX[i] * TILE_SIZE, snakeY[i] * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }
    }

    private void paintFood(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(foodX * TILE_SIZE, foodY * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isGameOver) {
            move();
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE && !timer.isRunning()) {
            initGame();
            timer.start();
        } else if (key == KeyEvent.VK_W && direction != Direction.DOWN) {
            direction = Direction.UP;
        } else if (key == KeyEvent.VK_S && direction != Direction.UP) {
            direction = Direction.DOWN;
        } else if (key == KeyEvent.VK_A && direction != Direction.RIGHT) {
            direction = Direction.LEFT;
        } else if (key == KeyEvent.VK_D && direction != Direction.LEFT) {
            direction = Direction.RIGHT;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = bufferImage.createGraphics();
        super.paint(g2d);

        if (!isGameOver) {
            paintSnake(g2d);
            paintFood(g2d);
        }

        g.drawImage(bufferImage, 0, 0, this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SnakeGameGUI snakeGame = new SnakeGameGUI();
            snakeGame.setVisible(true);
        });
    }

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}
