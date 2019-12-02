package promise.commons.model

/**
 * used to identify an instance in a collection
 *
 * @param T type of identifying id
 */
interface Identifiable<T> {
    /**
     * set the id to the instance
     *
     * @param t id
     */
    fun setId(t: T)

    /**
     * get the id from the instance
     *
     * @return
     */
    fun getId(): T
}