import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import java.util.*;

public class MineObject extends DrawableObject
{
   
   private Random rand = new Random();
   private int color = rand.nextInt(0, 256);
   private boolean up = true;
   private boolean funColor = true; //Set to true for fun!
   private int colorDeterminer = rand.nextInt(0, 6); //Determiner for fun
   
   public MineObject(float x, float y)
   {
      super(x, y);
   }
   
   //this method you implement for each object you want to draw. Act as if the thing you want to draw is at x,y.
   //NOTE: DO NOT CALL DRAWME YOURSELF. Let the the "draw" method do it for you. I take care of the math in that method for a reason.
   public void drawMe(float x, float y, GraphicsContext gc)
   {
   
      gc.setStroke(Color.DARKGRAY);
      if(funColor)
      {
      
         if(colorDeterminer == 0)
         {
         
            gc.setFill(Color.rgb(255, color, color));
         
         } else if(colorDeterminer == 1)
         {
         
            gc.setFill(Color.rgb(255, 255, color));
         
         } else if(colorDeterminer == 2)
         {
         
            gc.setFill(Color.rgb(color, 255, color));
         
         } else if(colorDeterminer == 3)
         {
         
            gc.setFill(Color.rgb(color, 255, 255));
         
         } else if(colorDeterminer == 4)
         {
         
            gc.setFill(Color.rgb(color, color, 255));
         
         } else if(colorDeterminer == 5)
         {
         
            gc.setFill(Color.rgb(255, color, 255));
         
         }
      
      } else
      {
         
         gc.setFill(Color.rgb(255, color, color));
      
      }
      if(up)
      {
      
         color = color+2;
         
         if(color>=255)
         {
            
            color = 255;
            up = false;
         
         }
         
      } else
      {
      
         color = color-2;
         if(color<=0)
         {
            
            color = 0;
            up = true;
         
         }
      
      }
      
      gc.fillOval(x-4, y-4, 9, 9);
      gc.strokeOval(x-6, y-6, 13, 13);
   
   }
   public void act()
   {
   }
   
}