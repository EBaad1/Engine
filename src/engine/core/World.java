package engine.core;

import engine.object.WorldObject;
import java.util.ArrayList;

public class World
{
    public double xMin , yMin, xMax, yMax;
    public EngineObjectContainer objects = new EngineObjectContainer();
    public View view;
    
    public World(double xMin, double yMin, double xMax, double yMax)
    {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        view = new View(((xMax - xMin) / 2) + xMin, ((yMax - yMin) / 2) + yMin, (xMax - xMin), (yMax - yMin));
    }
    
    public void setView(View view)
    {
        this.view = view;
    }
    
    public void setWorldView()
    {
        view = new View(((xMax - xMin) / 2) + xMin, ((yMax - yMin) / 2) + yMin, (xMax - xMin), (yMax - yMin));
    }
    
    
}
