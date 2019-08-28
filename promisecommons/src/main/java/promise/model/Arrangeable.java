package promise.model;

/**
 * Created on 7/17/18 by yoctopus.
 */
public class Arrangeable<Value, Key> {
    private Value value;
    private Key key;

    public Value value() {
        return value;
    }

    public Arrangeable<Value, Key> value(Value value) {
        this.value = value;
        return this;
    }

    public Key key() {
        return key;
    }

    public Arrangeable<Value, Key> key(Key key) {
        this.key = key;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arrangeable<?, ?> that = (Arrangeable<?, ?>) o;
        return value.equals(that.value) &&
            key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode() + value.hashCode();
    }
}
