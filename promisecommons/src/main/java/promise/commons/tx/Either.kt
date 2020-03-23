/*
 * Copyright 2017, Peter Vincent
 *  Licensed under the Apache License, Version 2.0, Android Promise.
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package promise.commons.tx

import promise.commons.data.log.LogUtil

/**
 *
 */
sealed class Either<T : Any, E : Throwable>(
    /**
     *
     */
    val t: T?,
    /**
     *
     */
    val e: E?) {
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

  /**
   *
   */
  fun fold(): PromiseCallback<T> = PromiseCallback { resolve, reject ->
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
  fun fold(
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

  /**
   *
   */
  fun foldToPromise(): Promise<T?> = Promise(object : CallbackWithResolver<Any, T?> {
    override fun call(arg: Any, resolver: Resolver<T?>) {
      if (t != null) try {
        resolver.resolve(t, null)
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      } else if (e != null) try {
        val exception = RuntimeException(e)
        resolver.resolve(null, exception)
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      }
    }
  })

  companion object {
    val TAG: String = LogUtil.makeTag(Either::class.java)
  }
}

/**
 *
 */
class Right<T : Any, E : Throwable>(
    /**
     *
     */
    t: T) : Either<T, E>(
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
    e: E) : Either<T, E>(
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
typealias AsyncEitherFunc<T> = () -> T?

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
    val e: AsyncEitherFunc<E>?) {
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
      err: (e: E) -> Unit) {
    if (t != null) try {
      val result = t.invoke()
      if (result != null) try {
        res(result)
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      }
    } catch (e: Throwable) {
      LogUtil.e(TAG, e)
    } else if (e != null) try {
      val error = e.invoke()
      if (error != null) try {
        err(error)
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      }
    } catch (e: Throwable) {
      LogUtil.e(TAG, e)
    }
  }

  /**
   *
   */
  fun fold(): PromiseCallback<T> = PromiseCallback { resolve, reject ->
    if (t != null) try {
      val result = t.invoke()
      if (result != null) try {
        resolve(result)
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      }
    } catch (e: Throwable) {
      LogUtil.e(TAG, e)
    } else if (e != null) try {
      val error = e.invoke()
      if (error != null) try {
        reject(error)
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      }
    } catch (e: Throwable) {
      LogUtil.e(TAG, e)
    }
  }

  /**
   *
   */
  fun fold(
      /**
       *
       */
      promiseResult: PromiseResult<T, E>) {
    if (t != null) try {
      val result = t.invoke()
      if (result != null) try {
        promiseResult.response(result)
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      }
    } catch (e: Throwable) {
      LogUtil.e(TAG, e)
    } else if (e != null) try {
      val error = e.invoke()
      if (error != null) try {
        promiseResult.error(error)
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      }
    } catch (e: Throwable) {
      LogUtil.e(TAG, e)
    }
  }

  /**
   *
   */
  fun foldToPromise(): Promise<T?> = Promise(object : CallbackWithResolver<Any, T?> {
    override fun call(arg: Any, resolver: Resolver<T?>) {
      if (t != null) try {
        val result = t.invoke()
        if (result != null) try {
          resolver.resolve(result, null)
        } catch (e: Throwable) {
          LogUtil.e(TAG, e)
        }
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      } else if (e != null) try {
        val error = e.invoke()
        if (error != null) try {
          val exception = RuntimeException(error)
          resolver.resolve(null, exception)
        } catch (e: Throwable) {
          LogUtil.e(TAG, e)
        }
      } catch (e: Throwable) {
        LogUtil.e(TAG, e)
      }
    }
  })

  companion object {
    val TAG: String = LogUtil.makeTag(Either::class.java)
  }
}

/**
 *
 */
class AsyncRight<T : Any, E : Throwable>(
    /**
     *
     */
    t: () -> T?) : AsyncEither<T, E>(
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
    e: () -> E?) : AsyncEither<T, E>(
    /**
     *
     */
    null,
    /**
     *
     */
    e)

