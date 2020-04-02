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

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.ArrayMap
import androidx.core.util.Pair
import kotlinx.android.synthetic.main.activity_main.*
import promise.commons.AndroidPromise
import promise.commons.data.log.CommonLogAdapter
import promise.commons.data.log.LogUtil
import promise.commons.makeInstance
import promise.commons.pref.Preferences
import promise.commons.tx.AsyncEither
import promise.commons.tx.Either
import promise.commons.tx.Left
import promise.commons.tx.Transaction
import promise.commons.tx.Transaction.CallBackExecutor
import promise.commons.tx.TransactionManager

interface FakeStringsRepo {
  fun getStrings(throwexception: Boolean): Either<Array<String>>
}

class FakeRepositoryImpl : FakeStringsRepo {
  override fun getStrings(throwexception: Boolean): Either<Array<String>> {
    if (throwexception) return Left(Exception("should throw exception"))
    return AsyncEither { resolve, _ ->
      Thread.sleep(5000)
      resolve(arrayOf("somekey", "somekey1",
          "somekey2", "somekey3",
          "somekey4", "somekey5"))
      Thread.sleep(5000)
      resolve(arrayOf("somekey", "somekey1",
          "somekey2", "somekey3",
          "somekey4", "somekey5"))
      Thread.sleep(5000)
      resolve(arrayOf("somekey", "somekey1",
          "somekey2", "somekey3",
          "somekey4", "somekey5"))
    }
  }
}

class MainActivity : AppCompatActivity() {

  private val preferences = makeInstance(Preferences::class, arrayOf(PREFERENCE_NAME)) as Preferences

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

          when {
            args != null -> preferences.getString(args)
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
        LogUtil.d(TAG, " on progress ", t!!)
        return t
      }

      /**
       * returns the progress of the current result
       * @param x current executed progress [.onCalculateProgress]
       */
      override fun onProgress(x: String?) {
        progress_textview.text = x
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    preferences.save(ArrayMap<String, Any>().apply {
      put("somekey", "key0")
      put("somekey1", "key1")
      put("somekey2", "key2")
      put("somekey3", "key3")
      put("somekey4", "key4")
      put("somekey5", "key5")
    })
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    val fakeStringsRepo: FakeStringsRepo = FakeRepositoryImpl()
    val either = fakeStringsRepo.getStrings(true)
    if (either is AsyncEither) either.yieldOnUi({ strings ->
      title_textview.text = "Started reading"
      LogUtil.e(TAG, strings.toString())
      TransactionManager.instance().execute(transaction.complete {
        preferences_textview.text = it.reverse().toString()
        title_textview.text = "Completed reading"
      }, Pair(strings, 100))
    }, {
      LogUtil.e(TAG, "execution error ", it)
    })
    else AndroidPromise.instance().execute {
      try {
        val data = either.foldSync()
        TransactionManager.instance().execute(transaction.complete {
          preferences_textview.text = it.reverse().toString()
          title_textview.text = "Completed reading"
        }, Pair(data, 100))
      } catch (e: java.lang.Exception) {
        LogUtil.e(TAG, e)
      }

    }

    /*fakeStringsRepo.getStrings().fold()

        .then { strings ->
          TransactionManager.instance().execute(transaction.complete {
            preferences_textview.text = it.reverse().toString()
          }, Pair(strings, 1000))
          strings
        }.execute()*/

    /* PromiseCallback<Array<String>> { resolve, _ ->
             resolve(arrayOf("somekey", "somekey1",
                 "somekey2", "somekey3",
                 "somekey4", "somekey5"))
         }
     .then {
         title_textview.text = "Started reading"
         TransactionManager.instance().execute(transaction.complete {
             preferences_textview.text = it.reverse().toString()
         }, Pair(it, 1000))
         null
     }
     .execute()*/
  }

  override fun finish() {
    preferences.clearAll()
    super.finish()
  }

  companion object {
    val TAG: String = LogUtil.makeTag(MainActivity::class.java)
    init {
      LogUtil.addLogAdapter(CommonLogAdapter())
    }
    const val PREFERENCE_NAME = "pref_name"
  }
}
