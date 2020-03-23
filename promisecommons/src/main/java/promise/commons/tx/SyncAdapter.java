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

package promise.commons.tx;

import androidx.annotation.Nullable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.Future;

public class SyncAdapter<T> implements Future<T> {
  private final CountDownLatch mReadyLatch = new CountDownLatch(1);
  private @Nullable T mResult;
  private @Nullable Throwable mException;

  /**
   * Sets the result. If another thread has called {@link #get}, they will immediately receive the
   * value. set or setException must only be called once.
   */
  public void set(@Nullable T result) {
    checkNotSet();
    mResult = result;
    mReadyLatch.countDown();
  }

  /**
   * Sets the exception. If another thread has called {@link #get}, they will immediately receive
   * the exception. set or setException must only be called once.
   */
  public void setException(Throwable exception) {
    checkNotSet();
    mException = exception;
    mReadyLatch.countDown();
  }

  @Override
  public boolean cancel(boolean mayInterruptIfRunning) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isCancelled() {
    return false;
  }

  @Override
  public boolean isDone() {
    return mReadyLatch.getCount() == 0;
  }

  @Override
  public @Nullable T get() throws InterruptedException, ExecutionException {
    mReadyLatch.await();
    if (mException != null) throw new ExecutionException(mException);
    return mResult;
  }

  /**
   * Wait up to the timeout time for another Thread to set a value on this future. If a value has
   * already been set, this method will return immediately.
   *
   * NB: For simplicity, we catch and wrap InterruptedException. Do NOT use this class if you
   * are in the 1% of cases where you actually want to handle that.
   */
  @Override
  public @Nullable T get(long timeout, TimeUnit unit) throws
      InterruptedException, ExecutionException, TimeoutException {
    if (!mReadyLatch.await(timeout, unit))
      throw new TimeoutException("Timed out waiting for result");
    if (mException != null) throw new ExecutionException(mException);
    return mResult;
  }

  /**
   * Convenience wrapper for {@link #get()} that re-throws get()'s Exceptions as
   * RuntimeExceptions.
   */
  public @Nullable T getOrThrow() {
    try {
      return get();
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Convenience wrapper for {@link #get(long, TimeUnit)} that re-throws get()'s Exceptions as
   * RuntimeExceptions.
   */
  public @Nullable T getOrThrow(long timeout, TimeUnit unit) {
    try {
      return get(timeout, unit);
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      throw new RuntimeException(e);
    }
  }

  private void checkNotSet() {
    if (mReadyLatch.getCount() == 0) throw new RuntimeException("Result has already been set!");
  }
}