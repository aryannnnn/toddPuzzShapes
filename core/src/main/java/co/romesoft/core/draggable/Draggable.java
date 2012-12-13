package co.romesoft.core.draggable;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.Pointer.Event;
import playn.core.Pointer.Listener;
import co.romesoft.core.Art;
import co.romesoft.core.Art.type;
import co.romesoft.core.Launcher;
import co.romesoft.core.WinLayer;
import co.romesoft.core.screens.LevelScreen;


public class Draggable {
	
	
	private static final float DEFAULT_INITIAL_SCALE = 0.8f;
	private float thisScale;
	
	//private static final int COLUMN_WIDE = 95;
	private final DropZone myDropZone;
	private final String image;
	private WinLayer wl;
	
	private boolean startDrag=false;
	private boolean stopDrag=false;
	private boolean gettingBackToStart=false;
	//public boolean placed=false;
	
	final ImageLayer iLayer;
	private float startX;
	private float startY;
	private float origDepth;

	public Draggable(final GroupLayer layer, String image, float y0, float y1, 
			final DropZone myDropZone, final LevelScreen levelScreen) {
		this.myDropZone = myDropZone;
		this.image = image;
		
		final Image car = assets().getImage(image);
	    iLayer = graphics().createImageLayer(car);
	    iLayer.setOrigin(car.width()/2, car.height()/2);
	    thisScale = Launcher.multHeight*DEFAULT_INITIAL_SCALE;
	    iLayer.setScale(thisScale);
	    
	    if (iLayer.scaledHeight()>Launcher.height/4) {
	    	//System.out.println("scaledHeight");
	    	thisScale = Launcher.multHeight*DEFAULT_INITIAL_SCALE*(float)0.5;
		    iLayer.setScale(thisScale);
	    }
	    if (iLayer.scaledWidth()>Launcher.multWidth*LevelScreen.RANDOM_DROPZONES_POSITION_PAD) {
	    	//System.out.println("scaledWidth");
	    	thisScale = Launcher.multWidth*DEFAULT_INITIAL_SCALE*(float)LevelScreen.RANDOM_DROPZONES_POSITION_PAD/iLayer.width();
		    iLayer.setScale(thisScale);
	    }
	    
	    iLayer.setTranslation(Launcher.multWidth*LevelScreen.RANDOM_DROPZONES_POSITION_PAD/2 ,
	    		y0+((y1-y0)/2));
	    
	    this.startX = iLayer.transform().tx();
		this.startY = iLayer.transform().ty();
	    
	    layer.add(iLayer);
	    
	    this.origDepth = iLayer.depth();
	    
	    iLayer.addListener(new Listener() {
			
			@Override
			public void onPointerStart(Event event) {
				
				startDrag=true;
				iLayer.setDepth(1000);
				//TODO vehicle sound ?
				Art.playVehicleSound(DragSounds.fromImageToType(Draggable.this.image));
			}
			
			@Override
			public void onPointerEnd(Event event) {
				boolean droppedOK = false;
				if (myDropZone != null) {
					droppedOK = Layer.Util.hitTest(myDropZone.iLayer, event);
				}
				System.out.println("onPointerEnd " +droppedOK);
				
				if (droppedOK) {
					myDropZone.iLayer.setImage(car);
					iLayer.destroy();
					if (wl == null) {
						wl = new WinLayer(layer, myDropZone.iLayer.transform().tx()+myDropZone.iLayer.scaledWidth()/2, 
								myDropZone.iLayer.transform().ty()+myDropZone.iLayer.scaledHeight()/2);
					}
					// play sound
					Art.playSound(Art.DRAGGABLE_PLACED);
					//placed=true;
					levelScreen.draggablePlaced();
				} else {
					//play random spring sound
					Art.playRandomSpring();
					
					iLayer.setDepth(origDepth);
					startDrag = false;
					stopDrag = true;
					gettingBackToStart = true;
				}
			}
			
			@Override
			public void onPointerDrag(Event event) {
				//bgLayerc.setOrigin(event.localX(), event.localY());
				iLayer.setTranslation(event.x(), event.y());
				
			}

			//@Override
			public void onPointerCancel(Event event) {				
			}
			
		});
	    
	    
	}
	
	public void update(float delta) {
		if (wl != null) {
			wl.update(delta);
			if (wl.thisLayer.destroyed()) {
				System.out.println("destroied wl");
				wl=null;
			}
		}
		
		if (startDrag) {
			iLayer.setScale(iLayer.transform().scaleX()+Launcher.multHeight*0.03f);
			if (iLayer.transform().scaleX() >= Launcher.multHeight*(myDropZone!=null ? myDropZone.scaleVal : DropZone.SCALE_VAL_DEFAULT)) {
				iLayer.setScale(Launcher.multHeight*(myDropZone!=null ? myDropZone.scaleVal : DropZone.SCALE_VAL_DEFAULT));
				startDrag = false;
			}
		}
		
		if (stopDrag) {
			iLayer.setScale(iLayer.transform().scaleX()-Launcher.multHeight*0.03f);
			if (iLayer.transform().scaleX() <= thisScale) {
				iLayer.setScale(thisScale);
				stopDrag = false;
			}
		}
		
		if (gettingBackToStart) {
			float x = startX;
			float y = startY;
			float distanceX = Math.abs(startX - iLayer.transform().tx());
			float distanceY = Math.abs(startY - iLayer.transform().ty());
			if (distanceX > distanceY) {
				distanceX = distanceX/distanceY;
				distanceX = distanceX > 2 ? 2 : distanceX;
				distanceY = 1;
			} else {
				distanceY = distanceY/distanceX;
				distanceY = distanceY > 2 ? 2 : distanceY;
				distanceX = 1;
			}
			
			if (iLayer.transform().tx() > startX) {
				x = iLayer.transform().tx() - (delta * getVelocity() * distanceX);
				if (x < startX) {
					x = startX;
				}
			} else if (iLayer.transform().tx() < startX) {
				x = iLayer.transform().tx() + (delta * getVelocity() * distanceX);
				if (x > startX) {
					x = startX;
				}
			}
			
			if (iLayer.transform().ty() > startY) {
				y = iLayer.transform().ty() - (delta * getVelocity() * distanceY);
				if (y < startY) {
					y = startY;
				}
			} else if (iLayer.transform().ty() < startY) {
				y = iLayer.transform().ty() + (delta * getVelocity() * distanceY);
				if (y > startY) {
					y = startY;
				}
			}
			
			iLayer.setTranslation(x, y);
			if (x == startX && y == startY) {
				gettingBackToStart = false;
			}
		}
	}
	
	 float getVelocity() {
	    return (0.3f)*Launcher.multHeight;
	  }
	
}
