package promise.commons

/**
 * provides a singleton instance of a variable through the life of
 * of its provider
 *
 * @param T type to provide
 * @constructor
 *
 *
 * @param provider
 */
class SingletonInstanceProvider<out T> private constructor(provider: InstanceProvider<T>) : InstanceProvider<T> {
  /**
   *
   */
  @Volatile
  private var provider: InstanceProvider<T>? = null
  /**
   *
   */
  @Volatile
  private var instance = NAKED

  /**
   *
   *
   * @return
   */
  override
  fun get(): T {
    var result = instance
    if (result === NAKED) {
      synchronized(this) {
        result = instance
        if (result === NAKED) {
          result = provider!!.get() as Any
          instance = result
          /* Null out the reference to the provider and make it eligible for GC. */
          provider = null
        }
      }
    }
    return result as T
  }

  companion object {
    /**
     *
     */
    private val NAKED = Any()

    /**
     *
     *
     * @param P
     * @param T
     * @param provider
     * @return
     */
    @JvmStatic
    fun <T, P : InstanceProvider<T>> provider(provider: P): InstanceProvider<T> =
        provider as? SingletonInstanceProvider<T>
            ?: SingletonInstanceProvider(provider)
  }

  /**
   * initialize the provider
   */
  init {
    this.provider = provider
  }
}
