import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Clock");
        Clock clock = new Clock();

        frame.add(clock);
        frame.pack();
        frame.setVisible(true);
    }
}