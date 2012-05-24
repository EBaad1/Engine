package engine.object;

import engine.core.EngineGraphics;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Evan Baad
 */
public abstract class GridObject
{  
    ArrayList<Primitive> primitives = new <Primitive>ArrayList(1);
    
    public void draw(EngineGraphics eng)
    {
        for(int i=0;i<primitives.size();i++)
        {
            primitives.get(i).draw(eng);
        }
    }
}
