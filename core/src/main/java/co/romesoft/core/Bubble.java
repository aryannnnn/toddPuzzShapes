package co.romesoft.core;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import playn.core.Canvas;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.ResourceCallback;
import playn.core.Canvas.Composite;


public class Bubble {
	
	//final float multHeight = (float)((float)Launcher.height / (float)480);
	
	private float xx;
	private float yy;
	//private Canvas cc;
	private ImageLayer dl;
	private int imgHeight;
	
	public boolean disposable = false;
	
	double size =  Math.random();
	double speed = Math.random() * 0.10;
	
	public Bubble(final GroupLayer g, final float x, final float y) {

		super();
		this.xx = x;
		this.yy = y;
		//this.cc=c;
		
		if (speed < 0.05) speed+=0.05;
		if (size < 0.5) size+=0.5;
		size*=0.25*Launcher.multHeight;
		speed*=Launcher.multHeight;
		
		Image dropImage = assets().getImage("images/bubble.png");
		
		dropImage.addCallback(new ResourceCallback<Image>() {
				
				@Override
				public void error(Throwable err) {
				
				}
				
				@Override
				public void done(Image resource) {
					dl = graphics().createImageLayer(resource);
					dl.setOrigin(resource.width()/2f, resource.height()/2f);
					dl.setTranslation(x, y);
					dl.setScale((float) size);
					dl.setAlpha(0.6F);
				    g.add(dl);
				    
				    imgHeight = (int) resource.height();
				}
			});
	}
	
	
	
	public void update(float delta) {
		yy = (float) (yy-delta*speed);
		dl.setTranslation(xx, yy);
		
		/*cc.setCompositeOperation(Composite.DST_OUT);
		
		cc.setFillColor(0x55000000);
		//ci.canvas().setAlpha(0F);
		cc.fillCircle(xx/Launcher.mult, yy/Launcher.mult, (float) (3*size));*/
		
		if (yy<(0-imgHeight )
				|| xx<0 || xx>Launcher.width) {
			disposable=true;
		}
	}
	
	public void  destroy() {
		dl.destroy();
	}

}
