package engine.object;

import engine.core.EngineObject;
import engine.core.EngineGraphics;
import engine.core.EngineRuntime;
import engine.core.EngineObjectContainer;
import engine.object.Primitive;
import engine.object.properties.Property;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Evan Baad
 */
public abstract class WorldObject extends EngineObject
{  
    public EngineObjectContainer structure = new EngineObjectContainer(1);
    
    public Vertex position = new Vertex(0, 0); //units
    public Vertex velocity = new Vertex(0, 0); //units / s
    public Vertex acceleration = new Vertex(0, 0); //units / s / s
    public Vertex centerOfRotation = new Vertex(0, 0); //units
    public double angularPosition = 0; //radians
    public double angularVelocity = 0; //radians / s
    public double angularAcceleration = 0; //radians / s / s
    
    public void timedUpdate(EngineRuntime runtime)
    {
        double dt = (double)runtime.getUpdateTime() / 1000; //seconds
        
        //Xf = Xi + Vi * t + 0.5 * Ai * t ^ 2
        position.x = position.x + velocity.x * dt + 0.5 * acceleration.x * Math.pow(dt, 2);
        position.y = position.y + velocity.y * dt + 0.5 * acceleration.y * Math.pow(dt, 2);
        velocity.x = velocity.x + acceleration.x * dt;
        velocity.y = velocity.y + acceleration.y * dt;
        
        angularPosition = angularPosition + angularVelocity * dt + 0.5 * angularAcceleration * Math.pow(dt, 2);
        angularVelocity = angularVelocity + angularAcceleration * dt;         
    }
    
    public void draw(EngineGraphics eng)
    {
        Primitive tmp;
        for(int i=0;i<structure.size();i++)
        {
            tmp = (Primitive)structure.get(i);
            tmp.draw(eng, this);
        }
    }
    
    public void scale(double factor)
    {
        double centerX = 0, centerY = 0;
        double xMin = 0, xMax = 0, yMin = 0, yMax = 0, tX, tY;
        
        Primitive tmp;
        
        for(int i=0;i<structure.size();i++)
        {
            tmp = (Primitive)structure.get(i);
            for(int j=0;j<tmp.vertices.size();j++)
            {
                tX = tmp.vertices.get(j).x;
                tY = tmp.vertices.get(j).y;
                
                if(tX < xMin)
                {
                    xMin = tX;
                }
                if(tX > xMax)
                {
                    xMax = tX;
                }
                if(tY < yMin)
                {
                    yMin = tY;
                }
                if(tY > yMax)
                {
                    yMax = tY;
                }
            }
        }
        
        centerX = (xMax - xMin) / 2;
        centerY = (yMax - yMin) / 2;
        
        for(int i=0;i<structure.size();i++)
        {
            tmp = (Primitive)structure.get(i);
            for(int j=0;j<tmp.vertices.size();j++)
            {
                double prevX = tmp.vertices.get(j).x;
                double prevY = tmp.vertices.get(j).y;
                
                tmp.vertices.set(j, new Vertex((prevX - centerX)*factor+centerX, (prevY - centerY)*factor+centerY));
            }
        }        
    }
    
}
