using System;
using System.Drawing;

using MonoTouch.Foundation;
using MonoTouch.UIKit;
//using GoogleAdMobAds;
using AlexTouch.GoogleAdMobAds;

namespace testAdsInapp
{
	public partial class testAdsInappViewController : UIViewController
	{
		//GADBannerView bannerView;

		public testAdsInappViewController () : base ("testAdsInappViewController", null)
		{
		}
		
		public override void DidReceiveMemoryWarning ()
		{
			// Releases the view if it doesn't have a superview.
			base.DidReceiveMemoryWarning ();
			
			// Release any cached data, images, etc that aren't in use.
		}
		
		public override void ViewDidLoad ()
		{
			base.ViewDidLoad ();
			
			// Perform any additional setup after loading the view, typically from a nib.
			/*
			var pointF = new PointF (0, this.View.Frame.Height - GADBannerView.GAD_SIZE_320x50.Height);
			bannerView = new GADBannerView(new RectangleF(pointF, GADBannerView.GAD_SIZE_320x50));
			bannerView.AdUnitID = "a1505192d2de474";
			bannerView.RootViewController = this;

			//bannerView.Delegate = new MyGADBannerViewDelegate();
			
			this.View.AddSubview(bannerView);
			var gADRequest = new GADRequest ();
			gADRequest.Testing = true;
			bannerView.LoadRequest(gADRequest);
			*/
			var ad = new GADBannerView(GADAdSizeCons.Banner,new PointF(0,0))
			{
				AdUnitID = "a1505192d2de474",
				RootViewController = this //or your RootViewController  
			};

			/*
			ad.DidReceiveAd += delegate 
			{
				this.View.AddSubview(ad);
				Console.WriteLine("AD Received");
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
			*/
			ad.AutoresizingMask=UIViewAutoresizing.FlexibleLeftMargin|UIViewAutoresizing.FlexibleRightMargin;
			this.View.AddSubview(ad);
			Console.Write("Requesting Ad");
			ad.LoadRequest(new GADRequest());


		}
		
		public override void ViewDidUnload ()
		{
			base.ViewDidUnload ();
			
			// Clear any references to subviews of the main view in order to
			// allow the Garbage Collector to collect them sooner.
			//
			// e.g. myOutlet.Dispose (); myOutlet = null;
			
			ReleaseDesignerOutlets ();
		}
		
		public override bool ShouldAutorotateToInterfaceOrientation (UIInterfaceOrientation toInterfaceOrientation)
		{
			// Return true for supported orientations
			return (toInterfaceOrientation == UIInterfaceOrientation.Portrait);
		}
	}

//    class MyGADBannerViewDelegate : GADBannerViewDelegate {
//		public override advire 
//	}
}

