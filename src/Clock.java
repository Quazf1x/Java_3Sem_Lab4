import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class Clock extends JPanel implements ActionListener {
    private Timer timer;
    private int hour;
    private int minute;
    private int second;

    public Clock() {
        timer = new Timer(1000, this);
        timer.start();
        setPreferredSize(new Dimension(600, 750));

        // Инициализация текущего времени
        Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY) % 12;
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);

        // Панель с кнопками
        JPanel btnPanel = new JPanel();
        JButton incHour = new JButton("Увеличить час");
        JButton decHour = new JButton("Уменьшить час");
        JButton incMinute = new JButton("Увеличить минуту");
        JButton decMinute = new JButton("Уменьшить минуту");

        incHour.addActionListener(e -> {
            hour = (hour + 1) % 12;
            repaint();
        });

        decHour.addActionListener(e -> {
            hour = (hour - 1 + 12) % 12;
            repaint();
        });

        incMinute.addActionListener(e -> {
            minute = (minute + 1) % 60;
            if (minute == 0) hour = (hour + 1) % 12;
            repaint();
        });

        decMinute.addActionListener(e -> {
            minute = (minute - 1 + 60) % 60;
            if (minute == 59) hour = (hour - 1 + 12) % 12;
            repaint();
        });

        btnPanel.add(incHour);
        btnPanel.add(decHour);
        btnPanel.add(incMinute);
        btnPanel.add(decMinute);

        setLayout(new BorderLayout());
        add(btnPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawClock(g);
    }

    private void drawClock(Graphics g) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(centerX, centerY) - 20;

        // Отрисовка циферблата
        g.setColor(Color.BLACK);
        g.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);

        // Отрисовка цифр
        for (int i = 0; i < 12; i++) {
            double angle = Math.toRadians(i * 30);
            int x = centerX + (int) (Math.cos(angle - Math.PI / 2) * (radius - 20));
            int y = centerY + (int) (Math.sin(angle - Math.PI / 2) * (radius - 20));
            g.drawString(String.valueOf(i == 0 ? 12 : i), x - 5, y + 5);
        }

        // Часовая
        drawHand(g, (hour + minute / 60.0) * 30, radius * 0.5,6,Color.BLACK);
        // Минутная
        drawHand(g, (minute + second / 60.0) * 6, radius * 0.8,4,Color.BLUE);
        // Секундная
        drawHand(g, second * 6, radius * 0.9, 2, Color.RED);
    }

    private void drawHand(Graphics g, double angle, double length, int width, Color color) {
        g.setColor(color);
        double radian = Math.toRadians(angle);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        int x = (int) (centerX + Math.cos(radian - Math.PI / 2) * length);
        int y = (int) (centerY + Math.sin(radian - Math.PI / 2) * length);

        g.fillRect(centerX, centerY, width, width);
        g.drawLine(centerX, centerY, x, y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        second = (second + 1) % 60;

        if (second == 0) {
            minute = (minute + 1) % 60;

            if (minute == 0) {
                hour = (hour + 1) % 12;
            }
        }

        repaint();
    }
}
