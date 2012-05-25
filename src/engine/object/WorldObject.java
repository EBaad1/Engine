package engine.object;

import engine.core.EngineGraphics;
import engine.core.EngineRuntime;
import engine.object.Primitive;
import engine.object.properties.Property;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Evan Baad
 */
public abstract class WorldObject
{  
    public ArrayList<Primitive> structure = new <Primitive>ArrayList(1);
    public ArrayList<Property> properties = new <Property>ArrayList(1);
    public Vertex position = new Vertex(0, 0);
    public double rotation = 0; //Radians
    
    public void setPosition(double x, double y)
    {
        position.x = x;
        position.y = y;
    }
    
    public void update(EngineRuntime runtime)
    {
        for(int i=0;i<properties.size();i++)
        {
            properties.get(i).update(runtime, this);
        }
    }
    
    public void draw(EngineGraphics eng)
    {
        for(int i=0;i<structure.size();i++)
        {
            structure.get(i).draw(eng, position, rotation);
        }
    }
}
