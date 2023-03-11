import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class code extends JPanel implements KeyListener {
    
    private static final long serialVersionUID = 1L;
    private JFrame frame;
    private ArrayList<Car> cars;
    private int winner;
    private boolean gameStarted;
    
    public code() {
        cars = new ArrayList<Car>();
        cars.add(new Car(Color.RED, 0, 30));
        cars.add(new Car(Color.BLUE, 0, 90));
        cars.add(new Car(Color.GREEN, 0, 150));
        winner = -1;
        gameStarted = false;
        frame = new JFrame("Racing Game");
        frame.setSize(800, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        frame.add(this);
        frame.setVisible(true);
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Car car : cars) {
            car.draw(g);
        }
        if (winner != -1) {
            g.setColor(Color.BLACK);
            g.drawString("Winner: Car " + (winner + 1), 350, 200);
        }
    }
    
    public void keyPressed(KeyEvent e) {
        if (!gameStarted) {
            gameStarted = true;
            Thread t = new Thread(new MoveCars());
            t.start();
        }
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP:
                cars.get(0).move();
                break;
            case KeyEvent.VK_DOWN:
                cars.get(1).move();
                break;
            case KeyEvent.VK_ENTER:
                cars.get(2).move();
                break;
        }
        checkWinner();
        repaint();
    }
    
    public void keyTyped(KeyEvent e) {}
    
    public void keyReleased(KeyEvent e) {}
    
    public void checkWinner() {
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getX() >= 700) {
                winner = i;
            }
        }
    }
    
    private class MoveCars implements Runnable {
        public void run() {
            while (winner == -1) {
                for (Car car : cars) {
                    car.move();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                checkWinner();
                repaint();
            }
        }
    }
    
    public static void main(String[] args) {
        new code();
    }

}

class Car {
    
    private Color color;
    private int x;
    private int y;
    
    public Car(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }
    
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, 50, 30);
    }
    
    public void move() {
        x += (int) (Math.random() * 10);
    }
    
    public int getX() {
        return x;
    }
    
}