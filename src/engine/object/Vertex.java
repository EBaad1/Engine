package engine.object;

public class Vertex
{
    public double x;
    public double y;
    
    public Vertex()
    {
    }
    
    public Vertex(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public double distance(Vertex v)
    {
        return Math.sqrt(Math.pow(v.x - this.x, 2) + Math.pow(v.y - this.y, 2));
    }
    
    public void set(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public void move(double dx, double dy)
    {
        x += dx;
        y += dy;
    }
    
    public String toString()
    {
        return "( "+x+" , "+y+" )";
    }
}
