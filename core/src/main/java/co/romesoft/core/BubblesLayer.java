package co.romesoft.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import playn.core.GroupLayer;
import playn.core.PlayN;

public class BubblesLayer {

	private static final int MAX_BUBBLES = 20;

	public final GroupLayer thisLayer = PlayN.graphics().createGroupLayer();

	List<Bubble> bubbles = new ArrayList<Bubble>();
	
	 private float menuTime=0;
	 
	 public BubblesLayer(final GroupLayer layer) {
		 layer.add(thisLayer);
	 }

	private void addBubble(Float x, Float y) {
		
		Bubble b = new Bubble(thisLayer, x, y);
		bubbles.add(b);
	}
	
	private boolean canAddMoreEntities() {
		if (bubbles.size() > MAX_BUBBLES) {
			return false;
		} else {
			return true;
		}
	}

	public void update(float delta) {
		menuTime+=delta;
		  if (menuTime==delta || (menuTime%600==0 && Math.random()>0.5)) {
			  if (canAddMoreEntities()) {
				  addBubble((float)(Math.random() * Launcher.width), (float)(Launcher.height - (Math.random() * 20)));
			  }
		  }
		  
		for (Bubble bubble : bubbles) {
			bubble.update(delta);
		}

		for (Iterator<Bubble> iterator = bubbles.iterator(); iterator.hasNext();) {
			Bubble drop = iterator.next();

			if (drop.disposable) {
				drop.destroy();
				iterator.remove();
			}

		}
	}

}
