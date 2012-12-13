using System;
using MonoTouch.Foundation;
using MonoTouch.UIKit;

using playn.ios;
using playn.core;
using co.romesoft.core;

namespace co.romesoft
{
  [Register ("AppDelegate")]
  public partial class AppDelegate : IOSApplicationDelegate {  //or UIApplicationDelegate ?
    public override bool FinishedLaunching (UIApplication app, NSDictionary options) {
      app.SetStatusBarHidden(true, true);
      var pf = IOSPlatform.register(app, IOSPlatform.SupportedOrients.LANDSCAPES);
      pf.assets().setPathPrefix("assets");
      PlayN.run(new Launcher()); //should not compile because it needs LauncherInterface  : IOSApplicationDelegate,LauncherInterface
      return true;
    }
  }

  public class Application {
    static void Main (string[] args) {
      UIApplication.Main (args, null, "AppDelegate");
    }
  }
}
