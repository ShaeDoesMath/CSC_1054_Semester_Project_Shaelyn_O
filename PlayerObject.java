import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.animation.*;
import javafx.event.*;
import javafx.scene.input.*;
import java.util.*;

public class PlayerObject extends DrawableObject
{

   private Random rand = new Random();
   
   private float forceX = 0.0f, forceY = 0.0f, changeX, changeY, objectMass = 1.0f;
   private boolean xPress=false, yPress=false, wPress=false, aPress = false, sPress = false, dPress = false, up1 = true, up2 = true, up3 = true, up4 = true;
   private int randCol1 = rand.nextInt(0, 256), randCol2 = rand.nextInt(0, 256), randCol3 = rand.nextInt(0, 256), randCol4 = rand.nextInt(0, 256);
   
   public PlayerObject(float x, float y)
   {
      super(x,y);
   }
   
   //this method you implement for each object you want to draw. Act as if the thing you want to draw is at x,y.
   //NOTE: DO NOT CALL DRAWME YOURSELF. Let the the "draw" method do it for you. I take care of the math in that method for a reason.
   public void drawMe(float x, float y, GraphicsContext gc)
   {
   
      gc.setStroke(Color.BLACK);
      gc.setFill(Color.rgb(150, 0, 220));
      gc.fillOval(x-17, y-17, 35, 35);
      gc.strokeOval(x-17, y-17, 35, 35);
      gc.setFill(Color.CYAN);
      gc.fillOval(x-5, y-5, 11, 11);
      gc.strokeOval(x-5, y-5, 11, 11);
      if(up1)
      {
      
         randCol1 = randCol1+2;
         
         if(randCol1>=255)
         {
            
            randCol1 = 255;
            up1 = false;
         
         }
         
      } else
      {
      
         randCol1 = randCol1-2;
         if(randCol1<=0)
         {
            
            randCol1 = 0;
            up1 = true;
         
         }
      
      }
      gc.setFill(Color.rgb(randCol1, 255, randCol1));
      gc.fillOval(x-2, y-14, 5, 5);
      gc.strokeOval(x-2, y-14, 5, 5);
      if(up2)
      {
      
         randCol2 = randCol2+2;
         
         if(randCol2>=255)
         {
            
            randCol2 = 255;
            up2 = false;
         
         }
         
      } else
      {
      
         randCol2 = randCol2-2;
         if(randCol2<=0)
         {
            
            randCol2 = 0;
            up2 = true;
         
         }
      
      }
      gc.setFill(Color.rgb(randCol2, 255, randCol2));
      gc.fillOval(x-2, y+10, 5, 5);
      gc.strokeOval(x-2, y+10, 5, 5);
      if(up3)
      {
      
         randCol3 = randCol3+2;
         
         if(randCol3>=255)
         {
            
            randCol3 = 255;
            up3 = false;
         
         }
         
      } else
      {
      
         randCol3 = randCol3-2;
         if(randCol3<=0)
         {
            
            randCol3 = 0;
            up3 = true;
         
         }
      
      }
      gc.setFill(Color.rgb(randCol3, 255, randCol3));
      gc.fillOval(x-14, y-2, 5, 5);
      gc.strokeOval(x-14, y-2, 5, 5);
      if(up4)
      {
      
         randCol4 = randCol4+2;
         
         if(randCol4>=255)
         {
            
            randCol4 = 255;
            up4 = false;
         
         }
         
      } else
      {
      
         randCol4 = randCol4-2;
         if(randCol4<=0)
         {
            
            randCol4 = 0;
            up4 = true;
         
         }
      
      }
      gc.setFill(Color.rgb(randCol4, 255, randCol4));
      gc.fillOval(x+10, y-2, 5, 5);
      gc.strokeOval(x+10, y-2, 5, 5);
   
   }
   
   public void act()
   {
   
      if(wPress)
      {
      
         forceY=forceY-.1f;
         if(forceY<-5)
         {
         
            forceY=-5;
         
         }
      
      }
      if(aPress)
      {
      
         forceX=forceX-.1f;
         if(forceX<-5)
         {
         
            forceX=-5;
         
         }
      
      }
      if(sPress)
      {
      
         forceY=forceY+.1f;
         if(forceY>5)
         {
         
            forceY=5;
         
         }
      
      }
      if(dPress)
      {
      
         forceX=forceX+.1f;
         if(forceX>5)
         {
         
            forceX=5;
         
         }
      
      }
      if(!xPress)
      {
      
         if(forceX<-.025f)
         {
         
            forceX = forceX+.025f;
         
         } else if((-.025f)<forceX&&forceX<(.025f))
         {
         
            forceX = 0f;
         
         } else
         {
         
            forceX = forceX-.025f;
         
         }
               
      }
      if(!yPress)
      {
      
         if(forceY<-.025f)
         {
         
            forceY = forceY+.025f;
         
         } else if(-.025f<forceY&&forceY<.025f)
         {
         
            forceY = 0f;
         
         } else
         {
         
            forceY = forceY-.025f;
         
         }
               
      }
      
      changeX = forceX/objectMass;
      changeY = forceY/objectMass;
   
      setX(getX()+changeX);
      setY(getY()+changeY);
   
   }
   
   public class KeyListenerPMPressed implements EventHandler<KeyEvent>
   {
   
      public void handle(KeyEvent event)
      {
      
         if(event.getCode() == KeyCode.W)
         {
         
            
            wPress=true;
            yPress=true;
         
         }
         if(event.getCode() == KeyCode.A)
         {
         
            aPress=true;
            xPress=true;
         
         }
         if(event.getCode() == KeyCode.S)
         {
            
            sPress=true;
            yPress=true;
         
         }
         if(event.getCode() == KeyCode.D)
         {
            
            dPress=true;
            xPress=true;
         
         }
      
      }
      
   }
   
   public KeyListenerPMPressed listenerPress()
   {
   
      KeyListenerPMPressed listener = new KeyListenerPMPressed();
      return listener;
   
   }
   
   public class KeyListenerPMReleased implements EventHandler<KeyEvent>
   {
   
      public void handle(KeyEvent event)
      {
      
         if(event.getCode() == KeyCode.W)
         {
         
            wPress=false;
         
         }
         if(event.getCode() == KeyCode.A)
         {
         
            aPress=false;
         
         }
         if(event.getCode() == KeyCode.S)
         {
         
            sPress=false;
         
         }
         if(event.getCode() == KeyCode.D)
         {
         
            dPress=false;
         
         }
         
         if(!wPress&&!sPress)
         {
         
            yPress = false;
         
         }
         if(!aPress&&!dPress)
         {
         
            xPress = false;
         
         }
      
      }
              
   }
   
   public KeyListenerPMReleased listenerRelease()
   {
   
      KeyListenerPMReleased listener = new KeyListenerPMReleased();
      return listener;
   
   }
   
}