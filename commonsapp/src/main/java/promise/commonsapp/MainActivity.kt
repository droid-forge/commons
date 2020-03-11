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

package promise.commonsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.ArrayMap
import androidx.core.util.Pair
import kotlinx.android.synthetic.main.activity_main.*
import promise.commons.tx.PromiseCallback
import promise.commons.data.log.CommonLogAdapter
import promise.commons.data.log.LogUtil
import promise.commons.makeInstance
import promise.commons.pref.Preferences
import promise.commons.tx.CallbackWithResolver
import promise.commons.tx.Promise
import promise.commons.tx.Resolver
import promise.commons.tx.Tx
import promise.commons.tx.Tx.CallBackExecutor
import promise.commons.tx.TxManager
import promise.commons.tx.VoidArgVoidReturnCallback
import promise.commons.tx.VoidReturnCallback

class SomeType {
    var name: String? = null
    override fun toString(): String = "SomeType$name"
}

class MainActivity : AppCompatActivity() {

    private val preferences = makeInstance(Preferences::class, arrayOf(PREFERENCE_NAME)) as Preferences

    private val tx = object : Tx<String, String, String>() {
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
                LogUtil.printDebug(t!!)
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
        LogUtil.addLogAdapter(CommonLogAdapter())

        preferences.save(ArrayMap<String, Any>().apply {
            put("somekey", "key0")
            put("somekey1", "key1")
            put("somekey2", "key2")
            put("somekey3", "key3")
            put("somekey4", "key4")
            put("somekey5", "key5")
        })
        preferences.save("somekey6", SomeType().apply { name= "key6" })
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        /*Promise(object: CallbackWithResolver<Array<String>, Array<String>> {
            override fun call(arg: Array<String>, resolver: Resolver<Array<String>>) {
                resolver.resolve(arrayOf("somekey", "somekey1",
                    "somekey2", "somekey3",
                    "somekey4", "somekey5"), null)
            }
        }).then(object : VoidReturnCallback<Array<String>> {
            override fun call(arg: Array<String>) {
                title_textview.text = "Started reading"
                TxManager.instance().execute(tx.complete {
                    val text = it.reverse().toString()
                    val newText = text to
                        preferences.getObject("somekey6", SomeType::class.java)
                    preferences_textview.text = newText.toString()
                }, Pair(arg, 1000))
            }
        })*/

        PromiseCallback<Array<String>> { resolve, _ ->
                resolve(arrayOf("somekey", "somekey1",
                    "somekey2", "somekey3",
                    "somekey4", "somekey5"))
            }
        .then { array ->
            title_textview.text = "Started reading"
            TxManager.instance().execute(tx.complete {
                val text = it.reverse().toString()
                val newText = text to
                    preferences.getObject("somekey6", SomeType::class.java)
                preferences_textview.text = newText.toString()
            }, Pair(array, 1000))
            null
        }
        .execute()
    }

    override fun finish() {
        preferences.clearAll()
        super.finish()
    }

    companion object {
        const val PREFERENCE_NAME = "pref_name"
    }
}
