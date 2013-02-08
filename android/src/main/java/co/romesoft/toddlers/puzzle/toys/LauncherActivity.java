package co.romesoft.toddlers.puzzle.toys;

import static playn.core.PlayN.storage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import playn.android.GameActivity;
import playn.core.PlayN;

import com.amazon.inapp.purchasing.PurchasingManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import co.romesoft.core.InfoPageLayer;
import co.romesoft.core.Launcher;
import co.romesoft.core.LauncherInterface;

public class LauncherActivity extends GameActivity implements LauncherInterface{

	  private Launcher game;
	  
	  private Handler h = new LauncherActivityHandler();
		
	  @Override
	  public void main() {
	    platform().assets().setPathPrefix("co/romesoft/resources");
	    game = new Launcher(this);
		PlayN.run(game);
	  }

	@Override
	public void closeLauncher() {
		//this.finish();
		this.moveTaskToBack(true);
		
		
	}

	@Override
	public void showLitePopup(boolean maxSaved) {
		//in pause
		//TODO game.paused = true;
		
		Message msg = h.obtainMessage();
	    Bundle b = new Bundle();
	    if (maxSaved) {
	    	b.putString("popupmaxSaved", "popupmaxSaved");
	    } else {
	    	b.putString("popup", "popup");
	    }
	    msg.setData(b);
	    
	    h.sendMessage(msg);
		/*
		android.app.AlertDialog.Builder ad = new AlertDialog.Builder(this);
		
		 ad.setMessage("If you like this game, please buy the supported version with more dogs and AD FREE in the market. Thanks for your support!")
		 .setPositiveButton("Buy", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
			
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("market://details?id=co.romesoft.toddlers.zooFull"));
				startActivity(intent);
				
			}
		})
		.setNegativeButton("No, thanks!", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
			
				game.paused = false;
				
			}
		})
		.setTitle("Buy")
		.show();*/
		
	}

	//a14f6c9bf562015

	@Override
	public void showAds() {
		//imAdView.setVisibility(View.VISIBLE);
		
		
		Message msg = handler.obtainMessage();
	    Bundle b = new Bundle();
	    b.putString("show", "shooowwwww");
	    msg.setData(b);
	    
	    handler.sendMessage(msg);
	    
	    
	}

	@Override
	public void hideAds() {
		//imAdView.setVisibility(View.GONE);
		
		Message msg = handler.obtainMessage();
	    Bundle b = new Bundle();
	    b.putString("hide", "hideeee");
	    msg.setData(b);
	    handler.sendMessage(msg);
	    
	}

		

		class LauncherActivityHandler extends Handler {

			@Override
			public void handleMessage(Message msg) {
				if (msg.getData().containsKey("popup")) {
					showPopup(false);
				} else if (msg.getData().containsKey("saveImage")) {
					//showPopupSaveImage();
				} else if (msg.getData().containsKey("popupmaxSaved")) {
					showPopup(true);
				}
				
			}
			
		}
		
		
		private static final String messageEN = "This is the FREE version of the app. " +
		 		"If your kid likes this game, you can buy the full version in the market with more puzzles/toys and without Advertising." +
		 		" Thanks for your support!";
		private static final String messageENSave = "You can store only three drawings in the free version. " +
		 		"If your kid likes this game, you can buy the full version in the market with more puzzles/toys and without Advertising." +
		 		" Thanks for your support!";
		private static String buyEN = "Buy";
		private static String noThanksEN = "No, thanks!";
		
		private static final String messageES = "Esta es la versión gratuita de la aplicación. " +
				"Si su hijo le gusta este juego, usted puede comprar la versión completa en el market con más rompecabezas/juguetes y sin publicidad." +
				" Gracias por tu apoyo!";
		private static final String messageESSave = "Puede almacenar sólo tres dibujos en la versión gratuita. " +
				"Si su hijo le gusta este juego, usted puede comprar la versión completa en el market con más rompecabezas/juguetes y sin publicidad." +
				" Gracias por tu apoyo!";
		private static String buyES = "Comprar";
		private static String noThanksES = "No, gracias!";
		
		private static final String messageIT = "Questa è la versione gratuita dell'applicazione. " +
				"Se al vostro bambino piace questo gioco, potete acquistare la versione completa sul market con più puzzle/giocattoli e senza pubblicità." +
				" Grazie per il vostro supporto!";
		private static final String messageITSave = "È possibile memorizzare solo tre disegni nella versione gratuita. " +
				"Se al vostro bambino piace questo gioco, potete acquistare la versione completa sul market con più puzzle/giocattoli e senza pubblicità." +
				" Grazie per il vostro supporto!";
		private static String buyIT = "Acquista";
		private static String noThanksIT = "No, grazie!";
				
		private static final String messageFR = "Ceci est la version gratuite de l'application. " +
				"Si votre enfant aime ce jeu, vous pouvez acheter la version complète sur le market avec plus de puzzle/jouets et sans publicité." +
				" Merci pour votre soutien!";
		private static final String messageFRSave = "Vous pouvez mémoriser que trois dessins dans la version gratuite. " +
				"Si votre enfant aime ce jeu, vous pouvez acheter la version complète sur le market avec plus de puzzle/jouets et sans publicité." +
				" Merci pour votre soutien!";
		private static String buyFR = "Achetez-le";
		private static String noThanksFR = "Non, merci!";
		
		private static final String messageDE = "Dies ist die kostenlose Version der App. " +
				"Ist Ihr Kind mag dieses Spiel, können Sie die Vollversion auf dem market mit mehr Puzzles/Spielzeug und ohne Werbung kaufen." +
				" Vielen Dank für Ihre Unterstützung!";
		private static final String messageDESave = "Sie können nur drei Zeichnungen in der kostenlosen Version zu speichern. " +
				"Ist Ihr Kind mag dieses Spiel, können Sie die Vollversion auf dem market mit mehr Puzzles/Spielzeug und ohne Werbung kaufen." +
				" Vielen Dank für Ihre Unterstützung!";
		private static String buyDE = "Kaufen";
		private static String noThanksDE = "Nein, danke!";
		
		
		//show amazon dialog
		/*
		private void showPopup(boolean maxSaved) {

			android.app.AlertDialog.Builder ad = new AlertDialog.Builder(this);
			//internationalize
			
			String message = messageEN;
			if (maxSaved) {
				message = messageENSave;
			}
			String buy = buyEN;
			String noThanks = noThanksEN;
			
			try {
				String lang = this.getResources().getConfiguration().locale.getLanguage();
				if (lang != null && lang.toLowerCase().contains("es")) {
					message = messageES;
					if (maxSaved) {
						message = messageESSave;
					}
					buy = buyES;
					noThanks = noThanksES;
				}
				if (lang != null && lang.toLowerCase().contains("it")) {
					message = messageIT;
					if (maxSaved) {
						message = messageITSave;
					}
					buy = buyIT;
					noThanks = noThanksIT;
				}
				if (lang != null && lang.toLowerCase().contains("fr")) {
					message = messageFR;
					if (maxSaved) {
						message = messageFRSave;
					}
					buy = buyFR;
					noThanks = noThanksFR;
				}
				if (lang != null && lang.toLowerCase().contains("de")) {
					message = messageDE;
					if (maxSaved) {
						message = messageDESave;
					}
					buy = buyDE;
					noThanks = noThanksDE;
				}
			} catch (Exception e) {
				
			}
			
			
			ad.setMessage(message)
			 .setPositiveButton(buy, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
				
					try {
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setData(Uri.parse(InfoPageLayer.FULL_URL_1));
						//amazon link
						//intent.setData(Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=co.romesoft.toddlers.dogFull"));
						startActivity(intent);
					} catch (Exception e) {
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setData(Uri.parse(InfoPageLayer.FULL_URL_2));
						startActivity(intent);
					}
					
					game.paused = false;
					
				}
			})
			.setNegativeButton(noThanks, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
				
					game.paused = false;
					
				}
			})
			.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					game.paused = false;
					return false;
				}
			})
			.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					game.paused = false;
					
				}
			})
			.setTitle(buy)
			.show();
		}*/
		
		
		///////////////////////////////INAPP AMAZON/////////////////////////
		public final String SKU_CONSUMABLE = "co.romesoft.toddlers.puzzle.toys.unlocker";
		
		// Keys for our shared prefrences
	    static final String UNLOCKED = "puzzletoysUnlocked";
	    
	    static final String unlockVal = "puzzletoysyeaahh2";
	    
		// currently logged in user
	    private String currentUser;   
	     
	    // Mapping of our requestIds to unlockable content
	    public Map<String, String> requestIds = new HashMap<String, String>();;
	    
	    public int numClicks;
	    
	    /**
	     * Update the UI for any purchases the customer has made.
	     */
	    public void update() {
	 
	        // Display the lock overlay on each swatch unless the customer has purchased it.
	        final SharedPreferences settings = getSharedPreferencesForCurrentUser();
	         
	        // Display the number of remaining clicks
	        numClicks = settings.getInt(UNLOCKED, 0);
	        
	        //clicksLeft.setText("" + numClicks);
	        if (numClicks > 0) {
	        	game.unlocked = true;
	        	hideAds();
	        	
	        	
	    		if (!game.gameStarted) {
	    			game.showInitMenu = true;
	    		}
	    		
	    		try {
					//SharedPreferences settings2 = getSharedPreferences(UNLOCKED, 0);
	    			//SharedPreferences settings2 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	    			SharedPreferences settings2 = getPreferences(0);
	    			settings2.edit().putString(UNLOCKED, unlockVal).commit();
				} catch (Exception e) {
					Log.d("UNLOCK", "setting SharedPreferences Exception "+e.toString());
				}
	    		
	    		try {
					String FILENAME = unlockVal;
					String string = unlockVal;
					FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
					fos.write(string.getBytes());
					fos.close();
				} catch (Exception e) {
					Log.d("UNLOCK", "setting FileOutputStream Exception "+e.toString());
				}
	    		
	        }
	    }
		
		/**
	     * Helper method to associate request ids to shared preference keys
	     *
	     * @param requestId
	     *            Request ID returned from a Purchasing Manager Request
	     * @param key
	     *            Key used in shared preferences file
	     */
	    private void storeRequestId(String requestId, String key) {
	        requestIds.put(requestId, key);
	    }
	     
	    /**
	     * Get the SharedPreferences file for the current user.
	     * @return SharedPreferences file for a user.
	     */
	    private SharedPreferences getSharedPreferencesForCurrentUser() {
	        final SharedPreferences settings = getSharedPreferences(currentUser, Context.MODE_PRIVATE);
	        return settings;
	    }
	     
	    /**
	     * Generate a SharedPreferences.Editor object.
	     * @return editor for Shared Preferences file.
	     */
	    private SharedPreferences.Editor getSharedPreferencesEditor(){
	        return getSharedPreferencesForCurrentUser().edit();
	    }
	     
	    /**
	     * Gets current logged in user
	     * @return current user
	     */
	    String getCurrentUser(){
	        return currentUser;
	    }
	     
	    /**
	     * Sets current logged in user
	     * @param currentUser current user to set
	     */
	    void setCurrentUser(final String currentUser){
	        this.currentUser = currentUser;
	    }
	 
	    /**
	     * Whenever the application regains focus, the observer is registered again.
	     */
	    @Override
	    public void onStart() {
	        super.onStart();
	        LauncherInAppObserver buttonClickerObserver = new LauncherInAppObserver(this);
	        PurchasingManager.registerObserver(buttonClickerObserver);
	    }
	    
	    /**
	     * When the application resumes the application checks which customer is signed in.
	     */
	    @Override
	    protected void onResume() {
	        super.onResume();
	        PurchasingManager.initiateGetUserIdRequest();
	    };
	    
	  
	    //TODO show amazon dialog
	  	private void showPopup(boolean maxSaved) {
	  		String requestId =
	  	            PurchasingManager.initiatePurchaseRequest(SKU_CONSUMABLE);
	  	        storeRequestId(requestId, UNLOCKED);
	  	}

		@Override
		public boolean isUnlocked() {
			//Shared Preferences
			try {
				//SharedPreferences settings = getSharedPreferences(UNLOCKED, 0);
				//SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				SharedPreferences settings = getPreferences(0);
				String val = settings.getString(UNLOCKED, null);
				
				if (val!=null && val.equals(unlockVal)) {
					return true;
				} else {
					Log.d("UNLOCK", "isUnlocked SharedPreferences false");
				}
			} catch (Exception e) {
				Log.d("UNLOCK", "isUnlocked SharedPreferences Exception "+e.toString());
			}
			
			//Internal Storage
			try {
				String FILENAME = unlockVal;
				byte[] b = new byte[10];
				FileInputStream fos = openFileInput(FILENAME);
				fos.read(b);
				fos.close();
				
				String val = new String(b);
				if (val.startsWith(unlockVal)) {
					return true;
				} else {
					Log.d("UNLOCK", "isUnlocked Internal Storage false");
				}
				
			} catch (Exception e) {
				Log.d("UNLOCK", "isUnlocked Internal Storage Exception "+e.toString());
			} 
			
			
			return false;
		}
	  	
		
		public void showProblemInAppDialog() {
			String messageEN = "This is the FREE version featuring only the animals category. " +
			 		"If your kid likes this game, you can buy the full version in the AMAZON store with more drawings and No Ads." +
			 		" Thanks for your support!";
			//Unlock all the drawings, remove all the Ads

			android.app.AlertDialog.Builder ad = new AlertDialog.Builder(this);
			//"There was a problem with the In-App Purchasing, try later or buy the FULL version in the AMAZON store"
			
			
			ad.setMessage(messageEN)
			 .setNegativeButton("No, thanks!", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//showPopup(false);
				}
			})
			.setPositiveButton("Buy the FULL version in the AMAZON store", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
				
					try {
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setData(Uri.parse("amzn://apps/android?p=co.romesoft.toddlers.puzzle.toysFull"));
						startActivity(intent);
					} catch (Exception e) {
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setData(Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=co.romesoft.toddlers.puzzle.toysFull"));
						startActivity(intent);
					}
					
					
				}
			})
			.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					
					return false;
				}
			})
			.setTitle("In-App Purchasing problem")
			.show();
		}	
		
		
		

		@Override
		public float getScreenDensity() {
			
			try {
				return getResources().getDisplayMetrics().density;
			} catch (Exception e) {
			}
			
			return 1;
		}
		
		@Override
	    protected void onDestroy() {
			try {
		    	System.gc();
		        Runtime.getRuntime().gc();
			} catch (Exception e) {
				
			}
	        
	        super.onDestroy();
	    }
  
  
}
