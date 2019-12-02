package promise.commons.model.function;

public interface FilterFunction<T> {
    boolean select(T t);
}
