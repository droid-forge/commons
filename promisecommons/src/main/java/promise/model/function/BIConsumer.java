package promise.model.function;

/**
 * Created on 7/23/18 by yoctopus.
 */
public interface BIConsumer<T, U> {
    void accept(T t, U u);
}
