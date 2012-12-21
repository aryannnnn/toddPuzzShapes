package co.romesoft.core.draggable;

import co.romesoft.core.Art.type;


public class DragSounds {

	public static type fromImageToType(final String image) {
		
		if (image.contains("/2a.png")||image.contains("/3b.png")||image.contains("/18a.png")
				||image.contains("/16c.png")) {
			return type.BABY;
		} else if (image.contains("/6c.png")) {
			return type.GUITAR;
		} else if (image.contains("/7c.png")||image.contains("/11a.png")||image.contains("/12c.png")) {
			return type.CAR;
		} else if (image.contains("/8a.png")) {
			return type.CHICKEN;
		} else if (image.contains("/8c.png")) {
			return type.PLANE;
		} else if (image.contains("/9b.png")||image.contains("/10c.png")) {
			return type.DOG;
		} else if (image.contains("/9c.png")) {
			return type.BOAT;
		} else if (image.contains("/11c.png")||image.contains("/19c.png")) {
			return type.DRUMS;
		} else if (image.contains("/12b.png")||image.contains("/14b.png")) {
			return type.TRUCK;
		} else if (image.contains("/13c.png")||image.contains("/15b.png")||image.contains("/4a.png")) {
			return type.TRAIN;
		} else if (image.contains("/14a.png")) {
			return type.HELI;
		} else if (image.contains("/16a.png")||image.contains("/10b.png")) {
			return type.HORSE;
		} else if (image.contains("/20c.png")||image.contains("/17a.png")) {
			return type.BIPLANE;
		} else if (image.contains("/17b.png")||image.contains("/2b.png")||image.contains("/8b.png")) {
			return type.BALL;
		} else if (image.contains("/14c.png")) {
			return type.MOTORBOAT;
		} else if (image.contains("/7b.png")) {
			return type.ROBOT;
		} else if (image.contains("/5c.png")||image.contains("/16b.png")) {
			return type.DUCK;
		}
		
		return type.RANDOM;
	}
}
