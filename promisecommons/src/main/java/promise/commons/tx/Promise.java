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

package promise.commons.tx;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import promise.commons.AndroidPromise;


public class Promise<R> {
  static final ExecutorService barrier = Executors.newSingleThreadExecutor();
  static final ExecutorService threadPool = AndroidPromise.instance().executor();
  public static Object Void;
  private static Handler handler = new Handler(Looper.myLooper());
  private State state;
  private R result;
  private RuntimeException error;
  private List<Resolver> handlers = new ArrayList<>();

  public Promise() {
    this.state = State.Fulfilled;
  }

  public <A> Promise(final CallbackWithResolver<? super A, R> callback) {
    this.state = State.Pending;
    final Resolver<? super R> finalResolver = (result, error) -> {
      final List<Resolver> nextPromises = new ArrayList<>();
      final CountDownLatch signal = new CountDownLatch(1);
      Promise.barrier.execute(() -> {
        if (Promise.this.getState() == State.Pending) {
          nextPromises.addAll(Promise.this.handlers);
          Promise.this.result = result;
          Promise.this.error = error;
          Promise.this.state = error != null ? State.Rejected : State.Fulfilled;
        }
        signal.countDown();
      });

      try {
        signal.await();
      } catch (InterruptedException ignored) {
      }

      for (Resolver next : nextPromises) next.resolve(result, error);
    };

    final Resolver<? super R> resolver = (Resolver<R>) (result, error) -> {
      if (result != null && error != null)
        throw new IllegalArgumentException("result and error must both be null");
      if (Promise.this.state == State.Pending) {
        if (result instanceof Promise)
          ((Promise<R>) result).pipe((Resolver<R>) finalResolver);
        else finalResolver.resolve(result, error);
      }
    };

    handler.post(() -> {
      try {
        callback.call(null, resolver);
      } catch (RuntimeException ex) {
        finalResolver.resolve(null, ex);
      }
    });
  }

  public <A> Promise(final long delayMillis, final CallbackWithResolver<? super A, R> callback) {
    this((CallbackWithResolver<A, R>) (arg, resolver) -> handler.postDelayed(() -> {
      try {
        callback.call(arg, resolver);
      } catch (RuntimeException ex) {
        resolver.resolve(null, ex);
      }
    }, delayMillis));
  }

  public <A> Promise(final long delayMillis, final Callback2<? super A, ? extends R> callback) {
    this((arg, resolver) -> handler.postDelayed(() -> {
      try {
        resolver.resolve(callback.call((A) arg), null);
      } catch (RuntimeException ex) {
        resolver.resolve(null, ex);
      }
    }, delayMillis));
  }

  public <A> Promise(final long delayMillis, final VoidArgCallback<? extends R> callback) {
    this((CallbackWithResolver<A, R>) (arg, resolver) -> handler.postDelayed(() -> {
      try {
        resolver.resolve(callback.call(), null);
      } catch (RuntimeException ex) {
        resolver.resolve(null, ex);
      }
    }, delayMillis));
  }

  public <A> Promise(final Callback2<? super A, ? extends R> callback) {
    this((arg, resolver) -> {
      try {
        resolver.resolve(callback.call((A) arg), null);
      } catch (RuntimeException ex) {
        resolver.resolve(null, ex);
      }
    });
  }

  public <N> Promise(final RuntimeException error) {
    this((arg, resolver) -> resolver.resolve(null, error));
  }

  public static <R> Promise<R> resolve(Promise<R> promise) {
    return promise;
  }

  public static <R> Promise<R> resolve(final R result) {
    return new Promise<>((arg, resolver) -> resolve(result));
  }

  public static <A, R> Promise<R> resolve(final RuntimeException ex) {
    return new Promise<>((CallbackWithResolver<A, R>)
        (arg, resolver) -> resolver.resolve(null, ex));
  }

  public static <A, R> Promise<List<R>> all(final List<? extends Promise<R>> promises) {
    return new Promise<>((CallbackWithResolver<A, List<R>>) (arg, resolver) -> {
      final AtomicInteger totalCount = new AtomicInteger(promises.size());
      for (Promise<R> promise : promises) {
        promise.pipe((result, error) -> {
          if (error != null)
            resolver.resolve(null, new RuntimeException("one of promise in promises was rejected", error));
          else if (totalCount.decrementAndGet() == 0) {
            List<R> results = new ArrayList<>(promises.size());
            for (Promise<R> promise1 : promises) results.add(promise1.result);
            resolver.resolve(results, null);
          }
        });
      }
    });
  }

  public static <A, R> Promise<R> race(final List<? extends Promise<R>> promises) {
    return new Promise<>((CallbackWithResolver<A, R>) (arg, resolver) -> {
      final AtomicInteger totalCount = new AtomicInteger(promises.size());
      for (Promise<R> promise : promises)
        promise.pipe((result, ex) -> {
          if (ex == null) resolver.resolve(result, null);
          else if (totalCount.decrementAndGet() == 0)
            resolver.resolve(null, new RuntimeException("all promise were rejected."));
        });
    });
  }

  public State getState() {
    return state;
  }

  public R getResult() {
    return result;
  }

  public RuntimeException getError() {
    return error;
  }

  public boolean isSuccess() {
    return error == null;
  }

  public void pipe(Resolver<R> resolver) {
    if (this.state == State.Pending) this.handlers.add(resolver);
    else resolver.resolve(result, error);
  }

  private <A, R> Promise<R> __pipe(final Promise<A> self, final Piper<? super A, R> piper) {
    return new Promise<>((CallbackWithResolver<A, R>) (arg, resolver) ->
        self.pipe((result, error) ->
            piper.pipe(result, error, resolver)));
  }

  public <N> Promise<N> then(final Callback2<? super R, ? extends N> then) {
    return __pipe(this, (arg, error, resolver) -> {
      if (error != null) resolver.resolve(null, error);
      else handler.post(() -> {
        try {
          resolver.resolve(then.call(arg), null);
        } catch (RuntimeException ex) {
          resolver.resolve(null, ex);
        }
      });
    });
  }

  public void then(final VoidReturnCallback<? super R> then) {
    __pipe(this, (arg, error, resolver) -> {
      if (error != null) resolver.resolve(null, error);
      else handler.post(() -> {
        try {
          then.call(arg);
          resolver.resolve(null, null);
        } catch (RuntimeException ex) {
          resolver.resolve(null, ex);
        }
      });
    });
  }

  public void then(final VoidArgVoidReturnCallback then) {
    __pipe(this, (arg, error, resolver) -> {
      if (error != null) resolver.resolve(null, error);
      else handler.post(() -> {
        try {
          then.call();
          resolver.resolve(null, null);
        } catch (RuntimeException ex) {
          resolver.resolve(null, ex);
        }
      });
    });
  }

  public <N> Promise<N> then(final VoidArgCallback<? extends N> then) {
    return __pipe(this, (arg, error, resolver) -> {
      if (error != null) resolver.resolve(null, error);
      else handler.post(() -> {
        try {
          resolver.resolve(then.call(), null);
        } catch (RuntimeException ex) {
          resolver.resolve(null, ex);
        }
      });
    });
  }

  public <N> Promise<N> then(final CallbackWithResolver<? super R, N> then) {
    return __pipe(this, (arg, error, resolver) -> {
      if (error != null) resolver.resolve(null, error);
      else handler.post(() -> {
        try {
          then.call(arg, resolver);
        } catch (RuntimeException ex) {
          resolver.resolve(null, ex);
        }
      });
    });
  }

  public <N> Promise<N> thenAsync(final Callback2<? super R, ? extends N> then) {
    return __pipe(this, (arg, error, resolver) -> {
      if (error != null) resolver.resolve(null, error);
      else threadPool.execute(() -> {
        try {
          resolver.resolve(then.call(arg), null);
        } catch (RuntimeException ex) {
          resolver.resolve(null, ex);
        }
      });
    });
  }

  public <N> Promise<N> thenAsync(final VoidArgCallback<? extends N> then) {
    return __pipe(this, (arg, error, resolver) -> {
      if (error != null) resolver.resolve(null, error);
      else threadPool.execute(() -> {
        try {
          resolver.resolve(then.call(), null);
        } catch (RuntimeException ex) {
          resolver.resolve(null, ex);
        }
      });
    });
  }

  public void thenAsync(final VoidReturnCallback<? super R> then) {
    __pipe(this, (arg, error, resolver) -> {
      if (error != null) resolver.resolve(null, error);
      else threadPool.execute(() -> {
        try {
          then.call(arg);
          resolver.resolve(null, null);
        } catch (RuntimeException ex) {
          resolver.resolve(null, ex);
        }
      });
    });
  }

  public void thenAsync(final VoidArgVoidReturnCallback then) {
    __pipe(this, (arg, error, resolver) -> {
      if (error != null) resolver.resolve(null, error);
      else threadPool.execute(() -> {
        try {
          then.call();
          resolver.resolve(null, null);
        } catch (RuntimeException ex) {
          resolver.resolve(null, ex);
        }
      });
    });
  }

  public <N> Promise<N> thenAsync(final CallbackWithResolver<? super R, N> then) {
    return __pipe(this, (arg, error, resolver) -> {
      if (error != null) resolver.resolve(null, error);
      else threadPool.execute(() -> {
        try {
          then.call(arg, resolver);
        } catch (RuntimeException ex) {
          resolver.resolve(null, ex);
        }
      });
    });
  }

  public <N> Promise<N> thenDelay(final long delayMillis, final Callback2<? super R, ? extends N> then) {
    return __pipe(this, (arg, error, resolver) -> {
      if (error != null) resolver.resolve(null, error);
      else handler.postDelayed(() -> {
        try {
          resolver.resolve(then.call(arg), null);
        } catch (RuntimeException ex) {
          resolver.resolve(null, ex);
        }
      }, delayMillis);
    });
  }

  public <N> Promise<N> thenDelay(final long delayMillis, final VoidArgCallback<? extends N> then) {
    return __pipe(this, (arg, error, resolver) -> {
      if (error != null) resolver.resolve(null, error);
      else handler.postDelayed(() -> {
        try {
          resolver.resolve(then.call(), null);
        } catch (RuntimeException ex) {
          resolver.resolve(null, ex);
        }
      }, delayMillis);
    });
  }

  public void thenDelay(final long delayMillis, final VoidReturnCallback<? super R> then) {
    __pipe(this, (arg, error, resolver) -> {
      if (error != null) resolver.resolve(null, error);
      else handler.postDelayed(() -> {
        try {
          then.call(arg);
          resolver.resolve(null, null);
        } catch (RuntimeException ex) {
          resolver.resolve(null, ex);
        }
      }, delayMillis);
    });
  }

  public void thenDelay(final long delayMillis, final VoidArgVoidReturnCallback then) {
    __pipe(this, (arg, error, resolver) -> {
      if (error != null) resolver.resolve(null, error);
      else handler.postDelayed(() -> {
        try {
          then.call();
          resolver.resolve(null, null);
        } catch (RuntimeException ex) {
          resolver.resolve(null, ex);
        }
      }, delayMillis);
    });
  }

  public <N> Promise<N> thenDelay(final long delayMillis, final CallbackWithResolver<? super R, N> then) {
    return __pipe(this, (arg, error, resolver) -> {
      if (error != null) resolver.resolve(null, error);
      else handler.postDelayed(() -> {
        try {
          then.call(arg, resolver);
        } catch (RuntimeException ex) {
          resolver.resolve(null, ex);
        }
      }, delayMillis);
    });
  }

  public Promise<R> error(final Callback2<RuntimeException, ? extends R> callback) {
    return __pipe(this, (arg, error, resolver) -> {
      if (error != null) handler.post(() -> {
        try {
          resolver.resolve(callback.call(error), null);
        } catch (RuntimeException ex) {
          resolver.resolve(null, ex);
        }
      });
      else {
        resolver.resolve(arg, null);
      }
    });
  }

  public Promise<R> error(final VoidArgCallback<? extends R> callback) {
    return __pipe(this, (arg, error, resolver) -> {
      if (error != null) handler.post(() -> {
        try {
          resolver.resolve(callback.call(), null);
        } catch (RuntimeException ex) {
          resolver.resolve(null, ex);
        }
      });
      else resolver.resolve(arg, null);
    });
  }

  public void error(final VoidReturnCallback<RuntimeException> callback) {
    __pipe(this, (arg, error, resolver) -> {
      if (error != null) handler.post(() -> {
        try {
          callback.call(error);
          resolver.resolve(null, null);
        } catch (RuntimeException ex) {
          resolver.resolve(null, ex);
        }
      });
      else resolver.resolve(arg, null);
    });
  }

  public void error(final VoidArgVoidReturnCallback callback) {
    __pipe(this, (arg, error, resolver) -> {
      if (error != null) handler.post(() -> {
        try {
          callback.call();
          resolver.resolve(null, null);
        } catch (RuntimeException ex) {
          resolver.resolve(null, ex);
        }
      });
      else resolver.resolve(arg, null);
    });
  }

  public Promise<R> errorAsync(final Callback2<RuntimeException, ? extends R> callback) {
    return __pipe(this, (arg, error, resolver) -> {
      if (error != null) threadPool.execute(() -> {
        try {
          resolver.resolve(callback.call(error), null);
        } catch (RuntimeException ex) {
          resolver.resolve(null, ex);
        }
      });
      else resolver.resolve(arg, null);
    });
  }

  public Promise<R> errorAsync(final VoidArgCallback<? extends R> callback) {
    return __pipe(this, (arg, error, resolver) -> {
      if (error != null) threadPool.execute(() -> {
        try {
          resolver.resolve(callback.call(), null);
        } catch (RuntimeException ex) {
          resolver.resolve(null, ex);
        }
      });
      else resolver.resolve(arg, null);
    });
  }

  public void errorAsync(final VoidReturnCallback<RuntimeException> callback) {
    __pipe(this, (arg, error, resolver) -> {
      if (error != null) threadPool.execute(() -> {
        try {
          callback.call(error);
          resolver.resolve(null, null);
        } catch (RuntimeException ex) {
          resolver.resolve(null, ex);
        }
      });
      else resolver.resolve(arg, null);
    });
  }

  public void errorAsync(final VoidArgVoidReturnCallback callback) {
    __pipe(this, (arg, error, resolver) -> {
      if (error != null) threadPool.execute(() -> {
        try {
          callback.call();
          resolver.resolve(null, null);
        } catch (RuntimeException ex) {
          resolver.resolve(null, ex);
        }
      });
      else resolver.resolve(arg, null);
    });
  }

  public <N> Promise<N> always(final Callback2<Object, ? extends N> always) {
    return __pipe(this, (arg, error, resolver) -> handler.post(() -> {
      try {
        resolver.resolve(always.call(error != null ? error : arg), null);
      } catch (RuntimeException ex) {
        resolver.resolve(null, ex);
      }
    }));
  }

  public <N> Promise<N> always(final VoidArgCallback<? extends N> always) {
    return __pipe(this, (arg, error, resolver) -> handler.post(() -> {
      try {
        resolver.resolve(always.call(), null);
      } catch (RuntimeException ex) {
        resolver.resolve(null, ex);
      }
    }));
  }

  public void always(final VoidReturnCallback<? super Object> always) {
    __pipe(this, (arg, error, resolver) -> handler.post(() -> {
      try {
        always.call(error != null ? error : arg);
        resolver.resolve(null, null);
      } catch (RuntimeException ex) {
        resolver.resolve(null, ex);
      }
    }));
  }

  public void always(final VoidArgVoidReturnCallback always) {
    __pipe(this, (arg, error, resolver) -> handler.post(() -> {
      try {
        always.call();
        resolver.resolve(null, null);
      } catch (RuntimeException ex) {
        resolver.resolve(null, ex);
      }
    }));
  }

  public <N> Promise<N> alwaysAsync(final Callback2<Object, ? extends N> always) {
    return __pipe(this, (arg, error, resolver) -> threadPool.execute(() -> {
      try {
        resolver.resolve(always.call(error != null ? error : arg), null);
      } catch (RuntimeException ex) {
        resolver.resolve(null, ex);
      }
    }));
  }

  public <N> Promise<N> alwaysAsync(final VoidArgCallback<? extends N> always) {
    return __pipe(this, (arg, error, resolver) -> threadPool.execute(() -> {
      try {
        resolver.resolve(always.call(), null);
      } catch (RuntimeException ex) {
        resolver.resolve(null, ex);
      }
    }));
  }

  public void alwaysAsync(final VoidReturnCallback<Object> always) {
    __pipe(this, (arg, error, resolver) -> threadPool.execute(() -> {
      try {
        always.call(error != null ? error : arg);
        resolver.resolve(null, null);
      } catch (RuntimeException ex) {
        resolver.resolve(null, ex);
      }
    }));
  }

  public void alwaysAsync(final VoidArgVoidReturnCallback always) {
    __pipe(this, (arg, error, resolver) -> threadPool.execute(() -> {
      try {
        always.call();
        resolver.resolve(null, null);
      } catch (RuntimeException ex) {
        resolver.resolve(null, ex);
      }
    }));
  }

  public enum State {
    Pending,
    Fulfilled,
    Rejected
  }

  public interface Piper<A, R> {
    void pipe(A arg, RuntimeException error, Resolver<? super R> resolver);
  }
}