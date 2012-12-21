package co.romesoft.core;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.random;
import playn.core.AssetWatcher;
import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.Image;
import playn.core.ResourceCallback;
import playn.core.Sound;
import playn.core.Canvas.Composite;


public class Art
{
   

    public static Image[][] font;
  


    public static void init(AssetWatcher watcher)
    {
    	
           
            cutImage("images/font2.png", 16, 16,10,watcher);
           

                   
    }

   
    private static void cutImage(String imageName, final int xSize, final int ySize, final int which,AssetWatcher watcher) 
    {
    	watcher.add(assets().getImage(imageName));
    	Image source = assets().getImage(imageName);
    	source.addCallback(new ResourceCallback<Image>() {

			@Override
			public void done(Image source) {
				
				Image[][] images = new Image[(int)source.width() / xSize][(int)source.height() / ySize];
		        for (int x = 0; x < source.width() / xSize; x++)
		        {
		            for (int y = 0; y < source.height() / ySize; y++)
		            {
		            	CanvasImage image = graphics().createImage(xSize, ySize);//gc.createCompatibleImage(xSize, ySize, Transparency.BITMASK);
		                //Graphics2D g = (Graphics2D) image.getGraphics();
		                Canvas g = image.canvas();
		               
		                //g.setComposite(AlphaComposite.Src);
		                g.setCompositeOperation(Composite.SRC);
		                g.drawImage(source, -x * xSize, -y * ySize);
		                // needed ?
		                //g.dispose();
		                images[x][y] = image;
		            }
		        }

		      
		        if (which==10) font = images;
		        
		        
			}

			@Override
			public void error(Throwable err) {
				
				
			}
    		
    	});
    	
        
    }
    
    public static void drawStringDropShadow(Canvas g, String text, int x, int y, int c)
    {
        //drawString(g, text, x*16+5, y*16+5, 3);
        drawString(g, text, x*16+4, y*16+4, c);
    }
    
    public static void drawString(Canvas g, String text, int x, int y, int c)
    {
        char[] ch = text.toCharArray();
        for (int i = 0; i < ch.length; i++)
        {
            try {
				g.drawImage(Art.font[ch[i] - 32][c], x + i * 12, y);
			} catch (Exception e) {
				
			}
        }
    }
    
    //public static Sound[] samples = new Sound[300];
    
    public static final int TRUCK = 110;
    public static final int FIRETRUCK = 120;
    public static final int TRACTOR = 130;
    public static final int EXCAVATOR = 140;
    public static final int BUS = 150;
    public static final int TRAIN = 160;
    public static final int TRAIN_STEAM = 165;
    public static final int POLICE = 170;
    public static final int AMBULANCE = 180;
    public static final int HELICOPTER = 190;
    public static final int AIRPLANE_JET = 200;
    public static final int AIRPLANE_EL = 205;
    public static final int MOTORCYCLE = 210;
    public static final int MOTORCYCLE_BYKE = 215;
    public static final int BOAT_MOTOR = 220;
    public static final int SAILBOAT = 230;
    public static final int SHIP = 240;
    public static final int RACE_CAR = 250;
    public static final int CAR = 260;
    public static final int BIKE = 265;
    public static final int FORKLIFT = 270;
    public static final int VAN = 275;
    
    public static final int BUB_POP = 289;
    public static final int SPRING_1 = 290;
    public static final int SPRING_2 = 291;
    public static final int SPRING_3 = 292;
    public static final int SPRING_4 = 293;
    public static final int SPRING_5 = 294;
    public static final int LEVEL_COMPLETED = 295;
    public static final int DRAGGABLE_PLACED = 296;
    public static final int BOING = 297;
   
    
    public static Sound getSound(int sound) {
    	try
        {
   		//if (samples[sound] == null) {
   			switch (sound) {
			case BOING:
				return assets().getSound("snd/boing");
				//break;
			case DRAGGABLE_PLACED:
				return assets().getSound("snd/dropped");
				//break;
			case LEVEL_COMPLETED:
				return assets().getSound("snd/applause");
				//break;
			case SPRING_1:
				return assets().getSound("snd/spring1");
				//break;
			case SPRING_2:
				return assets().getSound("snd/spring2");
				//break;
			case SPRING_3:
				return assets().getSound("snd/spring3");
				//break;
			case SPRING_4:
				return assets().getSound("snd/spring4");
				//break;
			case SPRING_5:
				return assets().getSound("snd/spring5");
				//break;
			case BUB_POP:
				return assets().getSound("snd/pop");
				//break;
			default:
				
				return assets().getSound("snd/animal("+sound+")");
				//break;
			}
   			
   			//return samples[sound];
   			
   		/*} else if (samples[sound] != null) {
   			return samples[sound];
    	}*/
	    	
	    	
        }
        catch (Exception e)
        {
        	
        }
   	
        return null;
    }
    
    public static void playSound(int sound) {
   	 try
        {
   		    Sound s = getSound(sound);
	   		if (s != null) {
	   			if (s.isPlaying()) {
	    			s.stop();
	    		}
	    		
	    		s.play();
	    		/*if (sound < 197) {
	    			latestPlaying=s;
	    		}*/
	   		}
	    	
        }
        catch (Exception e)
        {
        }
   	
   }
    
    /*public static Sound latestPlaying;
    
    public static void stopLatest() {
    	
    	try {
			if (latestPlaying != null) {
				if (latestPlaying.isPlaying()) {
					latestPlaying.stop();
				}
				
			}
			
		} catch (Exception e) {
			
		}
    	
    }*/
    
    
    
    public static void playRandomSpring() {
    	int v = Utils.getInRange(1,6);
    	switch (v) {
			case 1:
				Art.playSound(SPRING_1);
				break;
			case 2:
				Art.playSound(SPRING_2);
				break;
			case 3:
				Art.playSound(SPRING_3);
				break;
			case 4:
				Art.playSound(SPRING_4);
				break;
			case 5:
				Art.playSound(SPRING_5);
				break;
			default:
				Art.playSound(SPRING_1);
				break;
		}
    }
    
    public enum type {DUCK,ROBOT, MOTORBOAT,BIPLANE,BALL,DRUMS,HORSE,DOG, BABY,CHICKEN,GUITAR, RANDOM, CAR, TRUCK, BIKE, BOAT, BUS, EXCA, TRACTOR, VAN, TRAIN, MULETTO, HELI, PLANE, MOTO,
		 FIRETRUCK, POLICE, AMBULANCE, RACE_CAR }
   
	public static void playVehicleSound(type t) {
		switch (t) {
			case DUCK:
				assets().getSound("snd/duck").play();
				break;
			case ROBOT:
				assets().getSound("snd/robot").play();
				break;
			case MOTORBOAT:
				assets().getSound("snd/motorboat").play();
				break;
			case BIPLANE:
				assets().getSound("snd/biplane").play();
				break;
			case BALL:
				assets().getSound("snd/ball").play();
				break;
			case DRUMS:
				assets().getSound("snd/drums").play();
				break;
			case HORSE:
				assets().getSound("snd/horse").play();
				break;
			case DOG:
				assets().getSound("snd/dog").play();
				break;
			case CHICKEN:
				assets().getSound("snd/chicken").play();
				break;
			case GUITAR:
				assets().getSound("snd/guitar").play();
				break;
			case BABY:
				assets().getSound("snd/baby").play();
				break;
			case RANDOM:
				playRandomSpring();
				break;
			case CAR:
				Art.playSound(Art.CAR + (int)(random() * 3));
				break;
			case TRUCK:
				Art.playSound(Art.TRUCK+(int)(random() * 3));
				break;
			case BIKE:
				Art.playSound(Art.BIKE);
				break;
			case BOAT:
				Art.playSound(Art.SHIP/*+ (int)(random() * 4)*/);
				break;
			case BUS:
				Art.playSound(Art.BUS /*+ (int)(random() * 5)*/);
				break;
			case EXCA:
				Art.playSound(Art.EXCAVATOR/*+ (int)(random() * 4)*/);
				break;
			case TRACTOR:
				Art.playSound(Art.TRACTOR/*+ (int)(random() * 7)*/);
				break;
			case VAN:
				Art.playSound(Art.VAN);
				break;
			case TRAIN:
				Art.playSound(Art.TRAIN/*+ (int)(random() * 4)*/);
				break;
			case MULETTO:
				Art.playSound(Art.FORKLIFT);
				break;
			case HELI:
				Art.playSound(Art.HELICOPTER/* + (int)(random() * 5)*/);
				break;
			case PLANE:
				Art.playSound(Art.AIRPLANE_JET/* + (int)(random() * 4)*/);
				break;
			case MOTO:
				Art.playSound(Art.MOTORCYCLE/* + (int)(random() * 5)*/);
				break;
			case FIRETRUCK:
				Art.playSound(Art.FIRETRUCK/* + (int)(random() * 4)*/);
				break;
			case POLICE:
				Art.playSound(Art.POLICE/* + (int)(random() * 5)*/);
				break;
			case AMBULANCE:
				Art.playSound(Art.AMBULANCE/* + (int)(random() * 5)*/);
				break;
			case RACE_CAR:
				Art.playSound(Art.RACE_CAR/* + (int)(random() * 5)*/);
				break;
			default:
				Art.playSound(Art.RACE_CAR/*+ (int)(random() * 7)*/);
				break;
			}
	}

}