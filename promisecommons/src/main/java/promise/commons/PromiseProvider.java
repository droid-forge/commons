package promise.commons;

class PromiseProvider implements InstanceProvider<Promise> {

  private static PromiseProvider instance;

  private final ApplicationProvider applicationInstanceProvider;

  private int numOfThreads = 1;

  private PromiseProvider(ApplicationProvider applicationInstanceProvider) {
    this.applicationInstanceProvider = applicationInstanceProvider;
  }

  private PromiseProvider(ApplicationProvider applicationInstanceProvider, int numOfThreads) {
    this.applicationInstanceProvider = applicationInstanceProvider;
    this.numOfThreads = numOfThreads;
  }

  @Override
  public Promise get() {
    Promise instance = new Promise(applicationInstanceProvider.get());
    if (numOfThreads > 1) return instance.threads(numOfThreads);
    return instance;
  }

  static PromiseProvider create(ApplicationProvider applicationInstanceProvider) {
    if (instance == null) instance = new PromiseProvider(applicationInstanceProvider);
    return instance;
  }

  static PromiseProvider create(ApplicationProvider applicationInstanceProvider, int numOfThreads) {
    if (instance == null) instance = new PromiseProvider(applicationInstanceProvider, numOfThreads);
    return instance;
  }

  static PromiseProvider instance() throws IllegalAccessException {
    if (instance == null) throw new IllegalAccessException("Initialize promise first");
    return instance;
  }
}
