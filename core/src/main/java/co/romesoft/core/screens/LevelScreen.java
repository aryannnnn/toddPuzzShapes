package co.romesoft.core.screens;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.keyboard;
import static playn.core.PlayN.pointer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Key;
import playn.core.Keyboard;
import playn.core.Layer;
import playn.core.Pointer;
import playn.core.Keyboard.TypedEvent;
import playn.core.Pointer.Event;
import playn.core.Pointer.Listener;
import pythagoras.f.Point;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import co.romesoft.core.Art;
import co.romesoft.core.Launcher;
import co.romesoft.core.SuccessLevelLayer;
import co.romesoft.core.draggable.Draggable;
import co.romesoft.core.draggable.DropZone;


public class LevelScreen extends Screen implements Keyboard.Listener {
	private static final int TRY_TO_PLACE = 1000;

	private ScreenStack _screens;
	
	public static final int NUM_LEVELS = 15; //19 FULL //15 FREE
	
	private static int MAX_DRAG_ITEMS = 3;
	private int num_draggable;
	
	private static final boolean RANDOM_DROPZONES_POSITION = true;
	public static final float RANDOM_DROPZONES_POSITION_PAD = 145;
	
	private static int actualLevel;
	
	private SuccessLevelLayer successL;
	
	private HomeScreen hs;
	private List<Draggable> draggables = new ArrayList<Draggable>();
	private List<DropZone> dropZones = new ArrayList<DropZone>();
	
	static {
		
	}
	
	public LevelScreen(final ScreenStack _screens, final HomeScreen hs, int levelNumber) {
		if (levelNumber <= 0 || levelNumber > NUM_LEVELS) {
			levelNumber = 1;
		}
		this.actualLevel = levelNumber;
		this._screens = _screens;
		this.hs = hs;
		System.out.println("keyboard().setListener level");
		keyboard().setListener(this);
		
		/* TODO to pause and to remove
		pointer().setListener(new Pointer.Listener() {

			@Override
			public void onPointerStart(Event event) {

				if (event.x() > (float)(Launcher.width-100) && event.y() < (float)(100) ) {
					Launcher.paused=!Launcher.paused;
					System.out.println("paused "+Launcher.paused);
				}
				
			}

			@Override
			public void onPointerEnd(Event event) {

			}

			@Override
			public void onPointerDrag(Event event) {

			}
			
		});
		*/
		
		Image bgImage = assets().getImage("images/puzzlebg"+levelNumber+".jpg");
	    final ImageLayer bgLayer = graphics().createImageLayer(bgImage);
	    bgLayer.setScale(Launcher.multWidth, Launcher.multHeight);
	    
	    layer.add(bgLayer);
	    
	    
	    DropZone d1 = null,d2 = null,d3 = null,d4 = null;
	    String imageD1 = "images/objects/"+levelNumber+"a-off.png";
	    String imageD2 = "images/objects/"+levelNumber+"b-off.png";
	    String imageD3 = "images/objects/"+levelNumber+"c-off.png";
	    String imageD4 = "images/objects/"+levelNumber+"d-off.png";
	    
	    int availableHeight = Launcher.height;
	    int adsHeight = 0;
		boolean adsShowed = Launcher.showAdsAlways || Launcher.showAdsSometimes;
		if (adsShowed) {
			adsHeight = (int)(50*Launcher.screenDensity);
			availableHeight = availableHeight - adsHeight;
		}
	    
	    
	    if (RANDOM_DROPZONES_POSITION) {
	    	float scaleVal = DropZone.SCALE_VAL_DEFAULT;
	    	
	    	Image dzi1 = assets().getImage(imageD1);
	    	float x=(float) (Math.random()*(float)Launcher.width);
	    	if (x+(dzi1.width()*scaleVal*Launcher.multHeight)>Launcher.width) {
	    		x = Launcher.width-(dzi1.width()*scaleVal*Launcher.multHeight);
	    		//System.out.println("d1: sopra");
	    	} else if (x<RANDOM_DROPZONES_POSITION_PAD*Launcher.multWidth) {
	    		x = RANDOM_DROPZONES_POSITION_PAD*Launcher.multWidth;
	    		//System.out.println("d1: sotto");
	    	}
	    	float y=(float) (Math.random()*(float)availableHeight)+adsHeight;
	    	if (y+(dzi1.height()*scaleVal*Launcher.multHeight)>Launcher.height) {
	    		y = Launcher.height-(dzi1.height()*scaleVal*Launcher.multHeight);
	    	}
	    	//System.out.println("d1: "+x+" "+y);
	    	d1 = new DropZone(layer, imageD1,x, y, scaleVal);
			dropZones.add(d1);
			num_draggable=1;
			
			boolean notOverlap=false;
			dzi1 = assets().getImage(imageD2);
			scaleVal = DropZone.SCALE_VAL_DEFAULT;
			for (int i=0; i<TRY_TO_PLACE; i++) {
				
		    	x=(float) (Math.random()*(float)Launcher.width);
		    	if (x+(dzi1.width()*scaleVal*Launcher.multHeight)>Launcher.width) {
		    		x = Launcher.width-(dzi1.width()*scaleVal*Launcher.multHeight);
		    	} else if (x<RANDOM_DROPZONES_POSITION_PAD*Launcher.multWidth) {
		    		x = RANDOM_DROPZONES_POSITION_PAD*Launcher.multWidth;
		    	}
		    	y=(float) (Math.random()*(float)availableHeight)+adsHeight;
		    	if (y+(dzi1.height()*scaleVal*Launcher.multHeight)>Launcher.height) {
		    		y = Launcher.height-(dzi1.height()*scaleVal*Launcher.multHeight);
		    	}
		    	
		    	if (!overlap(new rect(d1.iLayer.transform().tx(), d1.iLayer.transform().ty(), d1.iLayer.scaledWidth(), d1.iLayer.scaledHeight()), new rect(x, y, dzi1.width()*Launcher.multHeight, dzi1.height()*Launcher.multHeight))) {
		    		notOverlap=true;
		    		break;
		    	}
		    	
		    	if (i > TRY_TO_PLACE/2) {
			    	scaleVal = DropZone.SCALE_VAL_DEFAULT - (DropZone.SCALE_VAL_DEFAULT*(float)i/(float)TRY_TO_PLACE);
			    	if (scaleVal<0.3) scaleVal=0.3f;
		    	}
			}
			if (notOverlap) {
				//System.out.println("d2: "+x+" "+y);
		    	d2 = new DropZone(layer, imageD2, x, y, scaleVal);
				dropZones.add(d2);
				num_draggable++;
			}
			
			notOverlap=false;
			dzi1 = assets().getImage(imageD3);
			scaleVal = DropZone.SCALE_VAL_DEFAULT;
			for (int i=0; i<TRY_TO_PLACE; i++) {
		    	x=(float) (Math.random()*(float)Launcher.width);
		    	if (x+(dzi1.width()*scaleVal*Launcher.multHeight)>Launcher.width) {
		    		x = Launcher.width-(dzi1.width()*scaleVal*Launcher.multHeight);
		    	} else if (x<RANDOM_DROPZONES_POSITION_PAD*Launcher.multWidth) {
		    		x = RANDOM_DROPZONES_POSITION_PAD*Launcher.multWidth;
		    	}
		    	y=(float) (Math.random()*(float)availableHeight)+adsHeight;
		    	if (y+(dzi1.height()*scaleVal*Launcher.multHeight)>Launcher.height) {
		    		y = Launcher.height-(dzi1.height()*scaleVal*Launcher.multHeight);
		    	}
		    	
		    	if (d1!=null && d2!=null) {
			    	if (!overlap(new rect(d1.iLayer.transform().tx(), d1.iLayer.transform().ty(), d1.iLayer.scaledWidth(), d1.iLayer.scaledHeight()), new rect(x, y, dzi1.width()*Launcher.multHeight, dzi1.height()*Launcher.multHeight))
			    			&& !overlap(new rect(d2.iLayer.transform().tx(), d2.iLayer.transform().ty(), d2.iLayer.scaledWidth(), d2.iLayer.scaledHeight()), new rect(x, y, dzi1.width()*Launcher.multHeight, dzi1.height()*Launcher.multHeight))) {
			    		notOverlap=true;
			    		break;
			    	}
		    	} else {
		    		if (!overlap(new rect(d1.iLayer.transform().tx(), d1.iLayer.transform().ty(), d1.iLayer.scaledWidth(), d1.iLayer.scaledHeight()), new rect(x, y, dzi1.width()*Launcher.multHeight, dzi1.height()*Launcher.multHeight))) {
			    		notOverlap=true;
			    		break;
			    	}
		    	}
		    	
		    	if (i > TRY_TO_PLACE/2) {
			    	scaleVal = DropZone.SCALE_VAL_DEFAULT - (DropZone.SCALE_VAL_DEFAULT*(float)i/(float)TRY_TO_PLACE);
			    	if (scaleVal<0.3) scaleVal=0.3f;
		    	}
			}
			if (notOverlap) {
		    	d3 = new DropZone(layer, imageD3,x, y, scaleVal);
				dropZones.add(d3);
				num_draggable++;
			}
			
			if (MAX_DRAG_ITEMS > 3) {
				notOverlap=false;
				dzi1 = assets().getImage(imageD4);
				scaleVal = DropZone.SCALE_VAL_DEFAULT;
				for (int i=0; i<TRY_TO_PLACE; i++) {
			    	x=(float) (Math.random()*(float)Launcher.width);
			    	if (x+(dzi1.width()*scaleVal*Launcher.multHeight)>Launcher.width) {
			    		x = Launcher.width-(dzi1.width()*scaleVal*Launcher.multHeight);
			    	} else if (x<RANDOM_DROPZONES_POSITION_PAD*Launcher.multWidth) {
			    		x = RANDOM_DROPZONES_POSITION_PAD*Launcher.multWidth;
			    	}
			    	y=(float) (Math.random()*(float)availableHeight)+adsHeight;
			    	if (y+(dzi1.height()*scaleVal*Launcher.multHeight)>Launcher.height) {
			    		y = Launcher.height-(dzi1.height()*scaleVal*Launcher.multHeight);
			    	}
			    	
			    	if (d1!=null && d2!=null && d3!=null) {
				    	if (!overlap(new rect(d1.iLayer.transform().tx(), d1.iLayer.transform().ty(), d1.iLayer.scaledWidth(), d1.iLayer.scaledHeight()), new rect(x, y, dzi1.width()*Launcher.multHeight, dzi1.height()*Launcher.multHeight))
				    			&& !overlap(new rect(d2.iLayer.transform().tx(), d2.iLayer.transform().ty(), d2.iLayer.scaledWidth(), d2.iLayer.scaledHeight()), new rect(x, y, dzi1.width()*Launcher.multHeight, dzi1.height()*Launcher.multHeight))
				    			&& !overlap(new rect(d3.iLayer.transform().tx(), d3.iLayer.transform().ty(), d3.iLayer.scaledWidth(), d3.iLayer.scaledHeight()), new rect(x, y, dzi1.width()*Launcher.multHeight, dzi1.height()*Launcher.multHeight))) {
				    		notOverlap=true;
				    		break;
				    	}
			    	} else if (d1!=null && d2!=null) {
				    	if (!overlap(new rect(d1.iLayer.transform().tx(), d1.iLayer.transform().ty(), d1.iLayer.scaledWidth(), d1.iLayer.scaledHeight()), new rect(x, y, dzi1.width()*Launcher.multHeight, dzi1.height()*Launcher.multHeight))
				    			&& !overlap(new rect(d2.iLayer.transform().tx(), d2.iLayer.transform().ty(), d2.iLayer.scaledWidth(), d2.iLayer.scaledHeight()), new rect(x, y, dzi1.width()*Launcher.multHeight, dzi1.height()*Launcher.multHeight))) {
				    		notOverlap=true;
				    		break;
				    	}
			    	} else {
			    		if (!overlap(new rect(d1.iLayer.transform().tx(), d1.iLayer.transform().ty(), d1.iLayer.scaledWidth(), d1.iLayer.scaledHeight()), new rect(x, y, dzi1.width()*Launcher.multHeight, dzi1.height()*Launcher.multHeight))) {
				    		notOverlap=true;
				    		break;
				    	}
			    	}
			    	
			    	if (i > TRY_TO_PLACE/2) {
				    	scaleVal = DropZone.SCALE_VAL_DEFAULT - (DropZone.SCALE_VAL_DEFAULT*(float)i/(float)TRY_TO_PLACE);
				    	if (scaleVal<0.3) scaleVal=0.3f;
			    	}
				}
				if (notOverlap) {
			    	d4 = new DropZone(layer, imageD4, x, y, scaleVal);
					dropZones.add(d4);
					num_draggable++;
				}
			}
			
			
	    } else {
		   
			d1 = new DropZone(layer, imageD1,
		    		200*Launcher.multWidth, 40*Launcher.multHeight, null);
			dropZones.add(d1);
		   
			d2 = new DropZone(layer, imageD2,
		    		400*Launcher.multWidth, 250*Launcher.multHeight, null);
			dropZones.add(d2);
		    
			d3 = new DropZone(layer, imageD3,
		    		180*Launcher.multWidth, 240*Launcher.multHeight, null);
			dropZones.add(d3);
		    
			d4 = new DropZone(layer, imageD4,
		    		250*Launcher.multWidth, 250*Launcher.multHeight, null);
			dropZones.add(d4);
	    }
	    
	    int y = Launcher.height/4;
	    if (MAX_DRAG_ITEMS > 3) {
	    	y = Launcher.height/4;
	    } else {
	    	y = Launcher.height/3;
	    }
    	//if (d1!=null) {
		draggables.add(new Draggable(layer, "images/objects/"+levelNumber+"a.png",
    		0,y,d1, this));
    	//}
    
    	//if (d2!=null) {
    	draggables.add(new Draggable(layer, "images/objects/"+levelNumber+"b.png",
    		y,y+y,d2, this));
    	//}
    
    	//if (d3!=null) {
    	draggables.add(new Draggable(layer, "images/objects/"+levelNumber+"c.png",
    			y+y,y+y*2,d3, this));
    	//}
    
    	if (MAX_DRAG_ITEMS > 3) {
    	//if (d4!=null) {
    	draggables.add(new Draggable(layer, "images/objects/"+levelNumber+"d.png",
    			y+y*2,y+y*3,d4, this));
    	//}
    	}
	   
	   
	}

	
	
	@Override public void wasAdded () {
		System.out.println("LevelScreen wasAdded");
	}
	
	 @Override public void wasRemoved () {
		 System.out.println("LevelScreen wasRemoved");
	    super.wasRemoved();
	       
	    //keyboard().setListener(null);
        while (layer.size() > 0) {
        	layer.get(0).destroy();
        }
    }
	 
	 int placedDraggbles=0;
	 boolean levelCompleted=false;
	 
	 public void draggablePlaced() {
		 placedDraggbles++;
		 
		 if (placedDraggbles == num_draggable) {
				// livello finito aggiungere animazione
				levelCompleted=true;
				System.out.println("levelCompleted");
				Art.playSound(Art.LEVEL_COMPLETED);
				
				successL = new SuccessLevelLayer(layer, this);
				
			}
	 }



	public void showNextButton() {
		Image nextpuzzleImage = assets().getImage("images/next.png");
		final ImageLayer nextpuzzleImageLayer = graphics().createImageLayer(nextpuzzleImage);
		nextpuzzleImageLayer.setScale(Launcher.multWidth);
		
		nextpuzzleImageLayer.setTranslation((RANDOM_DROPZONES_POSITION_PAD*Launcher.multWidth-nextpuzzleImageLayer.scaledWidth())/2, 
				(Launcher.height-nextpuzzleImageLayer.scaledHeight())/2);
		
		layer.add(nextpuzzleImageLayer);
		
		nextpuzzleImageLayer.addListener(new Listener() {
			
			@Override
			public void onPointerStart(Event event) {
				
				_screens.replace(new LevelScreen(_screens, hs, ++actualLevel));
				
			}
			
			@Override
			public void onPointerEnd(Event event) {
				
				
			}
			
			@Override
			public void onPointerDrag(Event event) {
				
				
			}

			//@Override
			public void onPointerCancel(Event event) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	public void update(float delta) {
		
		for (Draggable draggable : draggables) {
			draggable.update(delta);
		}
		
		for (DropZone dropZone : dropZones) {
			dropZone.update(delta);
		}
		
		if (successL != null) {
			successL.update(delta);
			if (successL.thisLayer.destroyed()) {
				System.out.println("destroied wl");
				successL=null;
			}
		}
		
	}

	@Override
	public void onKeyDown(playn.core.Keyboard.Event event) {

		
	}

	@Override
	public void onKeyTyped(TypedEvent event) {

		
	}
	
	private Date backClicked = new Date();

	@Override
	public void onKeyUp(playn.core.Keyboard.Event event) {
		System.out.println("onKeyUp");
		 //ANDROID SPECIFIC CODES
	    if (event.key() == Key.BACK || event.key() == Key.B)
	    {
	  	  Date now = new Date();
	  	  now = new Date(now.getTime()-1000);
	  	  
	  	  if (backClicked.before(now)) {
	  		  backClicked = new Date();
		      System.out.println("back");
		      hs.backToHome();
	  	  }
	    }
		
	}
	
	
	
	
	
	static class rect
	{
		public float x;
		public float y;
		public float width;
		public float height;
		public rect(float x, float y, float width, float height) {

			super();
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
		@Override
		public String toString() {

			return "rect [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + "]";
		}
	    
	    
	};

	
	
	static boolean overlap( rect r1,  rect r2)
	{
	    // The rectangles don't overlap if
	    // one rectangle's minimum in some dimension 
	    // is greater than the other's maximum in
	    // that dimension.
		//System.out.println(r1);
		//System.out.println(r2);

		boolean noOverlap = r1.x > r2.x+r2.width ||
	                     r2.x > r1.x+r1.width ||
	                     r1.y > r2.y + r2.height ||
	                     r2.y > r1.y +r1.height;

	    return !noOverlap;
	}
	


}
