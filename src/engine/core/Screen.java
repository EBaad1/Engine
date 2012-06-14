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
    public double aspectRatio;
    
    public GraphicsDevice device;
    public World world;
    
    public Screen(GraphicsDevice device, Rectangle dimensions)
    {
        this.device = device;
        x = dimensions.x;
        y = dimensions.y;
        width = dimensions.width;
        height = dimensions.height;
        aspectRatio = (double)width / (double)height;
    }
    
    public void setWorld(World world)
    {
        this.world = world;
    }
    
    public int mapX(double x)
    {
        //return (int)Math.round((x - world.xMin) * width / (world.xMax - world.xMin));
        return (int)Math.round((x - (world.view.xOrigin - (world.view.width / 2))) * width / world.view.width);
    }
    
    public int mapY(double y)
    {
        //return (int)Math.round(height - (y - world.yMin) * height / (world.yMax - world.yMin));
        return (int)Math.round(height - (y - (world.view.yOrigin - world.view.height / 2)) * height / world.view.height);
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
        return (int)Math.round((dx - (world.view.xOrigin - world.view.width / 2)) * width / world.view.width);
    }
    
    public int mapDY(double dy)
    {
        return (int)Math.round(-1 * (dy - (world.view.yOrigin - world.view.height / 2)) * height / world.view.height);
    }
    
    public void setViewToScreenAspectRatio()
    {
        world.view.height = world.view.width / aspectRatio;
    }
}
