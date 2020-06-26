package lab2a;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("lab2");
                JPanel panel = new JPanel();
                final Lab g = new Lab(Color.green, 1000, 5);

                panel.add(g);
                frame.getContentPane().add(panel);
                final JButton button = new JButton("Start");
                button.addActionListener(new ActionListener() {
                    private boolean pulsing = false;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (pulsing) {
                            pulsing = false;
                            g.stop();
                            button.setText("Start");
                        } else {
                            pulsing = true;
                            g.start();
                            button.setText("Stop");
                        }
                    }
                });
                panel.add(button);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(g.getMatrixSize() * 100 + 400, g.getMatrixSize() * 100 + 100);
                frame.setVisible(true);
            }
        });
    }
}
