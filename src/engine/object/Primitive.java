package engine.object;

import engine.core.EngineGraphics;
import engine.core.EngineObject;
import engine.core.Screen;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;


public class Primitive extends EngineObject
{   
    public static final short POINTS = 0;
    public static final short LINES = 1;
    public static final short LINE_STRIP = 2;
    public static final short LINE_LOOP = 3;
    public static final short POLYGON = 4;
    public static final short TRIANGLES = 5;
    public static final short TRIANGLE_STRIP = 6;
    public static final short TRIANGLE_FAN = 7;
    public static final short QUADS = 8;
    public static final short QUAD_STRIP= 9;
    
    public static final short HIGH = 0;
    public static final short MIDDLE = 1;
    public static final short LOW = 2;
    
    public short type;
    public Color color = Color.black;
    public BufferedImage loadedImage = null;
    public BufferedImage image = null;
    private boolean imageUpdated = false;
    
    public int pointSize = 5; //pixels
    
    public ArrayList<Vertex> vertices = new <Vertex>ArrayList(3);
    
    public Primitive()
    {
    }
    
    public Primitive(short n)
    {
        setType(n);
    }
    
    public Primitive(String type)
    {
        setType(type);
    }
        
    public void setType(short type)
    {
        this.type = type;
        imageUpdated = false;
    }    
    
    public void setType(String type)
    {
        if(type.equals("POINTS"))
        {
            this.type = Primitive.POINTS;
        }
        if(type.equals("LINES"))
        {
            this.type = Primitive.LINES;
        }
        if(type.equals("LINE_STRIP"))
        {
            this.type = Primitive.LINE_STRIP;
        }
        if(type.equals("LINE_LOOP"))
        {
            this.type = Primitive.LINE_LOOP;
        }
        if(type.equals("POLYGON"))
        {
            this.type = Primitive.POLYGON;
        }
        if(type.equals("TRIANGLES"))
        {
            this.type = Primitive.TRIANGLES;
        }
        if(type.equals("TRIANGLE_STRIP"))
        {
            this.type = Primitive.TRIANGLE_STRIP;
        }
        if(type.equals("TRIANGLE_FAN"))
        {
            this.type = Primitive.TRIANGLE_FAN;
        }
        if(type.equals("QUADS"))
        {
            this.type = Primitive.QUADS;
        }
        if(type.equals("QUAD_STRIP"))
        {
            this.type = Primitive.QUAD_STRIP;
        }
        
        imageUpdated = false;
    }
    
    public void addVertex(double x, double y)
    {
        vertices.add(new Vertex(x, y));
        imageUpdated = false;
    }
    
    public void addVertices(double[][] vertices)
    {
        try{
            for(int i=0;i<vertices.length;i++)
            {
                this.vertices.add(new Vertex(vertices[i][0], vertices[i][1]));
            }
        }catch(Exception e)
        {
            e.printStackTrace();
            return;
        }
        imageUpdated = false;
    }
    
    public void addVertices(double[] x, double[] y)
    {
        if(x.length != y.length)
        {
            return;
        }
        for(int i=0;i<x.length;i++)
        {
            this.vertices.add(new Vertex(x[i], y[i]));
        }        
        imageUpdated = false;
    }
    
    public void addVertex(Vertex v)
    {
        vertices.add(v);
        imageUpdated = false;
    }
    
    public void addVertices(Vertex[] vertices)
    {
        for(int i=0;i<vertices.length;i++)
        {
            this.vertices.add(vertices[i]);
        }
        imageUpdated = false;
    }
    
    public void move(double dx, double dy)
    {
        for(int i=0;i<vertices.size();i++)
        {
            vertices.get(i).move(dx, dy);
        }
        imageUpdated = false;
    }
    
    public void scale (double multiplier)
    {
        double sumX = 0;
        double sumY = 0;
        for(int i=0;i<vertices.size();i++)
        {
            sumX += vertices.get(i).x;
            sumY += vertices.get(i).y;
        }
        double centerX = sumX / vertices.size();
        double centerY = sumY / vertices.size();
        for(int i=0;i<vertices.size();i++)
        {
            vertices.get(i).x = centerX + (vertices.get(i).x - centerX) * multiplier;
            vertices.get(i).y = centerY + (vertices.get(i).y - centerY) * multiplier;
        }
        imageUpdated = false;
    }
    
    private void makeImage(Screen screen)
    {
        Bounds bounds = this.getBounds();        
        
        if(loadedImage != null)
        {
            image = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        }else{
            image = new BufferedImage(screen.mapDX(bounds.getWidth()), screen.mapDY(bounds.getHeight()), BufferedImage.TYPE_INT_ARGB);            
        }
        Graphics2D g = image.createGraphics();
        
        setRenderingQuality(g, HIGH);
        
        //Mapping vertices to image
        
        Vertex[] v = new Vertex[vertices.size()];
        for(int i=0;i<v.length;i++)
        {
            v[i] = new Vertex();
            v[i].x = (int)Math.round((vertices.get(i).x - bounds.xMin) * image.getWidth() / (bounds.xMax - bounds.xMin));
            v[i].y = (int)Math.round(image.getHeight() - (vertices.get(i).y - bounds.yMin) * image.getHeight() / (bounds.yMax - bounds.yMin));
        }
        
        drawPrimitive(g, v);
        
        if(loadedImage != null)
        {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN));
            g.drawImage(loadedImage, null, 0, 0);
        }
        
        g.dispose();        
        
        imageUpdated = true;
    }
    
    public void loadImage(String url)
    {
        try{
             loadedImage = ImageIO.read(new File(url));
        }catch(IOException e){
            e.printStackTrace();
        }        
        imageUpdated = false;
    }
    
    public Bounds getBounds()
    {
        Bounds bounds = new Bounds();
        double tX, tY;
        
        for(int i=0;i<vertices.size();i++)
        {
            tX = vertices.get(i).x;
            tY = vertices.get(i).y;

            if(tX < bounds.xMin)
            {
                bounds.xMin = tX;
            }
            if(tX > bounds.xMax)
            {
                bounds.xMax = tX;
            }
            if(tY < bounds.yMin)
            {
                bounds.yMin = tY;
            }
            if(tY > bounds.yMax)
            {
                bounds.yMax = tY;
            }
        }
        
        return bounds;
    }
    
    public Vertex[] mapToScreen(Screen s)
    {
        Vertex[] sVerts = new Vertex[vertices.size()];
        for(int i=0;i<vertices.size();i++)
        {
            sVerts[i] = new Vertex();
            sVerts[i].x = s.mapX(vertices.get(i).x);
            sVerts[i].y = s.mapY(vertices.get(i).y);
        }
        return sVerts;
    }
    
    public void setRenderingQuality(Graphics2D g, short quality)
    {
        if(quality == 0)
        {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);             
        }
        
        if(quality == 1)
        {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);              
        }
        
        if(quality == 2)
        {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF); 
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);                
        }
    }
    
    public void draw(EngineGraphics eng, WorldObject obj)
    {   
        if(!imageUpdated)
        {
            makeImage(eng.screen);
        }
        
        Graphics2D g = eng.graphics;
        
        Bounds bounds = getBounds();
        
        setRenderingQuality(g, HIGH);
        
        g.drawImage(image, eng.screen.mapX(obj.position.x), eng.screen.mapY(obj.position.y), eng.screen.mapDX(bounds.getWidth()), eng.screen.mapDY(bounds.getHeight()), null);
        
        /*
        System.out.println("Start");
        System.out.println("View");
        System.out.println(eng.screen.world.view.xOrigin);
        System.out.println(eng.screen.world.view.yOrigin);
        System.out.println(eng.screen.world.view.width);
        System.out.println(eng.screen.world.view.height);
        System.out.println("Bounds");
        System.out.println(bounds.getWidth());
        System.out.println(bounds.getHeight());
        System.out.println("mapped");
        System.out.println(eng.screen.mapDX(bounds.getWidth()));
        System.out.println(eng.screen.mapDY(bounds.getHeight()));
        */
        
                
        /*
        BufferedImage loaded = null;
        try{
             loaded = ImageIO.read(new File("Desert.jpg"));
        }catch(IOException e){
            e.printStackTrace();
        }                
        g.drawImage(loaded, 0, 0, null);*/
        /*
        //Setup drawing resources
        Graphics2D g = eng.graphics;

        //Apply transformations and map vertices to screen 

        Vertex[] v = new Vertex[vertices.size()];
        for(int i=0;i<vertices.size();i++)
        {
            v[i] = new Vertex();

            //Original
            double xO = vertices.get(i).x;
            double yO = vertices.get(i).y;

            //Rotating

            double xR = Math.cos(obj.angularPosition) * (xO - obj.centerOfRotation.x) - Math.sin(obj.angularPosition) * (yO - obj.centerOfRotation.y) + obj.centerOfRotation.x;
            double yR = Math.sin(obj.angularPosition) * (xO - obj.centerOfRotation.x) + Math.cos(obj.angularPosition) * (yO - obj.centerOfRotation.y) + obj.centerOfRotation.y;                

            //Translating
            double xT = xR + obj.position.x;
            double yT = yR + obj.position.y;

            v[i].x = eng.screen.mapX(xT);
            v[i].y = eng.screen.mapY(yT);
        }
        //


        //Apply primitive transformations

        g.setColor(color);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        drawPrimitive(g, v);
        */
    }
    
    public void drawPrimitive(Graphics2D g, Vertex[] v)
    {
        if(vertices.size() > 0)
        {
            g.setColor(color);
            
            //Drawing primitives
            switch(type){
                case POINTS:
                    for(int i=0;i<v.length;i++)
                    {
                        g.fillOval((int)v[i].x - pointSize / 2, (int)v[i].y - pointSize / 2, pointSize, pointSize);
                    }
                    break;

                case LINES:
                    if(vertices.size() > 1)
                    {
                        for(int i=0;i<(v.length - v.length%2);i += 2)
                        {
                            g.drawLine((int)v[i].x, (int)v[i].y, (int)v[i+1].x, (int)v[i+1].y);
                        }
                    }
                    break;

                case LINE_STRIP:
                    if(vertices.size() > 1)
                    {
                        for(int i=1;i<v.length;i++)
                        {
                            g.drawLine((int)v[i-1].x, (int)v[i-1].y, (int)v[i].x,(int)v[i].y);
                        }
                    }
                    break;

                case LINE_LOOP:
                    if(vertices.size() > 1)
                    {
                        for(int i=1;i<v.length;i++)
                        {
                            g.drawLine((int)v[i-1].x, (int)v[i-1].y, (int)v[i].x,(int)v[i].y);
                        }
                        g.drawLine((int)v[v.length-1].x, (int)v[v.length-1].y, (int)v[0].x,(int)v[0].y);
                    }
                    break;

                case POLYGON:
                    if(vertices.size() > 2)
                    {
                        for(int i=2;i<v.length;i++)
                        {
                            int[] xf3 = new int[3];
                            int[] yf3 = new int[3];
                            for(int j=0;j<2;j++)
                            {
                                xf3[j] = (int)v[i-j].x;
                                yf3[j] = (int)v[i-j].y;
                            }
                            xf3[2] = (int)v[0].x;
                            yf3[2] = (int)v[0].y;
                            g.fillPolygon(xf3, yf3, 3);
                        }
                    } 
                    break;

                case TRIANGLES:

                    if(vertices.size() > 2)
                    {
                        for(int i=0;i<(v.length - v.length%3);i += 3)
                        {
                            int[] xf3 = new int[3];
                            int[] yf3 = new int[3];
                            for(int j=0;j<3;j++)
                            {
                                xf3[j] = (int)v[i+j].x;
                                yf3[j] = (int)v[i+j].y;
                            }
                            g.fillPolygon(xf3, yf3, 3);
                        }
                    }                    
                    break;

                case TRIANGLE_STRIP:
                    if(vertices.size() > 2)
                    {
                        for(int i=2;i<v.length;i++)
                        {
                            int[] xf3 = new int[3];
                            int[] yf3 = new int[3];
                            for(int j=0;j<3;j++)
                            {
                                xf3[j] = (int)v[i-j].x;
                                yf3[j] = (int)v[i-j].y;
                            }
                            g.fillPolygon(xf3, yf3, 3);
                        }
                    }                       
                    break;

                case TRIANGLE_FAN:
                    if(vertices.size() > 2)
                    {
                        for(int i=2;i<v.length;i++)
                        {
                            int[] xf3 = new int[3];
                            int[] yf3 = new int[3];
                            for(int j=0;j<2;j++)
                            {
                                xf3[j] = (int)v[i-j].x;
                                yf3[j] = (int)v[i-j].y;
                            }
                            xf3[2] = (int)v[0].x;
                            yf3[2] = (int)v[0].y;
                            g.fillPolygon(xf3, yf3, 3);
                        }
                    } 
                    break;

                case QUADS:
                    if(vertices.size() > 3)
                    {
                        for(int i=0;i<(v.length - v.length%4);i += 4)
                        {
                            int[] xf4 = new int[4];
                            int[] yf4 = new int[4];
                            for(int j=0;j<4;j++)
                            {
                                xf4[j] = (int)v[i+j].x;
                                yf4[j] = (int)v[i+j].y;
                            }
                            g.fillPolygon(xf4, yf4, 4);
                        }                        
                    }
                    break;
                case QUAD_STRIP:
                    if(vertices.size() > 3)
                    {
                        for(int i=3;i<(v.length - v.length%2);i += 2)
                        {
                            int[] xf4 = new int[4];
                            int[] yf4 = new int[4];
                            for(int j=0;j<4;j++)
                            {
                                xf4[j] = (int)v[i-j].x;
                                yf4[j] = (int)v[i-j].y;
                            }
                            g.fillPolygon(xf4, yf4, 4);
                        }
                    }                       
                    break;                    
            }        
        }
    }
}
