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

package promise.commonsapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Pair
import kotlinx.android.synthetic.main.activity_main.*
import promise.commons.tx.Transaction
import promise.commons.tx.Transaction.CallBackExecutor
import promise.commons.tx.TransactionManager

@SuppressLint("Registered")
class TransactionsActivity : AppCompatActivity() {

  private val transaction = object : Transaction<String, String, String>() {
    /**
     * gets the callback methods used for executing the transaction
     * @return a callbacks object
     */
    override fun getCallBackExecutor(): CallBackExecutor<String, String> =
        CallBackExecutor { args ->
          /**
           * @return the result of the task
           */
          // assuming this is some very heavy operation that is synchronous
          Thread.sleep(1000)
          when {
            args != null -> args
            else -> ""
          }
        }

    /**
     * if there's more than one params to execute on
     * this provided a callback object to notify on progress of
     * each consecutive result
     * @return the progress callback
     */
    override fun getProgress(): Progress<String, String> = object : Progress<String, String> {
      /**
       * calculates the progress value for the current result
       * @param t current result
       * @return a progress of the result
       */
      override fun onCalculateProgress(t: String?): String {
        // calculate progress value based on current result
        return t!!
      }

      /**
       * returns the progress of the current result
       * @param x current executed progress [.onCalculateProgress]
       */
      override fun onProgress(x: String?) {
        // publish the calculated progress
        progress_textview.text = x
      }
    }
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    // assume the background work needs two args to do their task
    TransactionManager.instance().execute(transaction.complete {
      preferences_textview.text = it.reverse().toString()
    }, Pair(arrayOf("arg1", "arg2"), 1000))
  }

}
