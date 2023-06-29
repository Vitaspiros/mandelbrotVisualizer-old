import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class Plotter extends JPanel {
    int HEIGHT;
    int WIDTH;

    final int POINT_RADIUS = 1;
    final int CALC_TIMES = 100; // how many times a single pixel is calculated

    double mappingNum = 2;
    double offsetX = 0;
    double offsetY = 0;

    // the magic function of the mandelbrot set
    public Complex magicFunction(Complex z, Complex c) {
        return z.square().add(c);
    }

    // checks CALC_TIMES a pixel to see if it is in the Mandelbrot set
    public boolean calculatePixel(double x, double y) {
        Complex z = Complex.ZERO;
        Complex prevZ = Complex.ZERO;
        for (int i=0;i<CALC_TIMES;i++) {
            prevZ = z;
            z = magicFunction(z, new Complex(x,y));
            if (z.isNaN() || z.equals(prevZ)) return false;
        }
        return true;
    }

    public Plotter(int width, int height) {
        HEIGHT=height;
        WIDTH=width;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.GRAY);
        drawAxes(g);

        //drawPoint(g,1,1,Color.BLUE);
        drawFractal(g);

        //calculatePixel(-1, 0);
    }

    public void drawAxes(Graphics g) {
        g.setColor(Color.RED);
        g.drawLine((int)(HEIGHT/2),0,(int)(HEIGHT/2),HEIGHT);
        g.drawLine(0,(int)(WIDTH/2),WIDTH,(int)(WIDTH/2));
    }

    public void drawPoint(Graphics g,double x, double y, Color color, double offsetX, double offsetY) {
        int x2=(int)map(x+offsetX,-mappingNum,mappingNum,-(WIDTH/2),WIDTH/2);
        int y2=(int)map(y+offsetY,-mappingNum,mappingNum,-(WIDTH/2),HEIGHT/2);
        //System.out.println(x+" "+y+"   "+x2+" "+y2);

        g.setColor(color);
        g.fillRect(WIDTH/2-x2, HEIGHT/2-y2, POINT_RADIUS, POINT_RADIUS);

    }

    public double map(double value, double leftMin, double leftMax, double rightMin, double rightMax) {
        double leftSpan = leftMax - leftMin;
        double rightSpan = rightMax - rightMin;

        // Convert the left range into a 0-1 range (float)
        double valueScaled = (double)(value - leftMin) / (double)(leftSpan);

        // Convert the 0-1 range into a value in the right range.
        return rightMin + (valueScaled * rightSpan);
    }

    // Uses calculatePixel to calculate all pixels
    public void drawFractal(Graphics g) {
        for (int x=0;x<WIDTH-1;x++) {
            for (int y=0;y<HEIGHT-1;y++) {
                int x2=WIDTH/2-x-1;
                int y2=HEIGHT/2-y-1;
                if (calculatePixel(map(x2,-(WIDTH/2), WIDTH/2-1, -mappingNum, mappingNum), map(y2,-(HEIGHT/2), HEIGHT/2-1, -mappingNum, mappingNum))) drawPoint(g, map(x2,-(WIDTH/2), WIDTH/2, -mappingNum, mappingNum), map(y2,-(HEIGHT/2), HEIGHT/2, -mappingNum, mappingNum), Color.BLACK, offsetX, offsetY);
            }
        }

        System.out.println("DONE!");
    }

    public void zoom(MouseEvent e) {
        if (e.getButton()==MouseEvent.BUTTON1) {
            int x=e.getX();
            int y=e.getY();

            offsetX+=map(x, 0, WIDTH, -mappingNum, mappingNum) / (mappingNum / 2);
            offsetY+=map(y, 0, HEIGHT, -mappingNum, mappingNum) / (mappingNum / 2);

            mappingNum/=2;
        } else if (e.getButton()==MouseEvent.BUTTON3) {
            mappingNum = 2;

            offsetX = 0;
            offsetY = 0;
        }

        repaint();
    }
}
