package engine.object.properties;

import engine.core.EngineRuntime;
import engine.object.WorldObject;

public interface Property
{
    public void update(EngineRuntime runtime, WorldObject object);
}
