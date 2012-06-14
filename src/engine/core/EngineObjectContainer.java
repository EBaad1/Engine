package engine.core;

import java.util.ArrayList;

public class EngineObjectContainer<EngineObject> extends ArrayList {

    public EngineObjectContainer() {
        super();
    }

    public EngineObjectContainer(int i) {
        super(i);
    }

    public void sort() 
    {
        
        //Sorting z indexes
        
        EngineObject tmp;
        
        int prevZ = 0;
        int curZ = 0;
        
        for (int i = 1; i < this.size(); i++) 
        {
            tmp = (EngineObject)this.get(i-1);
            prevZ = tmp.get();
            tmp = (EngineObject)this.get(i);
            curZ = tmp.zIndex;
        }
    }
}
