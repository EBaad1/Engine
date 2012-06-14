package engine.util;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class DefaultKeyBindings implements KeyListener
{
    public boolean EXIT_ON_ESCAPE = false;
    
    public void keyPressed(KeyEvent e)
    {
    }
    
    public void keyReleased(KeyEvent e)
    {
        if(EXIT_ON_ESCAPE && e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            System.exit(0);
        }           
    }
    
    public void keyTyped(KeyEvent e)
    {     
    }    
}
