package co.romesoft.core;

import static playn.core.PlayN.*;

import java.util.Random;

import co.romesoft.core.screens.HomeScreen;

import playn.core.Game;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Platform;
import playn.core.PlayN;
import playn.core.Pointer.Event;
import playn.core.Pointer.Listener;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

public class Launcher implements Game {
	
	public static int width;
	public static int height;
	
	private float playingTime=0;
	
	public static float multHeight;
	public static float multWidth;
	
	private static final String TIME_SPENT_PERSISTED_NAME = "toddPuzzleToys";
	
	public static boolean unlocked = false;
	public static boolean showInitMenu = false;
	
	private static final int SHOW_LITE_POPUP_EVERY_MS = 60000*10; // 10 minutes
	
	private static final int SHOW_ADS_ALWAYS = 60000*60; // 60 minutes
	public static boolean showAdsAlways=false;
	
	private static final int SHOW_ADS_SOMETIMES = 60000*20; // 20 minutes
	public static boolean showAdsSometimes=false;
	
	public static float screenDensity;
	
	public static boolean gameStarted = false;
	public boolean paused = false;
	
	private final LauncherInterface launcher;
	
	public static final Random r = new Random();
	
	public Launcher(LauncherInterface li) {
  		super();
  		this.launcher = li;
  	}
	
	protected final ScreenStack _screens = new ScreenStack() {
        @Override protected void handleError (RuntimeException error) {
            PlayN.log().warn("Screen failure", error);
        }
        @Override protected Transition defaultPushTransition () {
            return slide();
        }
        @Override protected Transition defaultPopTransition () {
            return slide().right();
        }
    };
    HomeScreen home;
	
  @Override
  public void init() {
	  unlocked = launcher.isUnlocked();
	  
	  width = graphics().screenWidth();  //800
      height = graphics().screenHeight();//480
      
      /*
      width = 320; 
      height = 240;
      
      width = 400; 
      height = 240;
      
      width = 480; 
      height = 320;
      
      width = 800;
      height = 480;
      
      width = 1024; //kindle fire
      height = 600;
      
      width = 1280; //s3
      height = 720;
      
      width = 1280;
      height = 800;
      */
      //width = 400; 
      //height = 240;
      
      if (!unlocked) {
      showAdsAlways = true;
      }
      screenDensity = launcher.getScreenDensity();
      gameStarted = false;
	  
      if (!unlocked) {
      try {
    	  String item = storage().getItem(TIME_SPENT_PERSISTED_NAME);
    	  if (item!=null) {
    		  playingTime = Float.parseFloat(item);
    	  }
		} catch (Exception e1) {
			
		}
      }
		
        //based on the native assets resolution (bg is 320 h 480 w)
		multHeight = (float)((float)Launcher.height / (float)480);
		multWidth =  (float)((float)Launcher.width / (float)800);
	     
	   //graphics().setSize(width, height);
		if (platformType().equals(Platform.Type.JAVA)) {
    	   graphics().ctx().setSize(width, height);
        }
	   
	   home = new HomeScreen(_screens, launcher);
	   _screens.push(home);
    
    
       //graphics().rootLayer().add(home.layer);
	   if (!unlocked) {
		   launcher.showAds();
	   } else {
		   launcher.hideAds();
	   }
  }
  

  @Override
  public void paint(float alpha) {
	  _screens.paint(alpha);
  }

  @Override
  public void update(float delta) {
	  if (showInitMenu) {
		  home.wasHidden(); //reinit the whole screen or in the home.update check and only remove the unlock button
		  home.wasShown();
		  showInitMenu = false;
		  return;
	  }
	  if (paused) return;
	  
	  if (gameStarted) {
		  
		  if (!unlocked) {
		  playingTime+=delta;
		  
		  if (playingTime % 60000 == 0) {
			  //persist playingTime every minute
			  try {
				storage().setItem(TIME_SPENT_PERSISTED_NAME, playingTime+"");
			} catch (Exception e) {
				
			}
		  }
		  
		  if (playingTime % SHOW_LITE_POPUP_EVERY_MS == 0) {
			  launcher.showLitePopup(false);
		  }
		  
		  //ads based on time played
		  if (playingTime > SHOW_ADS_ALWAYS) {
			  showAdsAlways = true;
		  } else if (playingTime > SHOW_ADS_SOMETIMES) {
			  showAdsSometimes = true;
		  }
		  
		  }
	  }
	  
	  _screens.update(delta);
  }

  @Override
  public int updateRate() {
    return 25;  // 1000 / 25 = 40fps
  }
  
  
}
