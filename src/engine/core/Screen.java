package engine.core;

import java.awt.GraphicsDevice;
import java.awt.Rectangle;

public class Screen
{
    //Class variables
    public int x;
    public int y;
    public int width;
    public int height;
    
    public GraphicsDevice device;
    public World grid;
    
    public Screen(GraphicsDevice device, Rectangle dimensions)
    {
        this.device = device;
        x = dimensions.x;
        y = dimensions.y;
        width = dimensions.width;
        height = dimensions.height;
    }
    
    public void setWorld(World grid)
    {
        this.grid = grid;
    }
    
    public int mapX(double x)
    {
        //return (int)Math.round((x - grid.xMin) * width / (grid.xMax - grid.xMin));
        return (int)Math.round((x - (grid.view.xOrigin - grid.view.width / 2)) * width / grid.view.width);
    }
    
    public int mapY(double y)
    {
        //return (int)Math.round(height - (y - grid.yMin) * height / (grid.yMax - grid.yMin));
        return (int)Math.round(height - (y - (grid.view.yOrigin - grid.view.height / 2)) * height / grid.view.height);
    }
    
    public int[] mapX(double[] x)
    {
        int[] screenX = new int[x.length];
        for(int i=0;i<x.length;i++)
        {
            screenX[i] = mapX(x[i]);
        }
        return screenX;
    }
    
    public int[] mapY(double[] y)
    {
        int[] screenY = new int[y.length];
        for(int i=0;i<y.length;i++)
        {
            screenY[i] = mapY(y[i]);
        }
        return screenY;
    }
    
    public int mapDX(double dx)
    {
        return (int)Math.round((dx - (grid.view.xOrigin - grid.view.width / 2)) * width / grid.view.width);
    }
    
    public int mapDY(double dy)
    {
        return (int)Math.round(-1 * (dy - (grid.view.yOrigin - grid.view.height / 2)) * height / grid.view.height);
    }    
}
