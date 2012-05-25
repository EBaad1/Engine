package engine.object.properties;

import engine.core.EngineRuntime;
import engine.object.WorldObject;
import engine.object.Vertex;

public class Motion implements Property
{
    public Vertex velocity  = new Vertex(0, 0); // units/second
    public Vertex acceleration = new Vertex(0, 0); // units/second/second
    
    public void setVelocity(double vx, double vy)
    {
        velocity.x = vx;
        velocity.y = vy;
    }
    
    public void setAcceleration(double ax, double ay)
    {
        acceleration.x = ax;
        acceleration.y = ay;
    }
    
    public void update(EngineRuntime runtime, WorldObject object)
    {   
        double dt = (double)runtime.getUpdateTime() / 1000; //seconds
        
        //Xf = Xi + Vi * t + 0.5 * Ai * t ^ 2
        object.position.x = object.position.x + velocity.x * dt + 0.5 * acceleration.x * Math.pow(dt, 2);
        object.position.y = object.position.y + velocity.y * dt + 0.5 * acceleration.y * Math.pow(dt, 2);
        velocity.x = velocity.x + acceleration.x * dt;
        velocity.y = velocity.y + acceleration.y * dt;
    }
}
