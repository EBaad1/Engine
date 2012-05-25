package engine.object.properties;

import engine.core.EngineRuntime;
import engine.core.EngineGraphics;
import engine.object.WorldObject;
import engine.object.Vertex;

public class Motion implements Property
{
    public Vertex position = new Vertex(0, 0);
    public Vertex linearVelocity  = new Vertex(0, 0); // units/second
    public Vertex linearAcceleration = new Vertex(0, 0); // units/second/second
    public Vertex angularVelocity  = new Vertex(0, 0); // radians/second
    public Vertex angularAcceleration = new Vertex(0, 0); // radians/second/second
    public Vertex center = new Vertex(0, 0);
    
    public void setPosition(double px, double py)
    {
        position.x = px;
        position.y = py;
    }
    
    public void setLinearVelocity(double vx, double vy)
    {
        linearVelocity.x = vx;
        linearVelocity.y = vy;
    }
    
    public void setLinearAcceleration(double ax, double ay)
    {
        linearAcceleration.x = ax;
        linearAcceleration.y = ay;
    }
    
    public void setAngularVelocity(double vx, double vy)
    {
        angularVelocity.x = vx;
        angularVelocity.y = vy;
    }
    
    public void setAngularAcceleration(double ax, double ay)
    {
        angularAcceleration.x = ax;
        angularAcceleration.y = ay;
    }    
    
    public void update(EngineRuntime runtime, WorldObject object)
    {   
        double dt = (double)runtime.getUpdateTime() / 1000; //seconds
        
        //Xf = Xi + Vi * t + 0.5 * Ai * t ^ 2
        position.x = position.x + linearVelocity.x * dt + 0.5 * linearAcceleration.x * Math.pow(dt, 2);
        position.y = position.y + linearVelocity.y * dt + 0.5 * linearAcceleration.y * Math.pow(dt, 2);
        linearVelocity.x = linearVelocity.x + linearAcceleration.x * dt;
        linearVelocity.y = linearVelocity.y + linearAcceleration.y * dt;
    }
    
    public void graphics(EngineGraphics eng)
    {
        eng.graphics.translate(eng.screen.mapDX(position.x), eng.screen.mapDY(position.y));
    }
}
