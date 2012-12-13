package co.romesoft.core;


public class Utils {
	
	/**
	 * low</code> (inclusive) and <code>high</code> (exclusive)
	 * @param low
	 * @param high
	 * @return
	 */
    public static int getInRange (int low, int high) {
        return low + Launcher.r.nextInt(high - low);
    }

}
