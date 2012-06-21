package engine.core;

import engine.object.Bounds;

public abstract class EngineObject
{
    public int zIndex = 0;
    public boolean isVisible = true;
    public abstract Bounds getBounds();
}
