package co.romesoft.core.draggable;

import co.romesoft.core.Art.type;


public class DragSounds {

	public static type fromImageToType(final String image) {
		
		if (image.contains("/1a.png")||image.contains("/6c.png")) {
			return type.BIKE;
			
		} else if (image.contains("/1b.png")||image.contains("/11a.png")) {
			return type.BOAT;
			
		} else if (image.contains("/1c.png")
				||image.contains("/4b.png")
				||image.contains("/6a.png")||image.contains("/8a.png")
				||image.contains("/10c.png")||image.contains("/11b.png")
				||image.contains("/12a.png")
				||image.contains("/14b.png")
				||image.contains("/15b.png")
				||image.contains("/15c.png")
				||image.contains("/16b.png")
				||image.contains("/16c.png")
				||image.contains("/17a.png")) {
			return type.TRUCK;
			
		} else if (image.contains("/2a.png")||image.contains("/12b.png")) {
			return type.BUS;
			
		} else if (image.contains("/2b.png")||image.contains("/8b.png")||image.contains("/13a.png")
				||image.contains("/16a.png")) {
			return type.EXCA;
			
		} else if (image.contains("/2c.png")||image.contains("/10a.png")) {
			return type.HELI;
			
		} else if (image.contains("/3a.png")||image.contains("/5c.png")||image.contains("/8c.png")
				||image.contains("/12c.png")||image.contains("/15a.png")) {
			return type.CAR;
			
		} else if (image.contains("/3b.png")||image.contains("/6b.png")||image.contains("/9c.png")) {
			return type.MULETTO;
			
		} else if (image.contains("/3c.png")||image.contains("/7a.png")||image.contains("/9b.png")
				||image.contains("/14a.png")) {
			return type.PLANE;
			
		} else if (image.contains("/4a.png")) {
			return type.MOTO;
			
		} else if (image.contains("/4c.png")||image.contains("/9a.png")) {
			return type.TRAIN;
			
		} else if (image.contains("/5a.png")||image.contains("/7c.png")||image.contains("/10b.png")
				||image.contains("/13b.png")||image.contains("/14c.png")) {
			return type.VAN;
			
		} else if (image.contains("/5b.png")||image.contains("/7b.png")||image.contains("/11c.png")
				||image.contains("/13c.png")) {
			return type.TRACTOR;
		} else if (image.contains("/17b.png")||image.contains("/19b.png")) {
			return type.POLICE;
			
		} else if (image.contains("/17c.png")||image.contains("/18b.png")||image.contains("/19a.png")) {
			return type.RACE_CAR;
			
		} else if (image.contains("/18a.png")||image.contains("/19c.png")) {
			return type.FIRETRUCK;
			
		} else if (image.contains("/18c.png")) {
			return type.AMBULANCE;
			
		}
		
		return type.CAR;
	}
}
