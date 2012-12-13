package co.romesoft.core;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import java.util.ArrayList;

import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.PlayN;
import playn.core.ResourceCallback;
import playn.core.Canvas.Composite;


public class WinLayer {
	
	public final GroupLayer thisLayer = PlayN.graphics().createGroupLayer();
	
	/*private float xx;
	private float yy;
	private Canvas cc;*/
	private ArrayList<ImageLayer> starsLayer = new ArrayList<ImageLayer>();
	//private int imgHeight;
	
	public boolean disposable = false;
	
	//double size =  Math.random();
	double speed = Math.random() * 0.10;
	
	public WinLayer(final GroupLayer layer, final float x, final float y) {

		layer.add(thisLayer);
		
		if (speed < 0.05) speed+=0.05;
		//if (size < 0.5) size+=0.5;
		Image starImage = assets().getImage("images/star.png");
		
		starImage.addCallback(new ResourceCallback<Image>() {
				
				@Override
				public void error(Throwable err) {
				
				}
				
				@Override
				public void done(Image resource) {
					
					//float dist = (float)Launcher.width/10;
					float mult=0.5F;
					/*if (dist<resource.width()) {
						mult=dist/resource.width();
					}*/
					
					/*if (x==0 && y==0) {
												
						for (int i=0; i<10; i++) {
							
							ImageLayer dl = graphics().createImageLayer(resource);
							dl.setOrigin(resource.width()/2f, resource.height()/2f);
							
							
							dl.setTranslation(dist*i,(float)Math.random()*Launcher.height);
							dl.setScale(mult*Launcher.multHeight);
							
							thisLayer.add(dl);
							
							starsLayer.add(dl);
						}
					} else {*/
						for (int i=-2; i<2; i++) {
							
							ImageLayer dl = graphics().createImageLayer(resource);
							dl.setOrigin(resource.width()/2f, resource.height()/2f);
							
							
							dl.setTranslation(x+(i*40*Launcher.multHeight)*(float)Math.random() ,
											  y+(i*40*Launcher.multHeight)*(float)Math.random());
							dl.setScale(mult*Launcher.multHeight);
							
							thisLayer.add(dl);
							
							starsLayer.add(dl);
						}
						
					
					
				}
			});
	}
	
	private float angleStar;
	
	private float life;
	private float fadingTime;
	
	public void update(float delta) {
		life+=delta;
		if (life>=1000) {
			
	 		fadingTime+=delta;
			final float alpha = 1-fadingTime/1500;
			thisLayer.setAlpha(alpha);
			
			if (alpha<=0) {
				thisLayer.destroy();
		   }
		 	
		}
		
		angleStar += delta*0.25;
		  
		for (ImageLayer star : starsLayer) {
			star.setRotation(angleStar);
		}
		    
	}
	
	/*public void  destroy() {
		System.out.println("destroy");
		for (ImageLayer star : starsLayer) {
			star.destroy();
		}
	}*/

}
