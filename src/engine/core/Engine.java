package engine.core;

import engine.object.WorldObject;
import engine.util.*;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.GraphicsConfiguration;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

/**
 *
 * @author Evan Baad
 */
public class Engine
{
    
    //Class variables
    private Frame mainFrame;
    protected Screen screen;
    protected BufferStrategy buffer;
    protected EngineGraphics eng;
    protected EngineRuntime runtime;
    protected int maxFPS = 0;
    protected int sampleFPS = 0;
    protected boolean isRunning = false;
    
    //Class definitions
    private final int DOUBLE_BUFFERED = 2;
    private final int TRIPLE_BUFFERED = 3;
    
    public Engine()
    {
        //Getting hardware
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = environment.getDefaultScreenDevice();
        try {
            GraphicsConfiguration configuration = device.getDefaultConfiguration();    
            
            //Making frame
            mainFrame = new Frame(configuration);
            mainFrame.setUndecorated(true);
            mainFrame.setIgnoreRepaint(true);
            mainFrame.setResizable(false);
            
            //Setting fullscreen
            device.setFullScreenWindow(mainFrame);
            
            //Saving screen object (convenience class)
            screen = new Screen(device, mainFrame.getBounds());
            eng = new EngineGraphics(screen);
            
            
            //Setting display options
            mainFrame.createBufferStrategy(TRIPLE_BUFFERED); 
            buffer = mainFrame.getBufferStrategy();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void start()
    {
        Thread display = new Thread(new Display());
        display.start();
    }
    
    public Screen getScreen()
    {
        return screen;
    }
    
    //this is the boss function
    public void addKeyListener(KeyListener keyListener)
    {
        mainFrame.addKeyListener(keyListener);
    }
    
    public void end()
    {
        System.exit(0);
    }
    
    public void end(int errorCode)
    {
        System.exit(errorCode);
    }
    
    public void setDefaultKeyBindings()
    {
        DefaultKeyBindings dkb = new DefaultKeyBindings();
        dkb.EXIT_ON_ESCAPE = true;
        mainFrame.addKeyListener(dkb);
    }
    
    public class Display implements Runnable
    {
        public void run()
        {
            isRunning = true;
            runtime = new EngineRuntime(screen);
            
            //Temporary Variables
            WorldObject worldObject;
            //
            
            while(isRunning)
            {
                //Updating objects  
                runtime.updateStart();
                for(int i=0;i<screen.world.objects.size();i++)
                {
                    worldObject = (WorldObject)screen.world.objects.get(i);
                    worldObject.timedUpdate(runtime);
                }                
                runtime.updateEnd();
                
                //Drawing screen
                eng.setGraphics(buffer.getDrawGraphics());
                if(!buffer.contentsLost())
                {
                    //Default Background
                    eng.graphics.setColor(Color.WHITE);
                    eng.graphics.fillRect(0, 0, screen.width, screen.height);
                    
                    for(int i=0;i<screen.world.objects.size();i++)
                    {
                        worldObject = (WorldObject)screen.world.objects.get(i);
                        worldObject.draw(eng);
                    }
                    
                    buffer.show();
                    eng.graphics.dispose();
                }
                
                //Synching with framerate
                Toolkit.getDefaultToolkit().sync();
            }
        }
    }
    
}
