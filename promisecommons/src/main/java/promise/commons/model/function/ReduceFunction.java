package promise.commons.model.function;

import promise.commons.model.List;

/**
 * Created on 7/5/18 by yoctopus.
 */
public interface ReduceFunction<K, T> {
    K reduce(List<T> list);
}
