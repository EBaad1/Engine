package engine.core;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.GraphicsConfiguration;
import java.awt.Toolkit;
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
    protected int maxFPS = 0;
    protected int sampleFPS = 0;
    protected boolean isRunning = false;
    
    //Class definitions
    private final int DOUBLE_BUFFERED = 2;
    
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
            mainFrame.createBufferStrategy(DOUBLE_BUFFERED); 
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
    
    public void end()
    {
        System.exit(0);
    }
    
    public void end(int errorCode)
    {
        System.exit(errorCode);
    }
    
    public class Display implements Runnable
    {
        public void run()
        {
            isRunning = true;
            while(isRunning)
            {
                eng.setGraphics(buffer.getDrawGraphics());
                if(!buffer.contentsLost())
                {
                    //Default Background
                    eng.graphics.setColor(Color.WHITE);
                    eng.graphics.fillRect(0, 0, screen.width, screen.height);
                    
                    int nObjects = screen.grid.objects.size();
                    for(int i=0;i<nObjects;i++)
                    {
                        screen.grid.objects.get(i).draw(eng);
                    }
                    
                    buffer.show();
                    eng.graphics.dispose();
                }
                Toolkit.getDefaultToolkit().sync();
            }
        }
        
    }
    
}
