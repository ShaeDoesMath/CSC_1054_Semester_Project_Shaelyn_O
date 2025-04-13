import java.net.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.*;
import javafx.scene.paint.*;
import javafx.geometry.*;
import javafx.scene.image.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.io.*;
import java.lang.*;
import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.animation.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import java.net.*;
import javafx.geometry.*;


public class Main extends Application
{

   //Instantiating all the variables that need to span the entire program
   FlowPane fp;
   PlayerObject thePlayer = new PlayerObject(0, 0);
   Canvas theCanvas = new Canvas(600,600);
   AnimationTimer ta;
   static int highScore;
   double score = 0;
   ArrayList<MineObject> mineList = new ArrayList<MineObject>();
   Random rand = new Random();
   int ogridx=0, ogridy=0;
   static Scanner fileScan;
   FileOutputStream highScoreStream;
   PrintWriter scorePW;
   boolean hit = false;
   int count = 0;

   //Setting up the graphics and starting the animation
   public void start(Stage stage)
   {
      
   
      fp = new FlowPane();
      fp.getChildren().add(theCanvas);
      gc = theCanvas.getGraphicsContext2D();
      drawBackground(300,300,gc);
      thePlayer.draw(300,300,gc,true);
      fp.setOnKeyPressed(thePlayer.listenerPress());
      fp.setOnKeyReleased(thePlayer.listenerRelease());
      
      Scene scene = new Scene(fp, 600, 600);
      stage.setScene(scene);
      stage.setTitle("Project :)");
      stage.show();
      
      ta = new AnimationHandler();
      ta.start();
      
      fp.requestFocus();
   }
   
   //Provided code
   GraphicsContext gc;
   Image background = new Image("stars.png");
   Image overlay = new Image("starsoverlay.png");
   Random backgroundRand = new Random();
   public void drawBackground(float playerx, float playery, GraphicsContext gc)
   {
	  //re-scale player position to make the background move slower. 
      playerx*=.1;
      playery*=.1;
   
	   //figuring out the tile's position.
      float x = (playerx) / 400;
      float y = (playery) / 400;
      
      int xi = (int) x;
      int yi = (int) y;
      
	   //draw a certain amount of the tiled images
      for(int i=xi-3;i<xi+3;i++)
      {
         for(int j=yi-3;j<yi+3;j++)
         {
            gc.drawImage(background,-playerx+i*400,-playery+j*400);
         }
      }
      
	   //below repeats with an overlay image
      playerx*=2f;
      playery*=2f;
   
      x = (playerx) / 400;
      y = (playery) / 400;
      
      xi = (int) x;
      yi = (int) y;
      
      for(int i=xi-3;i<xi+3;i++)
      {
         for(int j=yi-3;j<yi+3;j++)
         {
            gc.drawImage(overlay,-playerx+i*400,-playery+j*400);
         }
      }
   }
   
   
   //Animating!
   public class AnimationHandler extends AnimationTimer
   {
      public void handle(long currentTimeInNanoSeconds) 
      {
      
         //Basic setup
         gc.clearRect(0,0,600,600);
         thePlayer.act();
         score = Math.sqrt((thePlayer.getX()*thePlayer.getX())+(thePlayer.getY()*thePlayer.getY()));
         int cgridx, cgridy;
         float posX = thePlayer.getX(), posY = thePlayer.getY();
         
         //Shaelyn's grid-determining nonsense (if we start grid at 0,0 in position, quad 4 is grid(0,0), quad 1 is(0, -1), quad 2 is(-1, -1), and quad 3 is(-1, 0) so the first 4 100*100 grids around 0,0 aren't all grid(0,0))
         if(thePlayer.getX()>=0)
         {
            
            cgridx = ((int)posX)/100;
            
         } else
         {
         
            if(((int)posX)%100==0)
            {
            
               cgridx = ((int)posX)/100;
            
            } else
            {
               
               cgridx = (((int)posX)/100)-1;
               
            }
         
         }
         if(thePlayer.getY()>=0)
         {
         
            cgridy = ((int)posY)/100;
         
         } else
         {
         
            if(((int)posY)%100==0)
            {
            
               cgridy = ((int)posY)/100;
            
            } else
            {
               
               cgridy = (((int)posY)/100)-1;
               
            }
         
         }
         
         //Checking if the player has changed grids- either moving from the original (forms a whole new grid of mines) or simply left a previous grid in one direction (spawns new mines top/bottom or left/right only based on direction)
         if((ogridx!=cgridx||ogridy!=cgridy))
         {
         
            ogridx = cgridx;
            ogridy = cgridy;
            
            //Forming the grid for mines
            for(int i = cgridx-5; i<cgridx+5; i++)
            {
               
               for(int j = cgridy-5; j<cgridy+5; j++)
               {
                  
                  int distFromCent = (int) Math.sqrt((i*100)*(i*100)+(j*100)*(j*100));
                  int maxMines = distFromCent/1000;
                  int currentMines = 0;
                  
                  //Checking existence of mines in a grid
                  for(int k = 0; k<mineList.size(); k++)
                  {
                  
                     MineObject currentMine = mineList.get(k);
                     int kgridx, kgridy;
                     
                     //More grid nonsense to check correct grid coords for a mine
                     if(currentMine.getX()>=0)
                     {
                        
                        kgridx = ((int)currentMine.getX())/100;
                        
                     } else
                     {
                     
                        if(((int)currentMine.getX())%100==0)
                        {
                        
                           kgridx = ((int)currentMine.getX())/100;
                        
                        } else
                        {
                     
                           kgridx = (((int)currentMine.getX())/100)-1;
                     
                        }
                        
                     }
                     if(currentMine.getY()>=0)
                     {
                     
                        kgridy = ((int)currentMine.getY())/100;
                     
                     } else
                     {
                     
                        if(((int)currentMine.getY())%100==0)
                        {
                        
                           kgridy = ((int)currentMine.getY())/100;
                        
                        } else
                        {
                     
                           kgridy = (((int)currentMine.getY())/100)-1;
                     
                        }
                     
                     }
                     if(kgridx==i&&kgridy==j)
                     {
                     
                        currentMines++;
                     
                     }
                  
                  }
                  int minesToSpawn = maxMines-currentMines;
                  
                  //Actually adding in mines along the edges of the grid
                  if(i==cgridx-5||i==cgridx+4)
                  {
                     
                     for(int k = 0; k < minesToSpawn; k++)
                     {
                     
                        int mineChance = rand.nextInt(0, 10);
                        if(mineChance<3)
                        {
                        
                           int randX = rand.nextInt(0, 100), randY = rand.nextInt(0, 100);
                           mineList.add(new MineObject(i*100+randX, j*100+randY));
                        
                        }
                        
                     
                     }
                  
                  }
                  if((j==cgridy-5||j==cgridy+4)&&!(i==cgridx-5||i==cgridx+4))
                  {
                     
                     for(int k = 0; k < minesToSpawn; k++)
                     {
                     
                        int mineChance = rand.nextInt(0, 10);
                        if(mineChance<3)
                        {
                        
                           int randX = rand.nextInt(0, 100), randY = rand.nextInt(0, 100);
                           mineList.add(new MineObject(randX+i*100, randY+j*100));
                        
                        }
                        
                     }
                  
                  }
               
               }
            
            }  
         
         }  
         
         //Setting up background
         drawBackground(thePlayer.getX(),thePlayer.getY(),gc); 
         gc.setStroke(Color.WHITE);

         //Checking for collision and drawing mines
         for(int i = 0; i < mineList.size(); i++)
         {
         
            MineObject currentMine = mineList.get(i);
            if(currentMine.distance(thePlayer)<=20)
            {
            
               //Loss code
               try
               {
                  
                  highScoreStream = new FileOutputStream("highScore.txt", false);
                  scorePW = new PrintWriter(highScoreStream);
                  
               } catch (FileNotFoundException fnfe)
               {
               
                  System.out.println("File does not exist!");
               
               }
               
               for(int j = i+1; j<mineList.size(); j++)
               {
               
                  MineObject unexplodedMine = mineList.get(j);
                  unexplodedMine.draw(thePlayer.getX(),thePlayer.getY(),gc,false);
               
               }
               
               if((int)score>highScore)
               {
               
                  scorePW.print((int)score);
                  scorePW.close();
               
               } else
               {
               
                  scorePW.print(highScore);
                  scorePW.close();
               
               }
               hit = true;
               ta.stop();
            
            } else if(currentMine.distance(thePlayer)>=800) //removing distant mines
            {
            
               mineList.remove(i);
            
            } else //drawing a normal mine
            {
            
               currentMine.draw(thePlayer.getX(),thePlayer.getY(),gc,false);
               
            }
            
         
         }
         if(!hit) //if game continues, drawing player
         {
         
            thePlayer.draw(300,300,gc,true);
            
         }

         //Score
         gc.setStroke(Color.WHITE);
	      gc.strokeText("Current score: "+(int) score, 5, 20);
         gc.strokeText("High Score: "+highScore, 5, 35);
         
      }
      
   }
   
   //Scanning in high score and launching the game
   public static void main(String[] args)
   {
   
      try
      {
         
         fileScan = new Scanner(new File("highScore.txt"));
         highScore = fileScan.nextInt();
         
      } catch (FileNotFoundException fnfe)
      {
      
         highScore = 0;
      
      } catch (NoSuchElementException nsee)
      {
      
         highScore = 0;
      
      }
      launch(args);
   }
}

