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
import promise.commons.data.log.CommonLogAdapter
import promise.commons.data.log.DiskLogAdapter
import promise.commons.data.log.LogUtil

class LoggingActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_logging)
    // for normal logging
    LogUtil.addLogAdapter(CommonLogAdapter())
    // for logging to file
    LogUtil.addLogAdapter(DiskLogAdapter())
    // in store, pushing logs to a server

    // all methods beginning with print use the adapters added
    LogUtil.d(TAG, "oncreate")
    LogUtil.printWTF("what the hell is being printd")

    // clear all the adapters
    LogUtil.clearLogAdapters()

  }

  companion object {
    val TAG = LogUtil.makeTag(LoggingActivity::class.java)
  }
}
