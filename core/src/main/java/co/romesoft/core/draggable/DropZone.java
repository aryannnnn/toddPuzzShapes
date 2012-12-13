package co.romesoft.core.draggable;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import co.romesoft.core.Launcher;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer.Event;
import playn.core.Pointer.Listener;


public class DropZone {
	
	public static final float SCALE_VAL_DEFAULT = 0.8f;
	
	public final float scaleVal;
	
	final public ImageLayer iLayer;
	
	public DropZone(final GroupLayer layer, String image, float x, float y, Float scaleVal) {
		this.scaleVal = scaleVal != null ? scaleVal : SCALE_VAL_DEFAULT;
		
		Image car = assets().getImage(image);
		iLayer = graphics().createImageLayer(car);
		iLayer.setScale(Launcher.multHeight*this.scaleVal);
		iLayer.setTranslation(x, y);
	    
		//iLayer.setOrigin(car.width()/2, car.height()/2);
	    layer.add(iLayer);
	    
	    /*bgLayerc.addListener(new Listener() {
			
			@Override
			public void onPointerStart(Event event) {
				System.out.println("drop start");
				
			}
			
			@Override
			public void onPointerEnd(Event event) {
				System.out.println("drop end");
				
			}
			
			@Override
			public void onPointerDrag(Event event) {
				//bgLayerc.setOrigin(event.localX(), event.localY());
				System.out.println("drop drag");
				
			}
			
			@Override
			public void onPointerCancel(Event event) {
				// TODO Auto-generated method stub
				
			}
		});*/
	}
	
	public void droppedOK() {
		
	}

	public void update(float delta) {

		
		
	}

}
