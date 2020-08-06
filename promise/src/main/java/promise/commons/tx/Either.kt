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

import android.os.Looper
import promise.commons.AndroidPromise
import promise.commons.data.log.CommonLogAdapter
import promise.commons.data.log.LogUtil

/**
 *
 */
interface Either<T: Any> {
  /**
   *
   */
  fun fold(
      /**
       *
       */
      res: (
          t: T?
      ) -> Unit,
      /**
       *
       */
      err: (
      (
          e: Throwable
      ) -> Unit
      )? = null)

  /**
   *
   */
  @Throws(Throwable::class)
  fun foldSync(): T?

  /**
   *
   */
  fun foldOnUI(
      /**
       *
       */
      res: (
          t: T?
      ) -> Unit,
      /**
       *
       */
      err: (
      (
          e: Throwable
      ) -> Unit
      )? = null)

  fun fold(): PromiseCallback<T>

  fun foldOnUI(): PromiseCallback<T>

  /**
   *
   */
  fun fold(
      /**
       *
       */
      promiseResult: PromiseResult<T, Throwable>)

  fun foldOnUI(
      /**
       *
       */
      promiseResult: PromiseResult<T, Throwable>)

  /**
   *
   */
  fun foldToPromise(): Promise<T?>

  /**
   *
   */
  fun foldToPromiseOnUI(): Promise<T?>

}

/**
 *
 */
sealed class SyncEither<T : Any>(
    /**
     *
     */
    val t: T?,
    /**
     *
     */
    val e: Throwable?) : Either<T> {
  /**
   *
   */
  override fun fold(
      /**
       *
       */
      res: (t: T?) -> Unit,
      /**
       *
       */
      err: ((e: Throwable) -> Unit)?) {
    if (t != null) try {
      res(t)
    } catch (e: Throwable) {
      LogUtil.e(TAG, e)
    } else if (e != null) try {
      err?.invoke(e)
    } catch (e: Throwable) {
      LogUtil.e(TAG, e)
    }
  }

  @Throws(Exception::class)
  override fun foldSync(): T? = when {
    t != null -> t
    e != null -> throw e
    else -> throw Exception("no data to unfold")
  }

  /**
   *
   */
  override fun foldOnUI(
      /**
       *
       */
      res: (t: T?) -> Unit,
      /**
       *
       */
      err: ((e: Throwable) -> Unit)?) {
    if (t != null) try {
      promise.executeOnUi {
        res(t)
      }
    } catch (e: Throwable) {
      LogUtil.e(TAG, e)
    } else if (e != null) try {
      promise.executeOnUi {
        err?.invoke(e)
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
      promiseResult: PromiseResult<T, Throwable>) {
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

  override fun foldOnUI(promiseResult: PromiseResult<T, Throwable>) {
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
    init {
      LogUtil.addLogAdapter(CommonLogAdapter())
    }
    val promise: AndroidPromise = AndroidPromise.instance()
  }
}

/**
 *
 */
class Right<T : Any>(
    /**
     *
     */
    t: T?) : SyncEither<T>(
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
class Left<T: Any>(
    /**
     *
     */
    e: Throwable) : SyncEither<T>(
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
class AsyncEither<T : Any>(
    /**
     *
     */
    val t: (resolve: (T?) -> Unit, reject: (Throwable) -> Unit) -> Unit) : Either<T> {

  private var syncAdapter: SyncAdapter<T>? = null

  private fun getAdapter(): SyncAdapter<T> {
    if (syncAdapter == null) syncAdapter = SyncAdapter()
    return syncAdapter!!
  }

  @JvmOverloads
  fun yield(
      res: (t: T?) -> Unit,
      /**
       *
       */
      err: ((e: Throwable) -> Unit)? = null) = promise.execute {
    try {
      t({ result ->
        if (result != null) try {
          res(result)
        } catch (e: Throwable) {
          err?.invoke(e)
        }
      }, { error ->
        try {
          err?.invoke(error)
        } catch (e: Throwable) {
          LogUtil.e(TAG, e)
        }
      })
    } catch (e: Throwable) {
      LogUtil.e(TAG, e)
    }
  }

  @JvmOverloads
  fun yieldOnUi(
      res: (t: T?) -> Unit,
      /**
       *
       */
      err: ((e: Throwable) -> Unit)? = null) = promise.execute {
    try {
      t({ result ->
        if (result != null) try {
          promise.executeOnUi {
            res(result)
          }
        } catch (e: Throwable) {
          err?.invoke(e)
        }
      }, { error ->
        try {
          promise.executeOnUi {
            err?.invoke(error)
          }
        } catch (e: Throwable) {
          LogUtil.e(TAG, e)
        }
      })
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
      res: (t: T?) -> Unit,
      /**
       *
       */
      err: ((e: Throwable) -> Unit)?) = promise.execute {
    try {
      t({ result ->
        if (result != null) try {
          getAdapter().set(result)
          res(result)
        } catch (e: Throwable) {
          err?.invoke(e)
        }
      }, { error ->
        try {
          getAdapter().setException(error)
          err?.invoke(error)
        } catch (e: Throwable) {
          LogUtil.e(TAG, e)
        }
      })
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
      res: (t: T?) -> Unit,
      /**
       *
       */
      err: ((e: Throwable) -> Unit)?) = promise.execute {
    try {
      t({ result ->
        if (result != null) try {
          getAdapter().set(result)
          promise.executeOnUi {
            res(result)
          }
        } catch (e: Throwable) {
          err?.invoke(e)
        }
      }, { error ->
        try {
          getAdapter().setException(error)
          promise.executeOnUi {
            err?.invoke(error)
          }
        } catch (e: Throwable) {
          LogUtil.e(TAG, e)
        }
      })
    } catch (e: Throwable) {
      LogUtil.e(TAG, e)
    }
  }

  @Throws(Exception::class)
  override fun foldSync(): T? {
    if (Thread.currentThread() == Looper.getMainLooper().thread) throw RuntimeException("cant be called from main thread")
    fold({
      getAdapter().set(it)
    }, {
      getAdapter().setException(it)
    })
    return getAdapter().get()
  }

  /**
   *
   */
  override fun fold(): PromiseCallback<T> = PromiseCallback { resolve, reject ->
    promise.execute {
      try {
        t({ result ->
          if (result != null) try {
            getAdapter().set(result)
            resolve(result)
          } catch (e: Throwable) {
            reject(e)
          }
        }, { error ->
          try {
            getAdapter().setException(error)
            reject(error)
          } catch (e: Throwable) {
            LogUtil.e(TAG, e)
          }
        })
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
      try {
        t({ result ->
          if (result != null) try {
            getAdapter().set(result)
            promise.executeOnUi {
              resolve(result)
            }
          } catch (e: Throwable) {
            reject(e)
          }
        }, { error ->
          try {
            getAdapter().setException(error)
            promise.executeOnUi {
              reject(error)
            }
          } catch (e: Throwable) {
            LogUtil.e(TAG, e)
          }
        })
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
      promiseResult: PromiseResult<T, Throwable>) {
    promise.execute {
      try {
        t({ result ->
          if (result != null) try {
            getAdapter().set(result)
            promiseResult.response(result)
          } catch (e: Throwable) {
            promiseResult.error(e)
          }
        }, { error ->
          try {
            getAdapter().setException(error)
            promiseResult.error(error)
          } catch (e: Throwable) {
            LogUtil.e(TAG, e)
          }
        })
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      }
    }
  }

  override fun foldOnUI(promiseResult: PromiseResult<T, Throwable>) {
    promise.execute {
      try {
        t({ result ->
          if (result != null) try {
            getAdapter().set(result)
            promise.executeOnUi {
              promiseResult.response(result)
            }
          } catch (e: Throwable) {
            promiseResult.error(e)
          }
        }, { error ->
          try {
            getAdapter().setException(error)
            promise.executeOnUi {
              promiseResult.error(error)
            }
          } catch (e: Throwable) {
            LogUtil.e(TAG, e)
          }
        })
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
      try {
        t({ result ->
          if (result != null) try {
            getAdapter().set(result)
            resolver.resolve(result, null)
          } catch (e: Throwable) {
            resolver.resolve(null, e)
          }
        }, { error ->
          try {
            getAdapter().setException(error)
            resolver.resolve(null, error)
          } catch (e: Throwable) {
            LogUtil.e(TAG, e)
          }
        })
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      }
    }
  })

  override fun foldToPromiseOnUI(): Promise<T?> = Promise(object : CallbackWithResolver<Any, T?> {
    override fun call(arg: Any, resolver: Resolver<T?>) {
      try {
        t({ result ->
          if (result != null) try {
            getAdapter().set(result)
            promise.executeOnUi {
              resolver.resolve(result, null)
            }
          } catch (e: Throwable) {
            promise.executeOnUi {
              resolver.resolve(null, e)
            }
          }
        }, { error ->
          try {
            getAdapter().setException(error)
            promise.executeOnUi {
              resolver.resolve(null, error)
            }
          } catch (e: Throwable) {
            LogUtil.e(TAG, e)
          }
        })
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      }
    }
  })

  companion object {
    val TAG: String = LogUtil.makeTag(SyncEither::class.java)
    init {
      LogUtil.addLogAdapter(CommonLogAdapter())
    }
    val promise: AndroidPromise = AndroidPromise.instance()

  }
}

