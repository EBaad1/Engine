package engine.core;

public class View
{
    public double xOrigin, yOrigin, width, height;
    
    public View(double width, double height)
    {
        this.width = width;
        this.height = height;
    }
    
    public View(double xOrigin, double yOrigin, double width, double height)
    {
        this.xOrigin = xOrigin;
        this.yOrigin = yOrigin;
        this.width = width;
        this.height = height;
    }    
    
    public void setOrigin(double xOrigin, double yOrigin)
    {
        this.xOrigin = xOrigin;
        this.yOrigin = yOrigin;
    }
}
