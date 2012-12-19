package co.romesoft.core;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.ResourceCallback;


public class CloudLow {
	
	//final float multHeight = (float)((float)Launcher.height / (float)480);
	
	ImageLayer il=null;
	float cloudY;
	float cloudX;

	public CloudLow(final GroupLayer g, int type, final Float x, final Float y) {
		Image starImage = assets().getImage("images/objects/2b.png");
	    starImage.addCallback(new ResourceCallback<Image>() {
			
			@Override
			public void error(Throwable err) {
			
			}
			
			@Override
			public void done(Image resource) {
				il = graphics().createImageLayer(resource);
				il.setOrigin(resource.width()/2f, resource.height()/2f);
				
				cloudY = (int)(Launcher.height-resource.height()*Launcher.multHeight/2f);
				/*if (cloudY<resource.height()/2f) {
					cloudY = resource.height()/2f;
				} else if (cloudY>(Launcher.height-resource.height()/2f)) {
					cloudY = Launcher.height-resource.height()/2f;
				}*/
				
				if (x != null) {
					cloudX = x;
					cloudY = y;
				}
				
				il.setTranslation(x!=null ? x : (Launcher.width - resource.width())/2 + resource.width()/2f, 
						cloudY);
				
				
			    
			    il.setScale(Launcher.multHeight);
				
				
				g.add(il);
			}
		});
	}
	
	private float angleStar;
	
	public void update(float delta) {
		 cloudX += delta * 0.033f*Launcher.multHeight;
		  il.setTranslation(cloudX, cloudY);

		    if (cloudX > il.scaledWidth() + Launcher.width) {
		    	cloudX = -il.scaledWidth();
		      
		    }
		    
		    angleStar += -(delta*0.25);
			il.setRotation(angleStar);
			
	}
	
	public void destroy() {
		il.destroy();
	}
	
	public boolean toBeRemoved = false;
	

	private float varVelo=(float) (Math.random()*0.1);
	
	float getVelocity() {
	    return (0.030f+varVelo)*Launcher.multHeight;
	  }
}
