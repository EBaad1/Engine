package engine.object;

public class PrimitiveObject extends GridObject
{
    public PrimitiveObject(Primitive p)
    {
        setPrimitive(p);
    }
    
    public void setPrimitive(Primitive p)
    {
        if(primitives.isEmpty())
        {
            primitives.add(p);
        }else{
            primitives.set(0, p);
        }
    }
}
