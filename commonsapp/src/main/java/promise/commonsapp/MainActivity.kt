package promise.commonsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.ArrayMap
import androidx.core.util.Pair
import kotlinx.android.synthetic.main.activity_main.*
import promise.commons.InstanceProvider
import promise.commons.SingletonInstanceProvider
import promise.commons.createInstance
import promise.commons.data.log.LogUtil
import promise.commons.pref.Preferences
import promise.commons.tx.Tx
import promise.commons.tx.Tx.CallBackExecutor
import promise.commons.tx.TxManager

class MainActivity : AppCompatActivity() {

  private val preferences = SingletonInstanceProvider.provider(object : InstanceProvider<Preferences> {
    /**
     * Provides a fully-constructed and injected instance of `T`.
     */
    override fun get(): Preferences = createInstance(arrayOf(PREFERENCE_NAME))
  }).get()

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
    val prefNames = arrayOf("somekey", "somekey1", "somekey2", "somekey3", "somekey4", "somekey5")
    val tx = object : Tx<String, String, String>() {
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
        override fun onCalculateProgress(t: String?): String = t!!

        /**
         * returns the progress of the current result
         * @param x current executed progress [.onCalculateProgress]
         */
        override fun onProgress(x: String?) {
          progress_textview.text = x
        }
      }
    }

    title_textview.text = "Started reading"
    TxManager.instance().execute(tx.complete {
      preferences_textview.text = it.reverse().toString()
    }, Pair(prefNames, 1000))
  }

  override fun finish() {
    preferences.clearAll()
    super.finish()
  }

  companion object {
    val TAG = LogUtil.makeTag(MainActivity::class.java)
    const val PREFERENCE_NAME = "pref_name"
  }
}
