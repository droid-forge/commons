/*
 * Copyright 2017, Peter Vincent
 * Licensed under the Apache License, Version 2.0, Android Promise.
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package promise.commons.tx

import promise.commons.AndroidPromise
import promise.commons.data.log.LogUtil

/**
 *
 */
interface Either<T : Any, E : Throwable> {
  /**
   *
   */
  fun fold(
      /**
       *
       */
      res: (t: T) -> Unit,
      /**
       *
       */
      err: (e: E) -> Unit)

  @Throws(Throwable::class)
  fun foldSync(): Pair<T?, E?>

  fun foldOnUI(
      /**
       *
       */
      res: (t: T) -> Unit,
      /**
       *
       */
      err: (e: E) -> Unit)

  fun fold(): PromiseCallback<T>

  fun foldOnUI(): PromiseCallback<T>

  /**
   *
   */
  fun fold(
      /**
       *
       */
      promiseResult: PromiseResult<T, E>)

  fun foldOnUI(
      /**
       *
       */
      promiseResult: PromiseResult<T, E>)

  /**
   *
   */
  fun foldToPromise(): Promise<T?>

  fun foldToPromiseOnUI(): Promise<T?>
}

/**
 *
 */
sealed class SyncEither<T : Any, E : Throwable>(
    /**
     *
     */
    val t: T?,
    /**
     *
     */
    val e: E?) : Either<T, E> {
  /**
   *
   */
  override fun fold(
      /**
       *
       */
      res: (t: T) -> Unit,
      /**
       *
       */
      err: (e: E) -> Unit) {
    if (t != null) try {
      res(t)
    } catch (e: Throwable) {
      LogUtil.e(TAG, e)
    } else if (e != null) try {
      err(e)
    } catch (e: Throwable) {
      LogUtil.e(TAG, e)
    }
  }

  @Throws(Exception::class)
  override fun foldSync(): Pair<T?, E?> = when {
    t != null -> Pair(t, null)
    e != null -> Pair(null, e)
    else -> throw Exception("no data to unfold")
  }

  /**
   *
   */
  override fun foldOnUI(
      /**
       *
       */
      res: (t: T) -> Unit,
      /**
       *
       */
      err: (e: E) -> Unit) {
    if (t != null) try {
      promise.executeOnUi {
        res(t)
      }
    } catch (e: Throwable) {
      LogUtil.e(TAG, e)
    } else if (e != null) try {
      promise.executeOnUi {
        err(e)
      }
    } catch (e: Throwable) {
      LogUtil.e(TAG, e)
    }
  }

  /**
   *
   */
  override fun fold(): PromiseCallback<T> = PromiseCallback { resolve, reject ->
    if (t != null) try {
      resolve(t)
    } catch (e: Throwable) {
      LogUtil.e(TAG, e)
    } else if (e != null) try {
      reject(e)
    } catch (e: Throwable) {
      LogUtil.e(TAG, e)
    }
  }

  /**
   *
   */
  override fun foldOnUI(): PromiseCallback<T> = PromiseCallback { resolve, reject ->
    if (t != null) try {
      promise.executeOnUi {
        resolve(t)
      }
    } catch (e: Throwable) {
      LogUtil.e(TAG, e)
    } else if (e != null) try {
      promise.executeOnUi {
        reject(e)
      }
    } catch (e: Throwable) {
      LogUtil.e(TAG, e)
    }
  }

  /**
   *
   */
  override fun fold(
      /**
       *
       */
      promiseResult: PromiseResult<T, E>) {
    if (t != null) try {
      promiseResult.response(t)
    } catch (e: Throwable) {
      LogUtil.e(TAG, e)
    } else if (e != null) try {
      promiseResult.error(e)
    } catch (e: Throwable) {
      LogUtil.e(TAG, e)
    }
  }

  override fun foldOnUI(promiseResult: PromiseResult<T, E>) {
    if (t != null) try {
      promise.executeOnUi {
        promiseResult.response(t)
      }
    } catch (e: Throwable) {
      LogUtil.e(TAG, e)
    } else if (e != null) try {
      promise.executeOnUi {
        promiseResult.error(e)
      }
    } catch (e: Throwable) {
      LogUtil.e(TAG, e)
    }
  }

  /**
   *
   */
  override fun foldToPromise(): Promise<T?> = Promise(object : CallbackWithResolver<Any, T?> {
    override fun call(arg: Any, resolver: Resolver<T?>) {
      if (t != null) try {
        resolver.resolve(t, null)
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      } else if (e != null) try {
        resolver.resolve(null, e)
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      }
    }
  })

  override fun foldToPromiseOnUI(): Promise<T?> = Promise(object : CallbackWithResolver<Any, T?> {
    override fun call(arg: Any, resolver: Resolver<T?>) {
      if (t != null) try {
        promise.executeOnUi {
          resolver.resolve(t, null)
        }
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      } else if (e != null) try {
        promise.executeOnUi {
          resolver.resolve(null, e)
        }
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      }
    }
  })

  companion object {
    val TAG: String = LogUtil.makeTag(SyncEither::class.java)
    val promise: AndroidPromise = AndroidPromise.instance()
  }
}

/**
 *
 */
class Right<T : Any, E : Throwable>(
    /**
     *
     */
    t: T) : SyncEither<T, E>(
    /**
     *
     */
    t,
    /**
     *
     */
    null)

/**
 *
 */
class Left<E : Throwable, T : Any>(
    /**
     *
     */
    e: E) : SyncEither<T, E>(
    /**
     *
     */
    null,
    /**
     *
     */
    e)
/**
 *
 */
typealias AsyncEitherFunc<T> = (resolve: (T?) -> Unit) -> Unit

/**
 *
 */
sealed class AsyncEither<T : Any, E : Throwable>(
    /**
     *
     */
    val t: AsyncEitherFunc<T>?,
    /**
     *
     */
    val e: AsyncEitherFunc<E>?) : Either<T, E> {


  /**
   *
   */
  override fun fold(
      /**
       *
       */
      res: (t: T) -> Unit,
      /**
       *
       */
      err: (e: E) -> Unit) = promise.execute {
    if (t != null) try {
      t.invoke { result ->
        if (result != null) try {
          res(result)
        } catch (e: Throwable) {
          err(e as E)
        }
      }
    } catch (e: Throwable) {
      LogUtil.e(TAG, e)
    } else if (e != null) try {
      e.invoke { error ->
        if (error != null) try {
          err(error)
        } catch (e: Throwable) {
          LogUtil.e(TAG, e)
        }
      }
    } catch (e: Throwable) {
      LogUtil.e(TAG, e)
    }
  }

  /**
   *
   */
  override fun foldOnUI(
      /**
       *
       */
      res: (t: T) -> Unit,
      /**
       *
       */
      err: (e: E) -> Unit) = promise.execute {
    if (t != null) try {
      t.invoke { result ->
        if (result != null) try {
          promise.executeOnUi {
            res(result)
          }
        } catch (e: Throwable) {
          err(e as E)
        }
      }
    } catch (e: Throwable) {
      LogUtil.e(TAG, e)
    } else if (e != null) try {
      e.invoke { error ->
        if (error != null) try {
          promise.executeOnUi {
            err(error)
          }
        } catch (e: Throwable) {
          LogUtil.e(TAG, e)
        }
      }
    } catch (e: Throwable) {
      LogUtil.e(TAG, e)
    }
  }

  @Throws(Exception::class)
  override fun foldSync(): Pair<T?, E?> {
    throw Exception("Not implemented in async either")
  }

  /**
   *
   */
  override fun fold(): PromiseCallback<T> = PromiseCallback { resolve, reject ->
    promise.execute {
      if (t != null) try {
        t.invoke { result ->
          if (result != null) try {
            resolve(result)
          } catch (e: Throwable) {
            LogUtil.e(TAG, e)
          }
        }
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      } else if (e != null) try {
        e.invoke { error ->
          if (error != null) try {
            reject(error)
          } catch (e: Throwable) {
            LogUtil.e(TAG, e)
          }
        }
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      }
    }
  }

  /**
   *
   */
  override fun foldOnUI(): PromiseCallback<T> = PromiseCallback { resolve, reject ->
    promise.execute {
      if (t != null) try {
        t.invoke { result ->
          if (result != null) try {
            promise.execute {
              resolve(result)
            }
          } catch (e: Throwable) {
            LogUtil.e(TAG, e)
          }
        }
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      } else if (e != null) try {
        e.invoke { error ->
          if (error != null) try {
            promise.executeOnUi {
              reject(error)
            }
          } catch (e: Throwable) {
            LogUtil.e(TAG, e)
          }
        }
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      }
    }
  }

  /**
   *
   */
  override fun fold(
      /**
       *
       */
      promiseResult: PromiseResult<T, E>) {
    promise.execute {
      if (t != null) try {
        t.invoke { result ->
          if (result != null) try {
            promiseResult.response(result)
          } catch (e: Throwable) {
            LogUtil.e(TAG, e)
          }
        }
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      } else if (e != null) try {
        e.invoke { error ->
          if (error != null) try {
            promiseResult.error(error)
          } catch (e: Throwable) {
            LogUtil.e(TAG, e)
          }
        }
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      }
    }
  }

  override fun foldOnUI(promiseResult: PromiseResult<T, E>) {
    promise.execute {
      if (t != null) try {
        t.invoke { result ->
          if (result != null) try {
            promise.executeOnUi {
              promiseResult.response(result)
            }
          } catch (e: Throwable) {
            LogUtil.e(TAG, e)
          }
        }
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      } else if (e != null) try {
        e.invoke { error ->
          if (error != null) try {
            promise.executeOnUi {
              promiseResult.error(error)
            }
          } catch (e: Throwable) {
            LogUtil.e(TAG, e)
          }
        }
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      }
    }
  }

  /**
   *
   */
  override fun foldToPromise(): Promise<T?> = Promise(object : CallbackWithResolver<Any, T?> {
    override fun call(arg: Any, resolver: Resolver<T?>) {
      if (t != null) try {
        t.invoke { result ->
          if (result != null) try {
            resolver.resolve(result, null)
          } catch (e: Throwable) {
            LogUtil.e(TAG, e)
          }
        }
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      } else if (e != null) try {
        e.invoke { error ->
          if (error != null) try {
            val exception = RuntimeException(error)
            resolver.resolve(null, exception)
          } catch (e: Throwable) {
            LogUtil.e(TAG, e)
          }
        }
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      }
    }
  })

  override fun foldToPromiseOnUI(): Promise<T?> = Promise(object : CallbackWithResolver<Any, T?> {
    override fun call(arg: Any, resolver: Resolver<T?>) {
      if (t != null) try {
        t.invoke { result ->
          if (result != null) try {
            promise.executeOnUi {
              resolver.resolve(result, null)
            }
          } catch (e: Throwable) {
            LogUtil.e(TAG, e)
          }
        }
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      } else if (e != null) try {
        e.invoke { error ->
          if (error != null) try {
            promise.executeOnUi {
              resolver.resolve(null, error)
            }
          } catch (e: Throwable) {
            LogUtil.e(TAG, e)
          }
        }
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      }
    }
  })

  companion object {
    val TAG: String = LogUtil.makeTag(SyncEither::class.java)
    val promise: AndroidPromise = AndroidPromise.instance()
  }
}

/**
 *
 */
class AsyncRight<T : Any, E : Throwable>(
    /**
     *
     */
    t: (resolve: (T?) -> Unit) -> Unit) : AsyncEither<T, E>(
    /**
     *
     */
    t,
    /**
     *
     */
    null)

/**
 *
 */
class AsyncLeft<E : Throwable, T : Any>(
    /**
     *
     */
    e: (resolve: (E?) -> Unit) -> Unit) : AsyncEither<T, E>(
    /**
     *
     */
    null,
    /**
     *
     */
    e)

