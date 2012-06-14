package engine.object;


public class PrimitiveObject extends WorldObject
{   
    public PrimitiveObject(Primitive p)
    {
        setPrimitive(p);
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
