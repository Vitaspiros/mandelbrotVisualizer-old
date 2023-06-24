import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Plotter extends JPanel {
    int HEIGHT;
    int WIDTH;

    final int POINT_RADIUS = 1;
    final int CALC_TIMES = 100; // how many times a single pixel is calculated

    // the magic function of the mandelbrot set
    public Complex magicFunction(Complex z, Complex c) {
        return z.square().add(c);
    }

    // checks CALC_TIMES a pixel to see if it is in the Mandelbrot set
    public boolean calculatePixel(double x, double y) {
        Complex z = Complex.ZERO;
        MySet<Complex> set = new MySet<>();
        Complex prevZ = Complex.ZERO;
        for (int i=0;i<CALC_TIMES;i++) {
            prevZ = z;
            z = magicFunction(z, new Complex(x,y));
            if (z.isNaN() || z.equals(prevZ)) break;
            set.add(z);
        }
        //if (x == -1 && y == 0) System.out.println(set.size());
        if (set.size()<=CALC_TIMES-20) return true;
        return false;
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

    public void drawPoint(Graphics g,double x, double y, Color color) {
        int x2=(int)map(x,-2,2,-(WIDTH/2),WIDTH/2);
        int y2=(int)map(y,-2,2,-(WIDTH/2),HEIGHT/2);
        //System.out.println(x+" "+y+"   "+x2+" "+y2);

        g.setColor(color);
        //g.fillArc(WIDTH/2-x2-POINT_RADIUS/2,HEIGHT/2-y2-POINT_RADIUS/2,POINT_RADIUS,POINT_RADIUS,0,360);
        g.fillRect(WIDTH/2-x2, HEIGHT/2-y2, POINT_RADIUS, POINT_RADIUS);

    }

    public double map(double value, int leftMin, int leftMax, int rightMin, int rightMax) {
        int leftSpan = leftMax - leftMin;
        int rightSpan = rightMax - rightMin;

        // Convert the left range into a 0-1 range (float)
        double valueScaled = (double)(value - leftMin) / (double)(leftSpan);

        // Convert the 0-1 range into a value in the right range.
        return rightMin + (valueScaled * rightSpan);
    }

    // Uses calculatePixel to calculate all pixels
    public void drawFractal(Graphics g) {
        for (int x=0;x<WIDTH;x++) {
            for (int y=0;y<HEIGHT;y++) {
                int x2=WIDTH/2-x;
                int y2=HEIGHT/2-y;
                if (!calculatePixel(map(x2,-(WIDTH/2), WIDTH/2, -2, 2), map(y2,-(HEIGHT/2), HEIGHT/2, -2, 2))) drawPoint(g, map(x2,-(WIDTH/2), WIDTH/2, -2, 2), map(y2,-(HEIGHT/2), HEIGHT/2, -2, 2), Color.BLACK);
            }
        }

        System.out.println("DONE!");
    }
}
