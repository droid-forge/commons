package promise.commons;

class AndroidPromiseInstanceProvider implements InstanceProvider<AndroidPromise> {

    private static AndroidPromiseInstanceProvider instance;

    private final ApplicationInstanceProvider applicationInstanceProvider;

    private int numOfThreads = 1;

    private boolean enableDebug = false;


    private AndroidPromiseInstanceProvider(ApplicationInstanceProvider applicationInstanceProvider, boolean enableDebug) {
        this.applicationInstanceProvider = applicationInstanceProvider;
        this.enableDebug = enableDebug;
    }

    private AndroidPromiseInstanceProvider(ApplicationInstanceProvider applicationInstanceProvider, int numOfThreads, boolean enableDebug) {
        this.applicationInstanceProvider = applicationInstanceProvider;
        this.numOfThreads = numOfThreads;
        this.enableDebug = enableDebug;
    }

    static AndroidPromiseInstanceProvider create(ApplicationInstanceProvider applicationInstanceProvider, boolean enableDebug) {
        if (instance == null) instance = new AndroidPromiseInstanceProvider(applicationInstanceProvider, enableDebug);
        return instance;
    }

    static AndroidPromiseInstanceProvider create(ApplicationInstanceProvider applicationInstanceProvider, int numOfThreads, boolean enableDebug) {
        if (instance == null)
            instance = new AndroidPromiseInstanceProvider(applicationInstanceProvider, numOfThreads, enableDebug);
        return instance;
    }

    static AndroidPromiseInstanceProvider instance() throws IllegalAccessException {
        if (instance == null) throw new IllegalAccessException("Initialize promise first");
        return instance;
    }

    @Override
    public AndroidPromise get() {
        AndroidPromise instance = new AndroidPromise(applicationInstanceProvider.get());
        instance.enableDebug = enableDebug;
        if (numOfThreads > 1) return instance.threads(numOfThreads);
        return instance;
    }
}
