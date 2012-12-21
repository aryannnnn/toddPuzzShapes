package co.romesoft.core.screens;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.keyboard;
import static playn.core.PlayN.log;
import static playn.core.PlayN.pointer;

import java.util.Date;

import co.romesoft.core.Art;
import co.romesoft.core.BubblesLayer;
import co.romesoft.core.Cloud;
import co.romesoft.core.CloudLow;
import co.romesoft.core.InfoPageLayer;
import co.romesoft.core.Launcher;
import co.romesoft.core.LauncherInterface;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Key;
import playn.core.Keyboard;
import playn.core.Keyboard.TypedEvent;
import playn.core.Pointer;
import playn.core.ResourceCallback;
import playn.core.Pointer.Event;
import playn.core.Pointer.Listener;
import playn.core.Sound;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;


public class HomeScreen extends Screen  implements Keyboard.Listener {
	
	private ScreenStack _screens;
	private final LauncherInterface launcher;
	 Cloud c;
	 CloudLow cl;
	//BubblesLayer bl;
	 InfoPageLayer infoPageL;
	 
	 //Sound startSound = assets().getSound("snd/traffic");
	
	public HomeScreen(final ScreenStack _screens, LauncherInterface launcher) {
		this._screens = _screens;
		this.launcher = launcher;
		
		pointer().setListener(new Pointer.Listener() {

			@Override
			public void onPointerStart(Event event) {
				if (infoPageL != null) {
					infoPageL.onPointerStart(event);
					return;
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
				//  Auto-generated method stub
				
			}
			
		});
	    
	}
	
	 @Override public void wasShown () {
		 System.out.println("HomeScreen wasShown");
	     super.wasShown();
	     
	     //startSound.setLooping(true);
	     //startSound.play();
	     
	     System.out.println("keyboard().setListener home");
		 keyboard().setListener(this);
	    
	     
	     Image bgImage = assets().getImage("images/bg.jpg");
		    final ImageLayer bgLayer = graphics().createImageLayer(bgImage);
		    bgLayer.setScale(Launcher.multWidth, Launcher.multHeight);
		    
		    layer.add(bgLayer);
		    
		    c = new Cloud(layer, 1, null, null);
		    cl = new CloudLow(layer, 1, null, null);
		   
		    
		    Image playButtonImage = assets().getImage("images/color_button.png");
		    ImageLayer playButtonL = graphics().createImageLayer(playButtonImage);
			
			playButtonL.setScale(Launcher.multHeight);
			playButtonL.setAlpha(0.9f);
			playButtonL.setTranslation((Launcher.width / 2) - (playButtonL.scaledWidth() / 2f) , 
					Launcher.height / 2 + ( (-playButtonL.scaledHeight())/2));
			layer.add(playButtonL);
			
			
			playButtonL.addListener(new Listener() {
				
				@Override
				public void onPointerStart(Event event) {
					
					
				}
				
				@Override
				public void onPointerEnd(Event event) {
					
					if (infoPageL ==null) {
						Launcher.gameStarted = true;
						int levelNumber = (int)((Math.random()*LevelScreen.NUM_LEVELS))+1;
						_screens.push(new LevelScreen(_screens, HomeScreen.this, levelNumber));
					}
					
				}
				
				@Override
				public void onPointerDrag(Event event) {
					
					
				}

				//@Override
				public void onPointerCancel(Event event) {
					//  Auto-generated method stub
					
				}
				
			});
			
			
			Image infoImage = assets().getImage("images/info.png");
			ImageLayer infoLayer = graphics().createImageLayer(infoImage);
			//width-infoImage.width(), height-infoImage.height()
			//if (Launcher.height<=320) {
			infoLayer.setScale(Launcher.multHeight);
			//}
			infoLayer.setTranslation(Launcher.width-infoLayer.scaledWidth(), 
					Launcher.height-infoLayer.scaledHeight());
			layer.add(infoLayer);
			
			//mainMenuLayer.add(buttonsLayer);
		   
		    
			infoLayer.addListener(new Listener() {
				
				@Override
				public void onPointerStart(Event event) {
					if (infoPageL == null) {
						Art.playSound(Art.BOING);
						infoPageL = new InfoPageLayer();
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
					//  Auto-generated method stub
					
				}
				
			});
	     
	     
			 //bl = new BubblesLayer(layer);
	 }
	 
	 @Override public void wasHidden () {
		 System.out.println("HomeScreen wasHidden");
	        super.wasHidden();
	        
	        //startSound.setLooping(false);
	        //startSound.stop();
	        
	        //iface.destroyRoot(_root);
	        while (layer.size() > 0) {
	        	layer.get(0).destroy();
	        }
	        c = null;
	        cl = null;
	        //bl = null;
	    }
	
	@Override
	public void update(float delta) {
		/*if (bl != null) {
			bl.update(delta);
		}*/
		if (c != null) {
			c.update(delta);
		}
		if (cl != null) {
			cl.update(delta);
		}
		
		if (infoPageL!=null && infoPageL.il.destroyed()) {
			  infoPageL = null;
			  
		  }
		
	}
	
	public void backToHome() {
		_screens.popTo(this);
		
		Launcher.gameStarted = false;
		launcher.showAds();
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
	  		
	           if (infoPageL!=null) {
	    		  infoPageL.destroy();
	    	   } else {
	    		  launcher.closeLauncher();
	    	  }
	  	  }
	    }
		
	}

}
