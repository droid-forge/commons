package promise.commons;

import android.app.Application;

class ApplicationProvider implements InstanceProvider<Application> {

  private static ApplicationProvider instance;

  private final Application applicationInstanceProvider;

  private ApplicationProvider(Application applicationInstanceProvider) {
    this.applicationInstanceProvider = applicationInstanceProvider;
  }

  @Override
  public Application get() {
    return applicationInstanceProvider;
  }

  static ApplicationProvider create(Application applicationInstanceProvider) {
    if (instance == null) instance = new ApplicationProvider(applicationInstanceProvider);
    return instance;
  }

  static Application instance() {
    if (instance == null) throw new RuntimeException("Provider was not created");
    return instance.get();
  }

}
