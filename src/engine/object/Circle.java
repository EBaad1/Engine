package engine.object;

public class Circle extends WorldObject
{
    
    public Circle()
    {
        
    }
    
    public Circle(double centerX, double centerY, double radius)
    {
        set(centerX, centerY, radius);
    }
    
    public void set(double centerX, double centerY, double radius)
    {
        int slices = 100;
        
        Primitive p = new Primitive(Primitive.TRIANGLE_FAN);
        p.addVertex(centerX, centerY);
        for(double theta=0;theta<=2*Math.PI+0.00001;theta=theta + (2*Math.PI)/(double)slices)
        {
            p.addVertex(centerX+Math.cos(theta)*radius, centerY+Math.sin(theta)*radius);
        }
        structure.add(p);
    }
}
