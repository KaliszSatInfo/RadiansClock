import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RadiansClock extends JPanel implements ActionListener {
    private Timer timer;
    private JLabel radiansLabel;

    public RadiansClock() {
        setLayout(new BorderLayout());

        radiansLabel = new JLabel("0:0:0", SwingConstants.CENTER);
        radiansLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(radiansLabel, BorderLayout.SOUTH);

        timer = new Timer(1000, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width, height) / 2 - 20;

        g.drawOval((width - radius * 2) / 2, (height - radius * 2) / 2, radius * 2, radius * 2);

        long currentTimeMillis = System.currentTimeMillis();
        long seconds = (currentTimeMillis / 1000) % 60;
        long minutes = (currentTimeMillis / (1000 * 60)) % 60;
        long hours = (currentTimeMillis / (1000 * 60 * 60)) % 12;

        double hourRadians = (hours + minutes / 60.0) * (2 * Math.PI / 12);
        double minuteRadians = (minutes + seconds / 60.0) * (2 * Math.PI / 60);
        double secondRadians = seconds * (2 * Math.PI / 60);

        radiansLabel.setText(String.format("%.2f : %.2f : %.2f", hourRadians, minuteRadians, secondRadians));

        drawHands(g, width, height, radius, hourRadians, minuteRadians, secondRadians);
        drawRadianMarkings(g, width / 2, height / 2, radius);
    }

    private void drawHands(Graphics g, int width, int height, int radius, double hourRadians, double minuteRadians, double secondRadians) {
        int centerX = width / 2;
        int centerY = height / 2;

        int hourHandLength = radius / 3;
        int hourX = centerX + (int) (hourHandLength * Math.cos(hourRadians - Math.PI / 2));
        int hourY = centerY + (int) (hourHandLength * Math.sin(hourRadians - Math.PI / 2));
        g.setColor(Color.BLACK);
        g.drawLine(centerX, centerY, hourX, hourY);

        int minuteHandLength = radius * 2 / 5;
        int minuteX = centerX + (int) (minuteHandLength * Math.cos(minuteRadians - Math.PI / 2));
        int minuteY = centerY + (int) (minuteHandLength * Math.sin(minuteRadians - Math.PI / 2));
        g.setColor(Color.BLUE);
        g.drawLine(centerX, centerY, minuteX, minuteY);

        int secondHandLength = radius * 2 / 3;
        int secondX = centerX + (int) (secondHandLength * Math.cos(secondRadians - Math.PI / 2));
        int secondY = centerY + (int) (secondHandLength * Math.sin(secondRadians - Math.PI / 2));
        g.setColor(Color.RED);
        g.drawLine(centerX, centerY, secondX, secondY);
    }

    private void drawRadianMarkings(Graphics g, int centerX, int centerY, int radius) {
        double[] radiansArray = {
                0,
                Math.PI / 6,
                Math.PI / 3,
                Math.PI / 2,
                2 * Math.PI / 3,
                5 * Math.PI / 6,
                Math.PI,
                7 * Math.PI / 6,
                4 * Math.PI / 3,
                3 * Math.PI / 2,
                5 * Math.PI / 3,
                11 * Math.PI / 6
        };

        String[] labels = {
                "0",
                "π/6",
                "π/3",
                "π/2",
                "2π/3",
                "5π/6",
                "π",
                "7π/6",
                "4π/3",
                "3π/2",
                "5π/3",
                "11π/6"
        };

        for (int i = 0; i < radiansArray.length; i++) {
            int x = centerX + (int) ((radius - 30) * Math.cos(radiansArray[i] - Math.PI / 2));
            int y = centerY + (int) ((radius - 30) * Math.sin(radiansArray[i] - Math.PI / 2));
            g.drawString(labels[i], x - 15, y + 5);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Radians Clock");
        RadiansClock clock = new RadiansClock();
        frame.add(clock);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
