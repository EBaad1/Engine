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
    public double angle = 0;
    public double angularVelocity  = 0;
    public double angularAcceleration = 0;
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
    
    public void setCenter(double x, double y)
    {
        center.x = x;
        center.y = y;
    }
    
    public void setAngle(double angle)
    {
        this.angle = angle;
    }
    
    public void setAngularVelocity(double rads)
    {
        angularVelocity = rads;
    }
    
    public void setAngularAcceleration(double radss)
    {
        angularAcceleration = radss;
    }    
    
    public void update(EngineRuntime runtime, WorldObject object)
    {   
        double dt = (double)runtime.getUpdateTime() / 1000; //seconds
        
        //Xf = Xi + Vi * t + 0.5 * Ai * t ^ 2
        position.x = position.x + linearVelocity.x * dt + 0.5 * linearAcceleration.x * Math.pow(dt, 2);
        position.y = position.y + linearVelocity.y * dt + 0.5 * linearAcceleration.y * Math.pow(dt, 2);
        linearVelocity.x = linearVelocity.x + linearAcceleration.x * dt;
        linearVelocity.y = linearVelocity.y + linearAcceleration.y * dt;
        
        angle = angle + angularVelocity * dt + 0.5 * angularAcceleration * Math.pow(dt, 2);
        angularVelocity = angularVelocity + angularAcceleration * dt; 
    }
    
    public void graphics(EngineGraphics eng)
    {
        eng.graphics.translate(eng.screen.mapDX(position.x), eng.screen.mapDY(position.y));
        eng.graphics.rotate(angle, center.x + position.x, center.y + position.y);
    }
}
