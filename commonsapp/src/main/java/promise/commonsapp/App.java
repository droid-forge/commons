package promise.commonsapp;

import android.app.Application;

import promise.commons.AndroidPromise;

public class App extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    AndroidPromise.init(this, 10, BuildConfig.DEBUG);
  }

  @Override
  public void onTerminate() {
    super.onTerminate();
    AndroidPromise.instance().terminate();
  }
}
