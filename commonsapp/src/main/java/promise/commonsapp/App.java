package promise.commonsapp;

import android.app.Application;

import promise.commons.Promise;

public class App extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    Promise.init(this, 5);
  }

  @Override
  public void onTerminate() {
    super.onTerminate();
    Promise.instance().terminate();
  }
}
