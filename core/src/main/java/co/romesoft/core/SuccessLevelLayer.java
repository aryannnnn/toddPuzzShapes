package co.romesoft.core;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import java.util.ArrayList;

import co.romesoft.core.screens.LevelScreen;

import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.PlayN;
import playn.core.Pointer.Listener;
import playn.core.ResourceCallback;
import playn.core.Canvas.Composite;
import playn.core.Pointer.Event;


public class SuccessLevelLayer {
	
	private static final int TIME_BEFORE_FADE_MS = 7000;

	private static final int NUM_IMAGES_SUCCESS = 4;

	private static final int NUMB_ELEMS = 12;

	public final GroupLayer thisLayer = PlayN.graphics().createGroupLayer();
	
	private LevelScreen levelScreen;
	
	private static int latestImageType=0;
	
	/*private float xx;
	private float yy;
	private Canvas cc;*/
	private ArrayList<ImageLayer> starsLayer = new ArrayList<ImageLayer>();
	
	private ArrayList<ImageLayer> starsFalling = new ArrayList<ImageLayer>();
	//private int imgHeight;
	
	public boolean disposable = false;
	
	//double size =  Math.random();
	double speed = Math.random() * 0.10;
	
	public SuccessLevelLayer(final GroupLayer layer, LevelScreen levelScreen) {
		this.levelScreen = levelScreen;
		layer.add(thisLayer);
		
		if (speed < 0.05) speed+=0.05;
		//if (size < 0.5) size+=0.5;
		
		Image starImage;
		int valR = 0;
		if (latestImageType==0) {
			valR = Utils.getInRange(1, NUM_IMAGES_SUCCESS);
		} else {
			valR = (latestImageType+1);
			if (valR > NUM_IMAGES_SUCCESS) {
				valR=1;
			}
		}
		latestImageType = valR;
		switch (valR) {
			case 1:
				starImage = assets().getImage("images/sun.png");
				break;
			case 2:
				starImage = assets().getImage("images/balloon.png");
				break;
			case 3:
				starImage = assets().getImage("images/ball.png");
				break;
			case 4:
				starImage = assets().getImage("images/bub.png");
				break;
			default:
				starImage = assets().getImage("images/sun.png");
				break;
		}
		
		starImage.addCallback(new ResourceCallback<Image>() {
				
				@Override
				public void error(Throwable err) {
				
				}
				
				@Override
				public void done(Image resource) {
					
					float dist = (float)Launcher.width/NUMB_ELEMS;
					float mult=0.5F;
					/*if (dist<resource.width()) {
						mult=dist/resource.width();
					}*/
					
					
												
					for (int i=0; i<NUMB_ELEMS; i++) {
						
						final ImageLayer dl = graphics().createImageLayer(resource);
						dl.setOrigin(resource.width()/2f, resource.height()/2f);
						
						
						dl.setTranslation(dist*i,(float)Math.random()*Launcher.height);
						dl.setScale(mult*Launcher.multHeight);
						if (latestImageType == 4) {
							dl.setAlpha(0.8f);
						}
						
						thisLayer.add(dl);
						
						starsLayer.add(dl);
						
						dl.addListener(new Listener() {
							
							@Override
							public void onPointerStart(Event event) {
								starsFalling.add(dl);
								if (latestImageType == 4 || latestImageType == 2) {
									Art.playSound(Art.BUB_POP);
								}
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
					
					
				}
			});
	}
	
	private float angleStar;
	
	private float life;
	private float fadingTime;
	
	public void update(float delta) {
		life+=delta;
		if (life>=TIME_BEFORE_FADE_MS) {
			
	 		fadingTime+=delta;
			final float alpha = 1-fadingTime/1500;
			thisLayer.setAlpha(alpha);
			
			if (alpha<=0 && !thisLayer.destroyed()) {
				thisLayer.destroy();
				levelScreen.showNextButton();
		   }
		 	
		} else {
			float ty = thisLayer.transform().ty() - (delta * 0.033f * Launcher.multHeight);
			thisLayer.setTranslation(thisLayer.transform().tx(), ty);
		}
		
		angleStar += delta*0.25;
		  
		for (ImageLayer star : starsLayer) {
			if (latestImageType != 2) {
				star.setRotation(angleStar);
			}
		}
		
		for (ImageLayer star : starsFalling) {
			if (latestImageType == 4 || latestImageType == 2) {
				//bub
				if (!star.destroyed()) {
					star.destroy();
				}
				
			} else {
				float ty = star.transform().ty() + (delta * 0.133f * Launcher.multHeight);
				star.setTranslation(star.transform().tx(), ty);
			}
		}
		    
	}
	
	/*public void  destroy() {
		System.out.println("destroy");
		for (ImageLayer star : starsLayer) {
			star.destroy();
		}
	}*/

}
