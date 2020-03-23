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
