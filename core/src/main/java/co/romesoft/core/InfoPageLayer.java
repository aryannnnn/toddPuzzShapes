package co.romesoft.core;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import playn.core.CanvasImage;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Platform;
import playn.core.PlayN;
import playn.core.ResourceCallback;
import playn.core.Pointer.Event;


public class InfoPageLayer {
	public static final String FULL_URL_1 = "itms-apps://itunes.apple.com/app/id594444436";
	public static final String FULL_URL_2 = "http://itunes.apple.com/app/id594444436?mt=8";
	
	public static final String LITE_URL_1 = "itms-apps://itunes.apple.com/app/id594445572";
	public static final String LITE_URL_2 = "http://itunes.apple.com/app/id594445572?mt=8";

	public static final String SEARCH_URL_1 = "itms-apps://itunes.com/apps/StefanoFrassi";
	public static final String SEARCH_URL_2 = "http://itunes.com/apps/StefanoFrassi";
	/*
	public static final String FULL_URL_1 = "market://details?id=co.romesoft.toddlers.puzzle.toysFull";
	public static final String FULL_URL_2 = "http://play.google.com/store/apps/details?id=co.romesoft.toddlers.puzzle.toysFull";
	
	public static final String LITE_URL_1 = "market://details?id=co.romesoft.toddlers.puzzle.toys";
	public static final String LITE_URL_2 = "http://play.google.com/store/apps/details?id=co.romesoft.toddlers.puzzle.toys";

	public static final String SEARCH_URL_1 = "market://search?q=pub:romeLab";
	public static final String SEARCH_URL_2 = "http://play.google.com/store/search?q=pub:romeLab";
	*/
	/*
	public static final String FULL_URL_1 = "amzn://apps/android?p=co.romesoft.toddlers.puzzle.toysFull";
	public static final String FULL_URL_2 = "http://www.amazon.com/gp/mas/dl/android?p=co.romesoft.toddlers.puzzle.toysFull";
	
	public static final String LITE_URL_1 = "amzn://apps/android?p=co.romesoft.toddlers.puzzle.toys";
	public static final String LITE_URL_2 = "http://www.amazon.com/gp/mas/dl/android?p=co.romesoft.toddlers.puzzle.toys";

	public static final String SEARCH_URL_1 = "amzn://apps/android?p=co.romesoft.toddlers.zoo&showAll=1";
	public static final String SEARCH_URL_2 = "http://www.amazon.com/gp/mas/dl/android?p=co.romesoft.toddlers.zoo&showAll=1";
	*/
	
	Image infoImage;
	//CanvasImage ci;
	public ImageLayer il;
	
	CanvasImage backG;
	ImageLayer il2;
	
	
	public InfoPageLayer() {
		backG = graphics().createImage(Launcher.width, Launcher.height);
		backG.canvas().setFillColor(0xFF000000);
		backG.canvas().fillRect(0, 0, Launcher.width, Launcher.height);
		
		il2 = graphics().createImageLayer(backG);
		il2.setAlpha(0.5F);
		graphics().rootLayer().add(il2);
		
		
		infoImage = assets().getImage("images/infoPage.png");
		infoImage.addCallback(new ResourceCallback<Image>() {
			
			@Override
			public void error(Throwable err) {
			
			}
			
			@Override
			public void done(Image resource) {
				il = graphics().createImageLayer(resource);
				//if (Launcher.height <= 320) {
					il.setScale(Launcher.multHeight);
				//}
				il.setTranslation((Launcher.width-il.scaledWidth())/2, (Launcher.height-il.scaledHeight())/2);
			    
				graphics().rootLayer().add(il);
			}
		});
		
		//ci =  graphics().createImage(Launcher.width, Launcher.height);
		//il = graphics().createImageLayer(ci);
		//graphics().rootLayer().add(il);
	}
	
	/*
	public void setColor(int color) {
		ci.canvas().clear();
		if (color == 0xFFFFFFFF) {
			//eraser
			ci.canvas().drawImage(Launcher.eraserImage, (Launcher.width-Launcher.eraserImage.width())/2+10, (Launcher.height-Launcher.eraserImage.height())/2);
		} else {
			ci.canvas().setFillColor(color);
			ci.canvas().fillCircle((Launcher.width-100)/2+10, (Launcher.height-100)/2, 50);
		}
	}
	*/
	
	public void onPointerStart(Event event) {
		
		
		
		if (event.x() > il.transform().tx() && event.x() < il.transform().tx() + il.scaledWidth()
				&& event.y() > il.transform().ty() && event.y() < il.transform().ty() + il.scaledHeight()) {
			
			////System.out.println("inside"); 
			float relHeight = event.y() - il.transform().ty();
			float spacePerLink = il.scaledHeight() / 4;
			
			if (relHeight<spacePerLink) {
				////System.out.println("open url 1");
				try {
					PlayN.openURL(FULL_URL_1);
				} catch (Exception e) {
					PlayN.openURL(FULL_URL_2);
				}
				// mailto:rome.soft.co@gmail.com
				// PlayN.openURL("mailto:rome.soft.co@gmail.com");
				//AMAZON
				//PlayN.openURL("http://www.amazon.com/gp/mas/dl/android?p=co.romesoft.toddlers.puzzle.toysFull");
			} else if (relHeight<spacePerLink*2) {
				//System.out.println("open url 2");
				try {
					PlayN.openURL(LITE_URL_1);
				} catch (Exception e) {
					PlayN.openURL(LITE_URL_2);
				}
				//AMAZON
				//PlayN.openURL("http://www.amazon.com/gp/mas/dl/android?p=co.romesoft.toddlers.puzzle.toys");
			}else if (relHeight<spacePerLink*3) {
				//System.out.println("open url 3");
				try {
					PlayN.openURL(SEARCH_URL_1);
				} catch (Exception e) {
					PlayN.openURL(SEARCH_URL_2);
				}
				//AMAZON
				//PlayN.openURL("http://www.amazon.com/gp/mas/dl/android?p=co.romesoft.toddlers.zoo&showAll=1");
			}else if (relHeight<spacePerLink*4) {
				//System.out.println("open url 4");
				PlayN.openURL("http://gplus.to/romeLab");
			}
			
		} else {
			//System.out.println("outside");
			destroy();
		}
		
		////System.out.println("open url");
		//PlayN.openURL("http://www.google.it/");
	}
	
	public void destroy() {
		il.destroy();
		il2.destroy();
	}

}
