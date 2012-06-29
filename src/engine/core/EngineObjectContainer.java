package engine.core;

public class EngineObjectContainer
{
    private EngineObject[] objects;
    
    public final int UNAVAILABLE = -1;
    
    public EngineObjectContainer()
    {
        objects = new EngineObject[10];
    }
    
    public EngineObjectContainer(int i)
    {
        objects = new EngineObject[i];
    }
    
    public void add(EngineObject obj)
    {
        int index = firstAvailableIndex();
        if(index == UNAVAILABLE)
        {
            int oldLength = objects.length;
            increaseSize(10);
            objects[oldLength] = obj;
        }else{
            objects[index] = obj;
        }
        
        sort();
    }
    
    public void set(int index, EngineObject obj)
    {
        if(index > objects.length - 1)
        {
            increaseSize(index - (objects.length - 1));
        }
        objects[index] = obj;
    }
    
    public void remove(EngineObject obj)
    {
        if(obj != null)
        {
            for(int i=0;i<this.size();i++)
            {
                if(objects[i].equals(obj))
                {
                    objects[i] = null;
                    for(int j=i+1;j<this.size();j++)
                    {
                        objects[j-1] = objects[j];
                        objects[j] = null;
                    }
                }
            }
        }
    }
    
    public EngineObject get(int i)
    {
        if(0 <= i && i < this.size())
        {
            return objects[i];
        }else{
            return null;
        }
    }
    
    public int size()
    {
        for(int i=objects.length-1;i>=0;i--)
        {
            if(objects[i] != null)
            {
                return i+1;
            }
        }
        
        return 0;
    }
    
    public boolean isEmpty()
    {
        if(size() == 0)
        {
            return true;
        }else{
            return false;
        }
    }
    
    private int firstAvailableIndex()
    {
        for(int i=0;i<objects.length;i++)
        {
            if(objects[i] == null)
            {
                return i;
            }
        }
        return UNAVAILABLE;
    }
    
    private void increaseSize(int n)
    {
        EngineObject[] newObjects = new EngineObject[objects.length + n];
        
        for(int i=0;i<objects.length;i++)
        {
            newObjects[i] = objects[i];
        }
        
        objects = newObjects;
    }
    
    private void sort() 
    {
        
        //Sorting z indexes
        
        for(int i=1; i<this.size();i++)
        {
            EngineObject n1 = objects[i-1];
            EngineObject n2 = objects[i];
            
            if(n2.zIndex < n1.zIndex)
            {
                objects[i-1] = n2;
                objects[i] = n1;
                
                for(int j=i;j>0;j--)
                {
                    n1 = objects[j-1];
                    n2 = objects[j];
                    
                    if(n2.zIndex < n1.zIndex)
                    {
                        objects[j-1] = n2;
                        objects[j] = n1;
                    }                    
                }
            }
        }
    }
    
    
}
