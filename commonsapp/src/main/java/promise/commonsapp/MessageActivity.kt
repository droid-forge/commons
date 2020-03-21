package promise.commonsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import promise.commons.AndroidPromise
import promise.commons.model.Message
import promise.commons.tx.PromiseResult

class MessageActivity : AppCompatActivity() {

   var notificationListenId: Int = 0
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_message)

    notificationListenId = AndroidPromise.instance().listen("sender", PromiseResult<Any, Throwable>()
        .withCallback {
          // use the message
        })
    initiateListen()
  }

  override fun finish() {
    AndroidPromise.instance().stopListening(notificationListenId)
    super.finish()
  }

  private fun initiateListen() {
    // call from anywhere in the app
    AndroidPromise.instance().send(Message("sender", "message here"))
  }
}
