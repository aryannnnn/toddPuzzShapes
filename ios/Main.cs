using System;
using MonoTouch.Foundation;
using MonoTouch.UIKit;

using playn.ios;
using playn.core;
using co.romesoft.core;
using AlexTouch.GoogleAdMobAds;
using System.Drawing;
using MonoTouch.CoreGraphics;

namespace co.romesoft
{
  [Register ("AppDelegate")]
  public partial class AppDelegate : IOSApplicationDelegate,LauncherInterface {  //or UIApplicationDelegate ?
		UIWindow w;
		UIView uiov;
		IOSPlatform pf;
		Launcher game;
		GADBannerView ad = null;

    public override bool FinishedLaunching (UIApplication app, NSDictionary options) {
      app.SetStatusBarHidden(true, true);
      pf = IOSPlatform.register(app, IOSPlatform.SupportedOrients.LANDSCAPES); //iPadLikePhone = false come default
      pf.assets().setPathPrefix("assets");
		w=	pf.window();
		uiov = pf.uiOverlay();
		//pf.graphics().screenWidth()   dovrebbe contenere il width giusto

	  game = new Launcher(this);
      PlayN.run(game); //should not compile because it needs LauncherInterface  : IOSApplicationDelegate,LauncherInterface
      
      //init admob
        /*
		//http://googleadsdeveloper.blogspot.it/2012/06/keeping-smart-banner-docked-to-bottom.html
		//http://www.monkeycoder.co.nz/Community/posts.php?topic=1246
		//http://pocketworx.com/?p=107
		bannerView_ = [[GADBannerView alloc]
	               initWithFrame:CGRectMake(0.0,
	                                        100.0 -
	                                        GAD_SIZE_320x50.height,
	                                        GAD_SIZE_320x50.width,
	                                        GAD_SIZE_320x50.height)];
	                                        
	    */
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
      
      return true;
    }

	//public override close 

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
		
		private static string messageEN = "This is the FREE version of the app. " +
		 		"If your kid likes this game, you can buy the full version in the market with more puzzles and without Advertising." +
		 		" Thanks for your support!";
		private static string messageENSave = "You can store only three drawings in the free version. " +
		 		"If your kid likes this game, you can buy the full version in the market with more puzzles and without Advertising." +
		 		" Thanks for your support!";
		private static string buyEN = "Buy";
		private static string noThanksEN = "No, thanks!";
		
		private static string messageES = "Esta es la versión gratuita de la aplicación. " +
				"Si su hijo le gusta este juego, usted puede comprar la versión completa en el market con más rompecabezas y sin publicidad." +
				" Gracias por tu apoyo!";
		private static string messageESSave = "Puede almacenar sólo tres dibujos en la versión gratuita. " +
				"Si su hijo le gusta este juego, usted puede comprar la versión completa en el market con más rompecabezas y sin publicidad." +
				" Gracias por tu apoyo!";
		private static string buyES = "Comprar";
		private static string noThanksES = "No, gracias!";
		
		private static string messageIT = "Questa è la versione gratuita dell'applicazione. " +
				"Se al vostro bambino piace questo gioco, potete acquistare la versione completa sul market con più puzzle e senza pubblicità." +
				" Grazie per il vostro supporto!";
		private static string messageITSave = "È possibile memorizzare solo tre disegni nella versione gratuita. " +
				"Se al vostro bambino piace questo gioco, potete acquistare la versione completa sul market con più puzzle e senza pubblicità." +
				" Grazie per il vostro supporto!";
		private static string buyIT = "Acquista";
		private static string noThanksIT = "No, grazie!";
				
		private static string messageFR = "Ceci est la version gratuite de l'application. " +
				"Si votre enfant aime ce jeu, vous pouvez acheter la version complète sur le market avec plus de puzzle et sans publicité." +
				" Merci pour votre soutien!";
		private static string messageFRSave = "Vous pouvez mémoriser que trois dessins dans la version gratuite. " +
				"Si votre enfant aime ce jeu, vous pouvez acheter la version complète sur le market avec plus de puzzle et sans publicité." +
				" Merci pour votre soutien!";
		private static string buyFR = "Achetez-le";
		private static string noThanksFR = "Non, merci!";
		
		private static string messageDE = "Dies ist die kostenlose Version der App. " +
				"Ist Ihr Kind mag dieses Spiel, können Sie die Vollversion auf dem market mit mehr Puzzles und ohne Werbung kaufen." +
				" Vielen Dank für Ihre Unterstützung!";
		private static string messageDESave = "Sie können nur drei Zeichnungen in der kostenlosen Version zu speichern. " +
				"Ist Ihr Kind mag dieses Spiel, können Sie die Vollversion auf dem market mit mehr Puzzles und ohne Werbung kaufen." +
				" Vielen Dank für Ihre Unterstützung!";
		private static string buyDE = "Kaufen";
		private static string noThanksDE = "Nein, danke!";

		public void showLitePopup (bool b)
		{
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
			
			/*
			
			UIAlertView al = new UIAlertView () {
					Title = "Title!"
				  , Message = "Message"
				} ;
			al.AddButton("OK");
			al.Show ();
			
			*/
			
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

		#endregion
  }

  public class Application {
    static void Main (string[] args) {
      UIApplication.Main (args, null, "AppDelegate");
    }
  }
}
