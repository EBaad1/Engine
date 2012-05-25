package engine.core;
 
public class EngineRuntime
{
    public Screen screen;
    public long engineStartTime;
    public long nextUpdateTime;
    public long lastUpdateTime;
    
    public EngineRuntime(Screen screen)
    {
        this.screen = screen;
        engineStartTime = System.currentTimeMillis();
        lastUpdateTime = engineStartTime;
    }
    
    public long getEngineRuntime()
    {
        return System.currentTimeMillis() - engineStartTime;
    }
    
    public long getUpdateTime()
    {
        return System.currentTimeMillis() - lastUpdateTime;
    }
    
    public void updateStart()
    {
        nextUpdateTime = System.currentTimeMillis();
    }
    
    public void updateEnd()
    {
        lastUpdateTime = nextUpdateTime;
    }
}