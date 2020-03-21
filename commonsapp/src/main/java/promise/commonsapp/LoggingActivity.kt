package promise.commonsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
