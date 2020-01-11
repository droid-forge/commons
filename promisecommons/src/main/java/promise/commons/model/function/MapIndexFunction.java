package promise.commons.model.function;

public interface MapIndexFunction<E, T> {
  E from(int index, T t);
}
