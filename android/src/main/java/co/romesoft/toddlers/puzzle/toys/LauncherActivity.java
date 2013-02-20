package co.romesoft.toddlers.puzzle.toys;

import static playn.core.PlayN.storage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

//import com.amazon.inapp.purchasing.PurchasingManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;
//import playn.android.AndroidCanvasImage;
import playn.android.GameActivity;
import playn.core.CanvasImage;
import playn.core.PlayN;

import co.romesoft.core.InfoPageLayer;
import co.romesoft.core.Launcher;
import co.romesoft.core.LauncherInterface;
import co.romesoft.toddlers.puzzle.toys.LauncherActivity;
import co.romesoft.toddlers.puzzle.toys.bill.BillingService;
import co.romesoft.toddlers.puzzle.toys.bill.Consts;
import co.romesoft.toddlers.puzzle.toys.bill.Consts.PurchaseState;
import co.romesoft.toddlers.puzzle.toys.bill.Consts.ResponseCode;
import co.romesoft.toddlers.puzzle.toys.bill.PurchaseDatabase;
import co.romesoft.toddlers.puzzle.toys.bill.PurchaseObserver;
import co.romesoft.toddlers.puzzle.toys.bill.ResponseHandler;

public class LauncherActivity extends GameActivity implements LauncherInterface{

	  private Launcher game;
	  
	  private Handler h = new LauncherActivityHandler();
		
	  @Override
	  public void main() {
	    //platform().assets().setPathPrefix("co/romesoft/resources");
	    game = new Launcher(this);
		PlayN.run(game);
		
		initializeOwnedItems();
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
		
		
		private void showPopupNormal(boolean maxSaved) {

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
		}
		

		
		
		///////////////////////////////INAPP AMAZON/////////////////////////
		public final String SKU_CONSUMABLE = "unlocker";
		
		// Keys for our shared prefrences
	    static final String UNLOCKED = "puzzletoysUnlocked";
	    
	    static final String unlockVal = "puzzletoysyeaahh2";
	    
		// currently logged in user
	   /* private String currentUser;   
	     
	    // Mapping of our requestIds to unlockable content
	    public Map<String, String> requestIds = new HashMap<String, String>();;
	    
	    public int numClicks;*/
	    
	    /**
	     * Update the UI for any purchases the customer has made.
	     */
	    public void update() {
	 
	        // Display the lock overlay on each swatch unless the customer has purchased it.
	        //final SharedPreferences settings = getSharedPreferencesForCurrentUser();
	         
	        // Display the number of remaining clicks
	        //numClicks = settings.getInt(UNLOCKED, 0);
	        int numClicks = mOwnedItems.size();
	        Log.d("UNLOCK", "numClicks "+numClicks);
	        
	        //clicksLeft.setText("" + numClicks);
	        if (numClicks > 0) {
	        	if (game!=null) {
	        	game.unlocked = true;
	        	hideAds();
	        	
	        	
	    		if (!game.gameStarted) {
	    			game.showInitMenu = true;
	    		}
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
	    
	    public void refund() {
	    	 	if (game!=null) {
	        	game.unlocked = false;
	        	showAds();
	        	
	        	
	    		if (!game.gameStarted) {
	    			game.showInitMenu = true;
	    		}
	    	 	}
	    		
	    		try {
					//SharedPreferences settings2 = getSharedPreferences(UNLOCKED, 0);
	    			//SharedPreferences settings2 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	    			SharedPreferences settings2 = getPreferences(0);
	    			settings2.edit().remove(UNLOCKED).commit();
				} catch (Exception e) {
					Log.d("UNLOCK", "refund SharedPreferences Exception "+e.toString());
				}
	    		
	    		try {
					String FILENAME = unlockVal;
					String string = "none";
					FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
					fos.write(string.getBytes());
					fos.close();
					deleteFile(FILENAME);
				} catch (Exception e) {
					Log.d("UNLOCK", "refund setting FileOutputStream Exception "+e.toString());
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
	    /*private void storeRequestId(String requestId, String key) {
	        requestIds.put(requestId, key);
	    }*/
	     
	    /**
	     * Get the SharedPreferences file for the current user.
	     * @return SharedPreferences file for a user.
	     */
	    /*private SharedPreferences getSharedPreferencesForCurrentUser() {
	        final SharedPreferences settings = getSharedPreferences(currentUser, Context.MODE_PRIVATE);
	        return settings;
	    }*/
	     
	    /**
	     * Generate a SharedPreferences.Editor object.
	     * @return editor for Shared Preferences file.
	     */
	    /*private SharedPreferences.Editor getSharedPreferencesEditor(){
	        return getSharedPreferencesForCurrentUser().edit();
	    }*/
	     
	    /**
	     * Gets current logged in user
	     * @return current user
	     */
	    /*String getCurrentUser(){
	        return currentUser;
	    }*/
	     
	    /**
	     * Sets current logged in user
	     * @param currentUser current user to set
	     */
	    /*void setCurrentUser(final String currentUser){
	        this.currentUser = currentUser;
	    }*/
	 
	    /**
	     * Whenever the application regains focus, the observer is registered again.
	     */
	    /*
	    @Override
	    public void onStart() {
	        super.onStart();
	        LauncherInAppObserver buttonClickerObserver = new LauncherInAppObserver(this);
	        PurchasingManager.registerObserver(buttonClickerObserver);
	    }
	    */
	    /**
	     * When the application resumes the application checks which customer is signed in.
	     */
	   /* @Override
	    protected void onResume() {
	        super.onResume();
	        //PurchasingManager.initiateGetUserIdRequest();
	    };*/
	    
	  
	    // show google dialog
	    /*
	  	private void showPopup(boolean maxSaved) {
	  		String requestId =
	  	            PurchasingManager.initiatePurchaseRequest(SKU_CONSUMABLE);
	  	        storeRequestId(requestId, UNLOCKED);
	  	}*/

		@Override
		public boolean isUnlocked() {
			//
			 /*CatalogEntry entry = mCatalog[position];
	         if (entry.managed == Managed.MANAGED && mOwnedItems.contains(entry.sku)) {
	             return false;
	         }
	         return true;
			
			*/
			
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
			 		"If your kid likes this game, you can buy the full version in the store with more drawings and No Ads." +
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
			.setPositiveButton("Buy the FULL version in the store", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
				
					try {
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setData(Uri.parse(InfoPageLayer.FULL_URL_1));
						startActivity(intent);
					} catch (Exception e) {
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setData(Uri.parse(InfoPageLayer.FULL_URL_2));
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
		
		
		//////////////////INAPP GOO/////////////////////////////////////////////////////////
		boolean billingSupported = false;
		
		private static final String TAG = "INAPP";
		
		private static final String DB_INITIALIZED = "db_initialized";

	    private DungeonsPurchaseObserver mDungeonsPurchaseObserver;
	    
	    private Handler mHandler;
	    
	    private BillingService mBillingService;
	    
	    private PurchaseDatabase mPurchaseDatabase;
	    
	    private Cursor mOwnedItemsCursor;
	    private Set<String> mOwnedItems = new HashSet<String>();
	    
	    private String mPayloadContents = null;
	    
	    private String mItemName;
	    private String mSku;
	    //private CatalogAdapter mCatalogAdapter;

	    private static final int DIALOG_CANNOT_CONNECT_ID = 1;
	    private static final int DIALOG_BILLING_NOT_SUPPORTED_ID = 2;

	    /**
	     * Each product in the catalog is either MANAGED or UNMANAGED.  MANAGED
	     * means that the product can be purchased only once per user (such as a new
	     * level in a game). The purchase is remembered by Android Market and
	     * can be restored if this application is uninstalled and then
	     * re-installed. UNMANAGED is used for products that can be used up and
	     * purchased multiple times (such as poker chips). It is up to the
	     * application to keep track of UNMANAGED products for the user.
	     */
	    private enum Managed { MANAGED, UNMANAGED }
	    
	    /**
	     * A {@link PurchaseObserver} is used to get callbacks when Android Market sends
	     * messages to this application so that we can update the UI.
	     */
	    private class DungeonsPurchaseObserver extends PurchaseObserver {
	        public DungeonsPurchaseObserver(Handler handler) {
	            super(LauncherActivity.this, handler);
	        }

	        @Override
	        public void onBillingSupported(boolean supported) {
	            try {
					if (Consts.DEBUG) {
					    Log.i(TAG, "supported: " + supported);
					}
					if (supported) {
					    restoreDatabase();
					    //mBuyButton.setEnabled(true);
					    //mEditPayloadButton.setEnabled(true);
					    billingSupported = true;
					} else {
						billingSupported = false;
					    //showDialog(DIALOG_BILLING_NOT_SUPPORTED_ID);
					}
				} catch (Exception e) {
					
				}
	        }

	        @Override
	        public void onPurchaseStateChange(PurchaseState purchaseState, String itemId,
	                int quantity, long purchaseTime, String developerPayload) {
	            
	        	try {
					if (Consts.DEBUG) {
					    Log.i(TAG, "onPurchaseStateChange() itemId: " + itemId + " " + purchaseState);
					}

					if (developerPayload == null) {
					    logProductActivity(itemId, purchaseState.toString());
					} else {
					    logProductActivity(itemId, purchaseState + "\n\t" + developerPayload);
					}

					if (purchaseState == PurchaseState.PURCHASED) {
					    mOwnedItems.add(itemId);
					    update();
					} else if (purchaseState == PurchaseState.REFUNDED) {
					    refund();
					}
					
					//mCatalogAdapter.setOwnedItems(mOwnedItems);
					for (String iterable_element : mOwnedItems) {
						Log.i(TAG, "OwnedItem: "+iterable_element);
					}
					mOwnedItemsCursor.requery();
				} catch (Exception e) {
					
				}
	        }

	        @Override
	        public void onRequestPurchaseResponse(BillingService.RequestPurchase request,
	                ResponseCode responseCode) {
	        	
	            try {
					if (Consts.DEBUG) {
					    Log.d(TAG, request.mProductId + ": " + responseCode);
					}
					if (responseCode == ResponseCode.RESULT_OK) {
					    if (Consts.DEBUG) {
					        Log.i(TAG, "purchase was successfully sent to server");
					    }
					    logProductActivity(request.mProductId, "sending purchase request");
					} else if (responseCode == ResponseCode.RESULT_USER_CANCELED) {
					    if (Consts.DEBUG) {
					        Log.i(TAG, "user canceled purchase");
					    }
					    logProductActivity(request.mProductId, "dismissed purchase dialog");
					} else {
					    if (Consts.DEBUG) {
					        Log.i(TAG, "purchase failed");
					    }
					    logProductActivity(request.mProductId, "request purchase returned " + responseCode);
					}
				} catch (Exception e) {
					
				}
	            
	        }

	        @Override
	        public void onRestoreTransactionsResponse(BillingService.RestoreTransactions request, 
	                ResponseCode responseCode) {
	        	
	            try {
					if (responseCode == ResponseCode.RESULT_OK) {
					    if (Consts.DEBUG) {
					        Log.d(TAG, "completed RestoreTransactions request");
					    }
					    // Update the shared preferences so that we don't perform
					    // a RestoreTransactions again.
					    SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
					    SharedPreferences.Editor edit = prefs.edit();
					    edit.putBoolean(DB_INITIALIZED, true);
					    edit.commit();
					} else {
					    if (Consts.DEBUG) {
					        Log.d(TAG, "RestoreTransactions error: " + responseCode);
					    }
					}
				} catch (Exception e) {
					
				}
	            
	        }
	    }

	    private static class CatalogEntry {
	        public String sku;
	        public int nameId;
	        public Managed managed;

	        public CatalogEntry(String sku, int nameId, Managed managed) {
	            this.sku = sku;
	            this.nameId = nameId;
	            this.managed = managed;
	        }
	    }
	    
	    /** An array of product list entries for the products that can be purchased. */
	    private static final CatalogEntry[] CATALOG = new CatalogEntry[] {
	        new CatalogEntry("sword_001", 1, Managed.MANAGED),
	        new CatalogEntry("potion_001", 2, Managed.UNMANAGED),
	        new CatalogEntry("android.test.purchased", 3,
	                Managed.UNMANAGED),
	        new CatalogEntry("android.test.canceled", 4,
	                Managed.UNMANAGED),
	        new CatalogEntry("android.test.refunded", 5,
	                Managed.UNMANAGED),
	        new CatalogEntry("android.test.item_unavailable", 6,
	                Managed.UNMANAGED),
	    };
	    
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	    	
	    	super.onCreate(savedInstanceState);
	        
	        try {
				mHandler = new Handler();
				mDungeonsPurchaseObserver = new DungeonsPurchaseObserver(mHandler);
				mBillingService = new BillingService();
				mBillingService.setContext(this);

				mPurchaseDatabase = new PurchaseDatabase(this);
				setupWidgets();

				// Check if billing is supported.
				ResponseHandler.register(mDungeonsPurchaseObserver);
				if (!mBillingService.checkBillingSupported()) { //TODO fix
				    //showDialog(DIALOG_CANNOT_CONNECT_ID);
					billingSupported = false;
				}
			} catch (Exception e) {
				
			}
	        
	    }
	    
	    /**
	     * Called when this activity becomes visible.
	     */
	    @Override
	    protected void onStart() {
	        super.onStart();
	        
	        try {
				ResponseHandler.register(mDungeonsPurchaseObserver);
				//initializeOwnedItems();
			} catch (Exception e) {
				
			}
	        
	    }
	    
	    /**
	     * Called when this activity is no longer visible.
	     */
	    @Override
	    protected void onStop() {
	        super.onStop();
	        
	        try {
				ResponseHandler.unregister(mDungeonsPurchaseObserver);
			} catch (Exception e) {
				
			}
	        
	    }

	    @Override
	    protected void onDestroy() {
	    	System.gc();
	        Runtime.getRuntime().gc();
	        
	        try {
				mPurchaseDatabase.close();
				mBillingService.unbind();
			} catch (Exception e) {
				
			}
	        
	        super.onDestroy();
	    }
	    
	    @Override
	    protected Dialog onCreateDialog(int id) {
	        switch (id) {
	        case DIALOG_CANNOT_CONNECT_ID:
	            return createDialog("Can\'t connect to Market",
	                    "This app cannot connect to Market. Your version of Market may be out of date. You can continue to use this app but you won\'t be able to make purchases.");
	        case DIALOG_BILLING_NOT_SUPPORTED_ID:
	            return createDialog("Can\'t make purchases",
	                    "The Market billing service is not available at this time.  You can continue to use this app but you won\'t be able to make purchases.");
	        default:
	            return null;
	        }
	    }
	    
	    
	    private Dialog createDialog(String titleId, String messageId) {
	        String helpUrl = replaceLanguageAndRegion("http://market.android.com/support/bin/answer.py?answer=1050566&amp;hl=%lang%&amp;dl=%region%");
	        if (Consts.DEBUG) {
	            Log.i(TAG, helpUrl);
	        }
	        final Uri helpUri = Uri.parse(helpUrl);

	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle(titleId)
	            .setIcon(android.R.drawable.stat_sys_warning)
	            .setMessage(messageId)
	            .setCancelable(false)
	            .setPositiveButton(android.R.string.ok, null)
	            .setNegativeButton("Learn more", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
	                    Intent intent = new Intent(Intent.ACTION_VIEW, helpUri);
	                    startActivity(intent);
	                }
	            });
	        return builder.create();
	    }
	    
	    /**
	     * Replaces the language and/or country of the device into the given string.
	     * The pattern "%lang%" will be replaced by the device's language code and
	     * the pattern "%region%" will be replaced with the device's country code.
	     *
	     * @param str the string to replace the language/country within
	     * @return a string containing the local language and region codes
	     */
	    private String replaceLanguageAndRegion(String str) {
	        // Substitute language and or region if present in string
	        if (str.contains("%lang%") || str.contains("%region%")) {
	            Locale locale = Locale.getDefault();
	            str = str.replace("%lang%", locale.getLanguage().toLowerCase());
	            str = str.replace("%region%", locale.getCountry().toLowerCase());
	        }
	        return str;
	    }
	    
	    private void setupWidgets() {
	    	mOwnedItemsCursor = mPurchaseDatabase.queryAllPurchasedItems();
	        startManagingCursor(mOwnedItemsCursor);
	        String[] from = new String[] { PurchaseDatabase.PURCHASED_PRODUCT_ID_COL,
	                PurchaseDatabase.PURCHASED_QUANTITY_COL
	        };
	        
	    }
	    
	    
	    private void prependLogEntry(CharSequence cs) {
	        SpannableStringBuilder contents = new SpannableStringBuilder(cs);
	        contents.append('\n');
	        
	        //contents.append(mLogTextView.getText());
	        //mLogTextView.setText(contents);
	        Log.i(TAG, "contents: " + contents);
	    }

	    private void logProductActivity(String product, String activity) {
	        SpannableStringBuilder contents = new SpannableStringBuilder();
	        contents.append(Html.fromHtml("<b>" + product + "</b>: "));
	        contents.append(activity);
	        prependLogEntry(contents);
	    }
	    
	    /**
	     * If the database has not been initialized, we send a
	     * RESTORE_TRANSACTIONS request to Android Market to get the list of purchased items
	     * for this user. This happens if the application has just been installed
	     * or the user wiped data. We do not want to do this on every startup, rather, we want to do
	     * only when the database needs to be initialized.
	     */
	    private void restoreDatabase() {
	    	
	        try {
				SharedPreferences prefs = getPreferences(MODE_PRIVATE);
				boolean initialized = prefs.getBoolean(DB_INITIALIZED, false);
				if (!initialized) {
				    mBillingService.restoreTransactions();
				    //Toast.makeText(this, "Restoring transactions", Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				
			}
	        
	    }
	    
	    /**
	     * Creates a background thread that reads the database and initializes the
	     * set of owned items.
	     */
	    private void initializeOwnedItems() {
	    	
	        try {
				new Thread(new Runnable() {
				    public void run() {
				        doInitializeOwnedItems();
				    }
				}).start();
			} catch (Exception e) {
				
			}
	        
	    }
	    
	    /**
	     * Reads the set of purchased items from the database in a background thread
	     * and then adds those items to the set of owned items in the main UI
	     * thread.
	     */
	    private void doInitializeOwnedItems() {
	    	
	        try {
				Cursor cursor = mPurchaseDatabase.queryAllPurchasedItems();
				if (cursor == null) {
				    return;
				}

				final Set<String> ownedItems = new HashSet<String>();
				try {
				    int productIdCol = cursor.getColumnIndexOrThrow(
				            PurchaseDatabase.PURCHASED_PRODUCT_ID_COL);
				    while (cursor.moveToNext()) {
				        String productId = cursor.getString(productIdCol);
				        ownedItems.add(productId);
				    }
				} finally {
				    cursor.close();
				}

				// We will add the set of owned items in a new Runnable that runs on
				// the UI thread so that we don't need to synchronize access to
				// mOwnedItems.
				mHandler.post(new Runnable() {
				    public void run() {
				        mOwnedItems.addAll(ownedItems);
				        if (!isUnlocked()) update();
				        //mCatalogAdapter.setOwnedItems(mOwnedItems);
				        Log.i(TAG, "ownedItems: " + ownedItems);
				    }
				});
			} catch (Exception e) {
				
			}
	        
	    }
	    
	  
	  	private void showPopup(boolean maxSaved) {
	  		
	  		try {
				//if (Consts.DEBUG) {
				Log.d(TAG, "buying: " + mItemName + " sku: " + mSku);
				    
				//mSku = CATALOG[2].sku;
				mSku = SKU_CONSUMABLE;
				//}
				if (!mBillingService.requestPurchase(mSku, mPayloadContents)) {
					billingSupported = false;
					Log.d(TAG, "DIALOG_BILLING_NOT_SUPPORTED_ID");
				    //showDialog(DIALOG_BILLING_NOT_SUPPORTED_ID);
				    //show FULL classic dialog
				    showPopupNormal(maxSaved);
				}
			} catch (Exception e) {
				showPopupNormal(maxSaved);
			}
	        
	  	}
	    
	    

		@Override
		public float getScreenDensity() {
			
			try {
				return getResources().getDisplayMetrics().density;
			} catch (Exception e) {
			}
			
			return 1;
		}
  
  
}
