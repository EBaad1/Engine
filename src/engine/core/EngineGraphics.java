package engine.core;

import java.awt.Graphics;
import java.awt.Graphics2D;
 
public class EngineGraphics
{
    public Graphics2D graphics;
    public Screen screen;
    
    public EngineGraphics(Screen screen)
    {
        this.screen = screen;
    }
    
    public void setGraphics(Graphics g)
    {
        this.graphics = (Graphics2D)g;
    }
}
