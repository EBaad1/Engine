package engine.test;

import java.awt.Color;

public class Triangle
{
    public double[] x; // {x1, x2, x3}
    public double[] y; // {y1, y2, y3}
    public Color color = Color.BLACK;
    
    public Triangle(double[] x, double[] y)
    {
        this.x = x;
        this.y = y;
    }
    
    public void scale(double factor)
    {
        double sumX = 0;
        double sumY = 0;
        for(int i=0;i<3;i++)
        {
            sumX += x[i];
            sumY += y[i];
        }
        double centerX = sumX / 3;
        double centerY = sumY / 3;
        for(int i=0;i<3;i++)
        {
            x[i] = centerX + (x[i] - centerX) * factor;
            y[i] = centerY + (y[i] - centerY) * factor;
        }
    }
}
