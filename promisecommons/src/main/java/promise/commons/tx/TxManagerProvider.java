package promise.commons.tx;

import promise.commons.InstanceProvider;

class TxManagerProvider implements InstanceProvider<TxManager> {

    private static TxManagerProvider instance;

    static void create() throws IllegalStateException {
        if (instance == null) instance = new TxManagerProvider();
        else throw new IllegalStateException("manager already initialized");
    }

    static TxManagerProvider instance() throws IllegalAccessException {
        if (instance == null) throw new IllegalAccessException("Initialize manager first");
        return instance;
    }

    @Override
    public TxManager get() {
        return new TxManager();
    }
}
