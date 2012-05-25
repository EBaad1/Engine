package engine.object;

import engine.object.properties.Property;
import engine.object.properties.Motion;

public class PrimitiveObject extends WorldObject
{
    public Motion motion;
    
    public PrimitiveObject(Primitive p)
    {
        setPrimitive(p);
        motion = new Motion();
        properties.add((Property)motion);
    }
    
    public void setPrimitive(Primitive p)
    {
        if(structure.isEmpty())
        {
            structure.add(p);
        }else{
            structure.set(0, p);
        }
    }
}
