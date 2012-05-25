package engine.core;

import engine.object.WorldObject;
import java.util.ArrayList;

public class World
{
    public double xMin , yMin, xMax, yMax;
    public ArrayList<WorldObject> objects = new <WorldObject>ArrayList();
    public View view;
    
    public World(double xMin, double yMin, double xMax, double yMax)
    {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }
    
    public void setView(View view)
    {
        this.view = view;
    }
    
    
}
