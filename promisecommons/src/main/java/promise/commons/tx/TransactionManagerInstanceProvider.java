package promise.commons.tx;

import promise.commons.InstanceProvider;

class TransactionManagerInstanceProvider implements InstanceProvider<TransactionManager> {

    private static TransactionManagerInstanceProvider instance;

    static void create() throws IllegalStateException {
        if (instance == null) instance = new TransactionManagerInstanceProvider();
        else throw new IllegalStateException("manager already initialized");
    }

    static TransactionManagerInstanceProvider instance() throws IllegalAccessException {
        if (instance == null) throw new IllegalAccessException("Initialize manager first");
        return instance;
    }

    @Override
    public TransactionManager get() {
        return new TransactionManager();
    }
}
