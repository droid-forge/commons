package promise.commonsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import promise.commons.pref.Preferences

class CustomType {
  var arg: Any? = null
}

class PreferenceActivity : AppCompatActivity() {

  lateinit var preferences: Preferences

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_preference)
    preferences = Preferences()
    // normal save ops
    preferences.save("key","value")
    preferences.save("key1",45)
    preferences.save("customType", CustomType().apply { arg = 67 })

    // retrieve values
    val value = preferences.getString("key")
    val fortyFive = preferences.getInt("key1")
    val customType = preferences.getObject("customType", CustomType::class.java)

  }
}
