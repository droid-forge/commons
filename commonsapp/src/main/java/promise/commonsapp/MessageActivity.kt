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
