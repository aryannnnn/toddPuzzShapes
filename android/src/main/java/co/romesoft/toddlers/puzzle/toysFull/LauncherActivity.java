package co.romesoft.toddlers.puzzle.toysFull;

import playn.android.GameActivity;
import playn.core.PlayN;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
		game.paused = true;
		
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
		// TODO Auto-generated method stub
		//imAdView.setVisibility(View.VISIBLE);
		
		
		Message msg = handler.obtainMessage();
	    Bundle b = new Bundle();
	    b.putString("show", "shooowwwww");
	    msg.setData(b);
	    
	    handler.sendMessage(msg);
	    
	    
	}

	@Override
	public void hideAds() {
		// TODO Auto-generated method stub
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
