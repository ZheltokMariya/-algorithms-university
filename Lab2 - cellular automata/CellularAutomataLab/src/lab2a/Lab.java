package lab2a;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Lab extends JComponent implements ActionListener {
    private double scale;
    private Color color;
    private Timer timer;
    private int matrixSize;
    private int[][] liveMatrix;

    public Lab(Color color, int delay, int matrixSize) {
        this.matrixSize = 14; //matrixSize;
        enterMatrix();
        scale = 1.0;
        timer = new Timer(delay, this);
        this.color = color;
        setPreferredSize(new Dimension(this.matrixSize * 100, this.matrixSize * 100));
    }

    public int getMatrixSize() {
        return matrixSize;
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(-10, -10, 2000, 2000);
        int c = 450;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        int width = matrixSize * 100;
        int height = matrixSize * 100;
        g.fillRect(0, 0, width, height);
        g2d.setColor(Color.black);
        g2d.drawRect(0, 0, width + 1, height + 1);
        for (int i = 1; i < matrixSize; i++) { // рисование сетки
            g2d.drawLine(0, 100 * i, 100 * 100, 100 * i); //hor lines
            g2d.drawLine(100 * i, 0, 100 * i, 100 * 100); //ver lines
        }
        g2d.setColor(Color.GRAY);
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) { // рисование текущего состояния
                if (liveMatrix[i][j] == 1)
                    g2d.fillRect(i * 100, j * 100, 100, 100);
            }
        }
        checkAliveCellsInf();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        g2d.scale(scale, scale);
    }

    private int getValueAtInf(int x, int y) {
        if(x<0) x=matrixSize-1;
        if(x==matrixSize) x=0;
        if(y==matrixSize) y=0;
        if(y<0) y=matrixSize-1;
        return liveMatrix[x][y];
    }

    private void checkAliveCellsInf() {
        int[][] newState = liveMatrix.clone();
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                int nearAliveCount = 0;
                if (getValueAtInf(i + 1, j + 1) == 1) nearAliveCount++;
                if (getValueAtInf(i + 1, j - 1) == 1) nearAliveCount++;
                if (getValueAtInf(i - 1, j + 1) == 1) nearAliveCount++;
                if (getValueAtInf(i - 1, j - 1) == 1) nearAliveCount++;
                if (getValueAtInf(i, j - 1) == 1) nearAliveCount++;
                if (getValueAtInf(i, j + 1) == 1) nearAliveCount++;
                if (getValueAtInf(i + 1, j) == 1) nearAliveCount++;
                if (getValueAtInf(i - 1, j) == 1) nearAliveCount++;

                if (nearAliveCount <= 1) newState[i][j] = 0;
                else if (nearAliveCount >= 3) newState[i][j] = 0;
                else newState[i][j] = 1;
            }
        }
        liveMatrix = newState;
    }

    private void enterMatrix() {
        this.liveMatrix = new int[][]{
                {1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0},
                {1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0},
                {1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
    }
}
