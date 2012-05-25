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
            structure.get(i).draw(eng, this);
        }
    }
}
