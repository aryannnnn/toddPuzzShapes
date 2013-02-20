using System;
using MonoTouch.Foundation;
using MonoTouch.UIKit;

using playn.ios;
using playn.core;
using co.romesoft.core;
using AlexTouch.GoogleAdMobAds;
using System.Drawing;
using MonoTouch.CoreGraphics;
using System.Collections.Generic;
using NonConsumables;
using MonoTouch.StoreKit;
using System.Threading;

namespace co.romesoft
{
  [Register ("AppDelegate")]
  public partial class AppDelegate : IOSApplicationDelegate,LauncherInterface {  //or UIApplicationDelegate ?
		UIWindow w;
		UIView uiov;
		public static IOSPlatform pf;
		Launcher game;
		GADBannerView ad = null;

    public override bool FinishedLaunching (UIApplication app, NSDictionary options) {
      app.SetStatusBarHidden(true, true);
      pf = IOSPlatform.register(app, IOSPlatform.SupportedOrients.LANDSCAPES); //iPadLikePhone = false come default
      pf.assets().setPathPrefix("assets");
		w=	pf.window();
		uiov = pf.uiOverlay();
		
	

	 
      //init admob
        
		Console.WriteLine("width Screen.Bounds: "+w.Screen.Bounds.Height); //480
		Console.WriteLine("width pf: "+pf.graphics().screenWidth());
		Console.WriteLine("width ad banner: "+GADAdSizeCons.Banner.size.Width); //320
	    ad = new GADBannerView(GADAdSizeCons.Banner,new PointF((pf.graphics().screenWidth()/2)-(GADAdSizeCons.Banner.size.Width/2),0))
		{
			AdUnitID = "a150f851a323813",
			RootViewController = w.RootViewController //or your RootViewController  
		};
		
	
		ad.DidReceiveAd += delegate 
		{
			//this.View.AddSubview(ad);
	
			Console.WriteLine("AD Received");
	
			//w.AddSubview(ad);
			//w.fr
			//w.BringSubviewToFront(ad);
		};
		
		ad.DidFailToReceiveAdWithError += delegate(object sender, GADBannerViewDidFailWithErrorEventArgs e) {
			Console.WriteLine(e.Error);
		};
		
		ad.WillPresentScreen += delegate {
			Console.WriteLine("showing new screen");
		};
		
		ad.WillLeaveApplication += delegate {
			Console.WriteLine("I will leave application");
		};
		
		ad.WillDismissScreen += delegate {
			Console.WriteLine("Dismissing opened screen");
		};
	
		//ad.AutoresizingMask=UIViewAutoresizing.FlexibleLeftMargin|UIViewAutoresizing.FlexibleRightMargin;
		//this.View.AddSubview(ad);
		//w.AddSubview(ad);
		uiov.AddSubview(ad);
		//CGAffineTransform composite = CGAffineTransform.MakeTranslation(20, 0);
		//uiov.Transform = composite;
		//uiov.Frame=new RectangleF(0,0,w.Screen.Bounds.Height,GADAdSizeCons.Banner.size.Height);
		//composite.
		//CGAffineTransform ta = CGAffineTransformTranslate(
		//Console.WriteLine("Requesting Ad");
		ad.LoadRequest(new GADRequest());
		
		
		 game = new Launcher(this);
      PlayN.run(game); //should not compile because it needs LauncherInterface  : IOSApplicationDelegate,LauncherInterface
      
      
      ///////////////////////////
		//INAPP////////////////////

		if (!isUnlocked()) {
				Thread longRunningProc = new Thread (delegate() {
			products = new List<string>() {productId};
			iap = new InAppPurchaseManager();
		
		// setup the observer to wait for prices to come back from StoreKit <- AppStore
			priceObserver = NSNotificationCenter.DefaultCenter.AddObserver (InAppPurchaseManager.InAppPurchaseManagerProductsFetchedNotification, 
			(notification) => {
				var info = notification.UserInfo;
				var NSProductId = new NSString(productId);
				
				// we only update the button with a price if the user hasn't already purchased it
				if (info!= null && info.ContainsKey(NSProductId)) {
					pricesLoaded = true;

					var product = (SKProduct) info.ObjectForKey(NSProductId);
					
					Console.WriteLine("Product id: " + product.ProductIdentifier);
					Console.WriteLine("Product title: " + product.LocalizedTitle);
					Console.WriteLine("Product description: " + product.LocalizedDescription);
					Console.WriteLine("Product price: " + product.Price);
					Console.WriteLine("Product l10n price: " + product.LocalizedPrice());	

					/*
					greyscaleButton.Enabled = true;
					greyscaleTitle.Text = product.LocalizedTitle;
					greyscaleDescription.Text = product.LocalizedDescription;
					greyscaleButton.SetTitle("Buy " + product.LocalizedPrice(), UIControlState.Normal);
					*/
				}
				
				
			});
			
			// only if we can make payments, request the prices
			if (iap.CanMakePayments()) {
				// now go get prices, if we don't have them already
				if (!pricesLoaded)
					iap.RequestProductData(products); // async request via StoreKit -> App Store
			} else {
				Console.WriteLine("AppStore disabled, purchases turned off in Settings?");	
				// can't make payments (purchases turned off in Settings?)
				//greyscaleButton.SetTitle ("AppStore disabled", UIControlState.Disabled);
			}
			// update the buttons before displaying, to reflect past purchases
			UpdateButtons ();

			priceObserver = NSNotificationCenter.DefaultCenter.AddObserver (InAppPurchaseManager.InAppPurchaseManagerTransactionSucceededNotification, 
			(notification) => {
				// update the buttons after a successful purchase
				UpdateButtons ();
			});

			requestObserver = NSNotificationCenter.DefaultCenter.AddObserver (InAppPurchaseManager.InAppPurchaseManagerRequestFailedNotification, 
			                                                                 (notification) => {
				// TODO: 
				Console.WriteLine ("Request Failed, Network down?");
				//greyscaleButton.SetTitle ("Network down?", UIControlState.Disabled);
				
			});

		} );
		longRunningProc.Start ();
		}
      
      return true;
    }
    
    void UpdateButtons () {
			// set whether the user already has purchased these products
			if (isUnlocked()) {
				//greyscaleButton.Enabled = true;
				//greyscaleButton.SetTitle("Use Greyscale Filter", UIControlState.Normal);
				//greyscalePurchased = true;
				
				Launcher.unlocked = true;
        		hideAds();
        	
				if (!Launcher.gameStarted) {
					Launcher.showInitMenu = true;
	    		}
				
			}
	}
    



		#region LauncherInterface implementation

		public float getScreenDensity ()
		{
			//should be OK, because IOS use points coordinate
			return 1;
		}

		public void showAds ()
		{
			//throw new NotImplementedException ();
			Console.WriteLine("showAds");
			if (ad!=null) ad.Hidden = false;
			
		}
		
		private static string messageEN = "Unlock all puzzles and remove the Ads";
		private static string messageENSave = "You can store only three drawings in the free version. " +
		 		"If your kid likes this game, you can buy the full version in the market with more puzzles and without Advertising." +
		 		" Thanks for your support!";
		private static string buyEN = "Unlock";
		private static string noThanksEN = "No, thanks!";
		private static string restoreEN = "Restore purchase";
		private static string saveImageEN = "Save image to Photos?";
		
		private static string messageES = "Desbloquear todos los rompecabezas y quitar la publicidad";
		private static string messageESSave = "Puede almacenar sólo tres dibujos en la versión gratuita. " +
				"Si su hijo le gusta este juego, usted puede comprar la versión completa en el market con más rompecabezas y sin publicidad." +
				" Gracias por tu apoyo!";
		private static string buyES = "Desbloquear";
		private static string noThanksES = "No, gracias!";
		private static string restoreES = "Restaurar compra";
		private static string saveImageES = "Guardar imagen en el álbum de fotos?";
		
		private static string messageIT = "Sblocca tutti i puzzle e rimuovere la pubblicità";
		private static string messageITSave = "È possibile memorizzare solo tre disegni nella versione gratuita. " +
				"Se al vostro bambino piace questo gioco, potete acquistare la versione completa sul market con più puzzle e senza pubblicità." +
				" Grazie per il vostro supporto!";
		private static string buyIT = "Sblocca";
		private static string noThanksIT = "No, grazie!";
		private static string restoreIT = "Ripristino acquisto";
		private static string saveImageIT = "Salva immagine nell'album foto?";
				
		private static string messageFR = "Débloquer tous les puzzle et supprimer la publicité";
		private static string messageFRSave = "Vous pouvez mémoriser que trois dessins dans la version gratuite. " +
				"Si votre enfant aime ce jeu, vous pouvez acheter la version complète sur le market avec plus de puzzle et sans publicité." +
				" Merci pour votre soutien!";
		private static string buyFR = "Débloquer";
		private static string noThanksFR = "Non, merci!";
		private static string restoreFR = "Rétablir l'achat";
		private static string saveImageFR = "Sauver l'image dans l'album photo?";
		
		private static string messageDE = "Freischalten alle Puzzles und entfernen Sie die Werbung";
		private static string messageDESave = "Sie können nur drei Zeichnungen in der kostenlosen Version zu speichern. " +
				"Ist Ihr Kind mag dieses Spiel, können Sie die Vollversion auf dem market mit mehr Puzzles und ohne Werbung kaufen." +
				" Vielen Dank für Ihre Unterstützung!";
		private static string buyDE = "Freischalten";
		private static string noThanksDE = "Nein, danke!";
		private static string restoreDE = "Wiederherstellen";
		private static string saveImageDE = "Bild speichern im Fotoalbum?";
		
		
		
		public static string productId = "co.romesoft.toddlers.puzzle.toys.unlocker";   
		InAppPurchaseManager iap;
		List<string> products;
		NSObject priceObserver, requestObserver;
		bool pricesLoaded = false;
		
		
		
		

		public void showLitePopup (bool b)
		{
			//TODO start inapp manager
			/*
			
			public override void ViewWillDisappear (bool animated)
			{
				// remove the observer when the view isn't visible
				NSNotificationCenter.DefaultCenter.RemoveObserver (priceObserver);
				NSNotificationCenter.DefaultCenter.RemoveObserver (requestObserver);
	
				base.ViewWillDisappear (animated);
			}
  	        */
  	        //b==true   max images saved message
			String message = (b==true) ? messageENSave : messageEN;
			String buy = buyEN;
			String noThanks = noThanksEN;
			String restore = restoreEN;
			
			String lang = NSLocale.PreferredLanguages[0];
			if (lang!=null && lang.ToLower().StartsWith("es")) {
				message = (b==true) ? messageESSave : messageES;
				buy = buyES;
				noThanks = noThanksES;
				restore = restoreES;
			} else if (lang!=null && lang.ToLower().StartsWith("it")) {
				message = (b==true) ? messageITSave : messageIT;
				buy = buyIT;
				noThanks = noThanksIT;
				restore = restoreIT;
			} else if (lang!=null && lang.ToLower().StartsWith("fr")) {
				message = (b==true) ? messageFRSave : messageFR;
				buy = buyFR;
				noThanks = noThanksFR;
				restore = restoreFR;
			} else if (lang!=null && lang.ToLower().StartsWith("de")) {
				message = (b==true) ? messageDESave : messageDE;
				buy = buyDE;
				noThanks = noThanksDE;
				restore = restoreDE;
			}
  	        
			var x = new UIAlertView (message, "",  null, noThanks, buy, restore);
  	        /*
  	        x.Title = "";
			x.AddButton("Ok");
			x.AddButton("No Thanks");
			x.AddButton("Restore");
			x.Message = "Unlock all drawings!";
			*/
    		x.Show();
    		x.Clicked += (sender, buttonArgs) => {
		        Console.WriteLine ("User clicked on {0}", buttonArgs.ButtonIndex);
		    	int clicked = buttonArgs.ButtonIndex;
		    	if (clicked == 1) {
		    		 // initiate payment
					iap.PurchaseProduct (productId);
		    	} else if (clicked == 2) {
		    		 // Restore payment
					iap.Restore();
		    	} 
		    	
		    	
		    }; 
  	        
  	        
  	       
			/*
			//in pause
			game.paused = true;
			
			//show internationalized dialog redirect to full version or initiatePurchaseRequest inapp
			
			String message = messageEN;
			String buy = buyEN;
			String noThanks = noThanksEN;
			
			String lang = NSLocale.PreferredLanguages[0];
			if (lang!=null && lang.ToLower().StartsWith("es")) {
				message = messageES;
				buy = buyES;
				noThanks = noThanksES;
			} else if (lang!=null && lang.ToLower().StartsWith("it")) {
				message = messageIT;
				buy = buyIT;
				noThanks = noThanksIT;
			} else if (lang!=null && lang.ToLower().StartsWith("fr")) {
				message = messageFR;
				buy = buyFR;
				noThanks = noThanksFR;
			} else if (lang!=null && lang.ToLower().StartsWith("de")) {
				message = messageDE;
				buy = buyDE;
				noThanks = noThanksDE;
			}
			
			
		    var x = new UIAlertView ("", message,  null, buy, noThanks);
    		x.Show();
    		x.Clicked += (sender, buttonArgs) => {
		        Console.WriteLine ("User clicked on {0}", buttonArgs.ButtonIndex);
		    	int clicked = buttonArgs.ButtonIndex;
		    	if (clicked == 0) {
		    		try {
		    		  pf.openURL(InfoPageLayer.FULL_URL_1);
		    		} catch (Exception ex) {
					  pf.openURL(InfoPageLayer.FULL_URL_2);
					}
		    	}
		    	
		    	game.paused = false;
		    };   
			*/
			
		}

		public void closeLauncher ()
		{
			//throw new NotImplementedException ();
			//nothing to do ?
		}

		public void hideAds ()
		{
			//throw new NotImplementedException ();
			Console.WriteLine("hideAds");
			if (ad!=null) ad.Hidden = true;
		}
		
		public static string UNLOCKED = "puzzleToysUnlocked";
    
		public static string unlockVal = "puzzleToysyeaahh2";
		
		public bool isUnlocked() {
			var item = pf.storage().getItem(UNLOCKED);
			if (item!=null && item.Equals(unlockVal)) {
				Console.WriteLine("isUnlocked");
				return true;
			} else {
				Console.WriteLine("NOT isUnlocked");
				return false;
			}
		}

		#endregion
  }

  public class Application {
    static void Main (string[] args) {
      UIApplication.Main (args, null, "AppDelegate");
    }
  }
}
