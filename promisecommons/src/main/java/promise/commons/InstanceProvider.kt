package promise.commons

/**
 *
 *
 * @param T
 */
interface InstanceProvider<out T> {

  /**
   * Provides a fully-constructed and injected instance of `T`.
   */
  fun get(): T
}
