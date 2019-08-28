package promise.model.function;

import promise.model.List;

/**
 * Created on 7/5/18 by yoctopus.
 */
public interface ReduceFunction<K, T> {
    K reduce(List<T> list);
}
