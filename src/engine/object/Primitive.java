package engine.object;

import engine.core.EngineGraphics;
import engine.core.Screen;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Primitive
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
    
    public short type;
    public Color color = Color.black;
    
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
    }
    
    public void addVertex(double x, double y)
    {
        vertices.add(new Vertex(x, y));
    }
    
    public void addVertex(Vertex v)
    {
        vertices.add(v);
    }
    
    public void addVertices(Vertex[] vertices)
    {
        for(int i=0;i<vertices.length;i++)
        {
            this.vertices.add(vertices[i]);
        }
    }
    
    public void move(double dx, double dy)
    {
        for(int i=0;i<vertices.size();i++)
        {
            vertices.get(i).move(dx, dy);
        }
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
    
    public void draw(EngineGraphics eng, Vertex position, double rotation)
    {
        if(vertices.size() > 0)
        {
            Graphics2D g = eng.graphics;
            Vertex[] v = mapToScreen(eng.screen);
            
            g.setColor(color);
            g.translate(eng.screen.mapDX(position.x), eng.screen.mapDY(position.y));
            g.rotate(rotation);
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
