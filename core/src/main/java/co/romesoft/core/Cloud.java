package co.romesoft.core;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.ResourceCallback;


public class Cloud {
	
	//final float multHeight = (float)((float)Launcher.height / (float)480);
	
	ImageLayer il=null;
	float cloudY;
	float cloudX;

	public Cloud(final GroupLayer g, int type, final Float x, final Float y) {
		Image starImage = assets().getImage("images/objects/8c.png");
	    starImage.addCallback(new ResourceCallback<Image>() {
			
			@Override
			public void error(Throwable err) {
			
			}
			
			@Override
			public void done(Image resource) {
				il = graphics().createImageLayer(resource);
				il.setOrigin(resource.width()/2f, resource.height()/2f);
				
				cloudY = (int)(Launcher.height*Math.random());
				if (cloudY<resource.height()/2f) {
					cloudY = resource.height()/2f;
				} else if (cloudY>(Launcher.height-resource.height()/2f)) {
					cloudY = Launcher.height-resource.height()/2f;
				}
				
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
	
	
	public void update(float delta) {
		 cloudX += delta * 0.063f*Launcher.multHeight;
		  il.setTranslation(cloudX, 70*Launcher.multHeight);

		    if (cloudX > il.width() + Launcher.width) {
		    	cloudX = -il.width();
		      
		    }
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
