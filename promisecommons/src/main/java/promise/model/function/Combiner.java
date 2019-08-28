package promise.model.function;

/**
 * Created on 7/5/18 by yoctopus.
 */
public interface Combiner<K, T> {
    T join(T t, K k);
}
