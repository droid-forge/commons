package promise.commons;

import android.app.Application;

class ApplicationInstanceProvider implements InstanceProvider<Application> {

    private static ApplicationInstanceProvider instance;

    private final Application applicationInstanceProvider;

    private ApplicationInstanceProvider(Application applicationInstanceProvider) {
        this.applicationInstanceProvider = applicationInstanceProvider;
    }

    static ApplicationInstanceProvider create(Application applicationInstanceProvider) {
        if (instance == null) instance = new ApplicationInstanceProvider(applicationInstanceProvider);
        return instance;
    }

    static Application instance() {
        if (instance == null) throw new RuntimeException("Provider was not created");
        return instance.get();
    }

    @Override
    public Application get() {
        return applicationInstanceProvider;
    }

}
