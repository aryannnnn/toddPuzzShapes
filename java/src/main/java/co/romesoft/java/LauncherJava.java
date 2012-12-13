package co.romesoft.java;

import static playn.core.PlayN.storage;
import playn.core.CanvasImage;
import playn.core.PlayN;
import playn.java.JavaPlatform;

import co.romesoft.core.Launcher;
import co.romesoft.core.LauncherInterface;

public class LauncherJava implements LauncherInterface {

	private Launcher game;
	
	public void start() {
    JavaPlatform platform = JavaPlatform.register();
    platform.assets().setPathPrefix("co/romesoft/resources");
    game = new Launcher(this);
	PlayN.run(game);
  }
  
  public static void main(String[] args) {
	  LauncherJava l = new LauncherJava();
	  l.start();
  }
  
  @Override
  public void closeLauncher() {

  	System.out.println("app in background");
  	
  }

  @Override
  public void showLitePopup(boolean maxSaved) {

  	game.paused = true;
  	try {
  		if (maxSaved) {
  			System.out.println("only 2 savings! this is the FREE version.. buy it ?");
  		} else {
  			System.out.println("this is the FREE version.. buy it ?");
  		}
  		Thread.sleep(1000);
  		System.out.println("showLitePopup done");
  	} catch (InterruptedException e) {
  		game.paused = false;
  	}
  	game.paused = false;
  	
  }

  @Override
  public void showAds() {

  	System.out.println("showAds!");
  	
  }

  @Override
  public void hideAds() {

  	System.out.println("NO Ads");
  	
  }

 

  @Override
  public float getScreenDensity() {
  	
  	return 1;
  }
}
