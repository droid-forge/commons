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

import promise.commons.data.log.LogUtil
import promise.commons.model.List

/**
 * promise callback executor chain
 *
 * @property resultConsumer
 */

class PromiseCallback<RESULT>(private val resultConsumer: (resolve: (RESULT) -> Unit, reject: (Throwable) -> Unit) -> Unit) {
  /**
   * holds the result of then execution
   */
  private var lastResult: RESULT? = null

  /**,
   * last error caught by the system
   */
  private var lastError: Throwable? = null

  /**
   * list of result acceptors
   */
  private val resultAcceptors: List<(RESULT) -> RESULT?> = List()

  /**
   * list of error acceptors
   */
  private val errorAcceptors: List<(Throwable) -> Throwable?> = List()

  /**
   * registers result acceptors
   *
   * @param resultAcceptor result acceptor
   * @return
   */
  fun then(resultAcceptor: (RESULT) -> RESULT?): PromiseCallback<RESULT> {
    this.resultAcceptors.add(resultAcceptor)
    return this
  }

  /**
   * registers error acceptors
   *
   * @param errorAcceptor error acceptor
   * @return
   */
  fun error(errorAcceptor: (Throwable) -> Throwable?): PromiseCallback<RESULT> {
    this.errorAcceptors.add(errorAcceptor)
    return this
  }

  /**
   * executes the promise code
   *
   */
  fun execute() {
    try {
      resultConsumer({ result ->
        try {
          lastResult = result
          cycleResultAccepting()
        } catch (e: Throwable) {
          lastError = e
          cycleErrorAccepting()
        }
      }, { throwable ->
        lastError = throwable
        cycleErrorAccepting()
      })
    } catch (e: Throwable) {
      lastError = e
      cycleErrorAccepting()
    }
  }

  private fun cycleResultAccepting() {
    for (acceptor in resultAcceptors) {
      lastResult = (if (lastResult != null) {
        val stepResponse = acceptor(lastResult!!)
        stepResponse
      } else acceptor(lastResult!!))
    }
  }

  private fun cycleErrorAccepting() {
    if (errorAcceptors.isEmpty()) {
      LogUtil.e(TAG, "No error acceptors found ")
      return
    }
    for (acceptor in errorAcceptors) {
      lastError = (if (lastError != null) {
        val stepError = acceptor(lastError!!)
        if (stepError != null) {
          lastError = stepError
          cycleErrorAccepting()
          break
        }
        null
      } else acceptor(lastError!!))
    }
  }

  companion object {
    val TAG: String = LogUtil.makeTag(PromiseCallback::class.java)
  }

}