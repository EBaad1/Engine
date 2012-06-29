package engine.object;

public class Square extends WorldObject
{
    public Square()
    {
        
    }
    
    public Square(double x, double y, double length)
    {
        set(x, y, length);
    }
    
    public void set(double x, double y, double length)
    {
        Primitive p = new Primitive(Primitive.QUADS);
        p.addVertex(x, y);
        p.addVertex(x+length, y);
        p.addVertex(x+length, y-length);
        p.addVertex(x, y-length);
        structure.add(p);
    }
}
