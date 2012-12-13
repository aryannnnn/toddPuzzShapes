package co.romesoft.core;

import playn.core.CanvasImage;

public interface LauncherInterface {

	public void closeLauncher();
	
	public void showLitePopup(boolean maxSaved);
	
	public void showAds();
	
	public void hideAds();
	
	public float getScreenDensity();
}
