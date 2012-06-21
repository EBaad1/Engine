package engine.object;

public class Bounds 
{
    public double xMin=Double.MAX_VALUE, yMin=Double.MAX_VALUE, xMax=Double.MIN_VALUE, yMax=Double.MIN_VALUE;
    
    public Bounds()
    {
        
    }
    
    public Bounds(double xMin, double yMin, double xMax, double yMax)
    {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }
    
    public double getWidth()
    {
        return xMax - xMin;
    }
    
    public double getHeight()
    {
        return yMax - yMin;
    }
}
