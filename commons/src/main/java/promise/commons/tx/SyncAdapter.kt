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

import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutionException
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class SyncAdapter<T> : Future<T?> {
  private val mReadyLatch = CountDownLatch(1)
  private var mResult: T? = null
  private var mException: Throwable? = null

  /**
   * Sets the result. If another thread has called [.get], they will immediately receive the
   * value. set or setException must only be called once.
   */
  fun set(result: T?) {
    checkNotSet(result)
    mResult = result
    mReadyLatch.countDown()
  }

  /**
   * Sets the exception. If another thread has called [.get], they will immediately receive
   * the exception. set or setException must only be called once.
   */
  fun setException(exception: Throwable?) {
    checkNotSet(exception)
    mException = exception
    mReadyLatch.countDown()
  }

  override fun cancel(mayInterruptIfRunning: Boolean): Boolean =
      throw UnsupportedOperationException()

  override fun isCancelled(): Boolean = false

  override fun isDone(): Boolean = mReadyLatch.count == 0L

  @Throws(InterruptedException::class, ExecutionException::class)
  override fun get(): T? {
    mReadyLatch.await()
    if (mException != null) throw ExecutionException(mException)
    return mResult
  }

  /**
   * Wait up to the timeout time for another Thread to set a value on this future. If a value has
   * already been set, this method will return immediately.
   *
   *
   * NB: For simplicity, we catch and wrap InterruptedException. Do NOT use this class if you
   * are in the 1% of cases where you actually want to handle that.
   */
  @Throws(InterruptedException::class, ExecutionException::class, TimeoutException::class)
  override fun get(timeout: Long, unit: TimeUnit): T? {
    if (!mReadyLatch.await(timeout, unit)) throw TimeoutException("Timed out waiting for result")
    if (mException != null) throw ExecutionException(mException)
    return mResult
  }

  /**
   * Convenience wrapper for [.get] that re-throws get()'s Exceptions as
   * RuntimeExceptions.
   */
  val orThrow: T?
    get() = try {
      get()
    } catch (e: InterruptedException) {
      throw RuntimeException(e)
    } catch (e: ExecutionException) {
      throw RuntimeException(e)
    }

  /**
   * Convenience wrapper for [.get] that re-throws get()'s Exceptions as
   * RuntimeExceptions.
   */
  fun getOrThrow(timeout: Long, unit: TimeUnit): T? = try {
    get(timeout, unit)
  } catch (e: InterruptedException) {
    throw RuntimeException(e)
  } catch (e: ExecutionException) {
    throw RuntimeException(e)
  } catch (e: TimeoutException) {
    throw RuntimeException(e)
  }

  private fun checkNotSet(data: Any?) {
    if (mReadyLatch.count == 0L) throw RuntimeException("Result has already been set! " + data.toString())
  }
}