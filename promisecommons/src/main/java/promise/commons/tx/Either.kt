package promise.commons.tx

import promise.commons.data.log.LogUtil

sealed class Either<T : Any, E : Throwable>(val t: T?, val e: E?) {

  fun fold(res: (t: T) -> Unit, err: (e: E) -> Unit) {
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

  fun fold(promiseResult: PromiseResult<T, E>) {
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

  companion object {
    val TAG: String = LogUtil.makeTag(Either::class.java)
  }
}

class Right<T : Any>(t1: T) : Either<T, Throwable>(t1, null)

class Left<E : Throwable>(e1: E) : Either<Any, E>(null, e1)
