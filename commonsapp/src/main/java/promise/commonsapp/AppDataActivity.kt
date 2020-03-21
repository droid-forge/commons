package promise.commonsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import promise.commons.file.Dir

class AppDataActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_app_data)
  }

  override fun finish() {
    Dir.clearAppData()
    super.finish()
  }
}
