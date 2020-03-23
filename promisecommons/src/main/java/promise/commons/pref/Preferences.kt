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
package promise.commons.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import promise.commons.AndroidPromise
import promise.commons.Utils
import promise.commons.data.log.LogUtil
import kotlin.collections.ArrayList

/**
 *
 */
class Preferences {
  /**
   *
   */
  private var preferences: SharedPreferences

  /**
   *
   */
  constructor() {
    preferences = PreferenceManager
        .getDefaultSharedPreferences(AndroidPromise.instance().context())
  }

  /**
   * @param name
   */
  constructor(name: String) {
    preferences = AndroidPromise.instance().context().getSharedPreferences(name, Context.MODE_PRIVATE)
  }

  /**
   * @param preferenceChange
   * @return
   */
  fun preferenceChange(preferenceChange: PreferenceChange): Preferences {
    preferences.registerOnSharedPreferenceChangeListener { sharedPreferences: SharedPreferences?, key: String? ->
      preferenceChange.onChange(sharedPreferences, key)
    }
    return this
  }

  /**
   * @return
   */
  val all: Map<String, *>
    get() = preferences.all

  fun select(vararg keys: String): List<Any?> {
    val items = ArrayList<Any?>()
    keys.forEach {
      val item = getPreference(Pref(it, Any()))
      items.add(item)
    }
    return items
  }

  @JvmOverloads
  fun getString(key: String, `val`: String = EMPTY_STRING): String = try {
    getPreference(Pref(key, `val`))!!
  } catch (invalidPrefType: InvalidPrefType) {
    LogUtil.e(TAG, Utils.getStackTraceString(invalidPrefType))
    `val`
  }

  @JvmOverloads
  fun getBoolean(key: String, `val`: Boolean = EMPTY_BOOLEAN): Boolean = try {
    getPreference(Pref(key, `val`))!!
  } catch (invalidPrefType: InvalidPrefType) {
    LogUtil.e(TAG, Utils.getStackTraceString(invalidPrefType))
    `val`
  }

  @JvmOverloads
  fun getLong(key: String, `val`: Long = EMPTY_LONG): Long = try {
    getPreference(Pref(key, `val`))!!
  } catch (invalidPrefType: InvalidPrefType) {
    LogUtil.e(TAG, Utils.getStackTraceString(invalidPrefType))
    `val`
  }

  @JvmOverloads
  fun getInt(key: String, `val`: Int = EMPTY_INT): Int = try {
    getPreference(Pref(key, `val`))!!
  } catch (invalidPrefType: InvalidPrefType) {
    LogUtil.e(TAG, Utils.getStackTraceString(invalidPrefType))
    `val`
  }

  @JvmOverloads
  fun getDouble(key: String, `val`: Double = EMPTY_DOUBLE): Double = try {
    getPreference(Pref(key, `val`))!!
  } catch (invalidPrefType: InvalidPrefType) {
    LogUtil.e(TAG, Utils.getStackTraceString(invalidPrefType))
    `val`
  }

  @JvmOverloads
  fun <T> getObject(key: String, type: Class<T>, `val`: T? = null): T? {
    val json = getString(key, EMPTY_STRING)
    return when {
      Utils.isEmpty(json) -> `val`
      else -> try {
        Gson().fromJson(json, type)
      } catch (e: JsonSyntaxException) {
        `val`
      }
    }
  }

  /**
   * @param key
   * @param param
   * @return
   */
  fun save(key: String, param: String): Boolean = try {
    savePreference(Pref(key, param))
    true
  } catch (invalidPref: InvalidPref) {
    LogUtil.e(TAG, Utils.getStackTraceString(invalidPref))
    false
  }

  /**
   * @param key
   * @param param
   * @return
   */
  fun save(key: String, param: Boolean): Boolean = try {
    savePreference( Pref(key, param))
    true
  } catch (invalidPref: InvalidPref) {
    LogUtil.e(TAG, invalidPref)
    false
  }

  /**
   * @param key
   * @param param
   * @return
   */
  fun save(key: String, param: Long): Boolean = try {
    savePreference(Pref(key, param))
    true
  } catch (invalidPref: InvalidPref) {
    LogUtil.e(TAG, invalidPref)
    false
  }

  /**
   * @param key
   * @param param
   * @return
   */
  fun save(key: String, param: Int): Boolean = try {
    savePreference(Pref(key, param))
    true
  } catch (invalidPref: InvalidPref) {
    invalidPref.printStackTrace()
    LogUtil.e(TAG, invalidPref)
    false
  }

  /**
   * @param key
   * @param param
   * @return
   */
  fun save(key: String, param: Double): Boolean = try {
    savePreference(Pref(key, param))
    true
  } catch (invalidPref: InvalidPref) {
    invalidPref.printStackTrace()
    LogUtil.e(TAG, invalidPref)
    false
  }

  fun save(key: String, param: Any): Boolean {
    val json = Gson()
    return save(key, json.toJson(param))
  }

  /**
   * @param params
   * @return
   */
  fun save(params: Map<String, Any>): Boolean {
    val prefs: MutableList<Pref<Any>> = ArrayList()
    for ((key, value) in params) prefs.add(Pref(key, value))
    return try {
      savePreference(*prefs.toTypedArray())
      true
    } catch (invalidPref: InvalidPref) {
      LogUtil.e(TAG, invalidPref)
      false
    }
  }

  /**
   * @param pref
   * @throws InvalidPref
   */
  @Throws(InvalidPref::class)
  private fun savePreference(pref: Pref<*>) {
    val editor = preferences.edit()
    when {
      pref.get() is String -> editor.putString(pref.name, pref.get() as String?)
      pref.get() is Int -> editor.putInt(pref.name, (pref.get() as Int?)!!)
      pref.get() is Boolean -> editor.putBoolean(pref.name, (pref.get() as Boolean?)!!)
      pref.get() is Long -> editor.putLong(pref.name, (pref.get() as Long?)!!)
      pref.get() is Float -> editor.putFloat(pref.name, (pref.get() as Float?)!!)
      pref.get() is Any -> save(pref.name, pref.get() as Any)
      else -> throw InvalidPref(pref)
    }
    editor.apply()
  }

  /**
   * @param prefs
   * @throws InvalidPref
   */
  @Throws(InvalidPref::class)
  private fun savePreference(vararg prefs: Pref<*>) {
    val editor = preferences.edit()
    for (pref in prefs) {
      when {
        pref.get() is String -> editor.putString(pref.name, pref.get() as String?
        )
        pref.get() is Int -> editor.putInt(pref.name, (pref.get() as Int?)!!)
        pref.get() is Boolean -> editor.putBoolean(pref.name, (pref.get() as Boolean?)!!)
        pref.get() is Long -> editor.putLong(pref.name, (pref.get() as Long?)!!)
        pref.get() is Float -> editor.putFloat(pref.name, (pref.get() as Float?)!!)
        pref.get() is Any -> save(pref.name, pref.get() as Any)
        else -> throw InvalidPref(pref)
      }
    }
    editor.apply()
  }

  /**
   *
   */
  fun clearAll() {
    preferences.edit().clear().apply()
  }

  /**
   * @param key
   */
  fun clear(key: String) {
    preferences.edit().remove(key).apply()
  }

  /**
   * @param pref
   * @param <T>
   * @return
   * @throws InvalidPrefType
  </T> */
  @Throws(InvalidPrefType::class)
  private fun <T> getPreference(pref: Pref<T>): T? {
    val data: Any? = when {
      pref.get() is String -> preferences.getString(pref.name,
          pref.get() as String)
      pref.get() is Int -> preferences.getInt(pref.name,
          (pref.get() as Int))
      pref.get() is Boolean -> preferences.getBoolean(pref.name,
          (pref.get() as Boolean))
      pref.get() is Long -> preferences.getLong(pref.name,
          (pref.get() as Long))
      pref.get() is Float -> preferences.getFloat(pref.name,
          (pref.get() as Float))
      else -> throw InvalidPrefType(pref.get())
    }
    return data as T?
  }

  /**
   *
   */
  interface PreferenceChange {
    /**
     * @param preferences
     * @param key
     */
    fun onChange(preferences: SharedPreferences?, key: String?)
  }

  /**
   * @param <T>
  </T> */
  class Pref<T>
  /**
   * @param key
   * @param data
   */ internal constructor(
      /**
       *
       */
      var name: String,
      /**
       *
       */
      private var data: T) {

    fun get(): T = data

    fun set(data: T) {
      this.data = data
    }

  }

  /**
   *
   */
  class Builder {
    /**
     *
     */
    private var EMPTY_STRING = ""
    /**
     *
     */
    private var EMPTY_BOOLEAN = false
    /**
     *
     */
    private var EMPTY_LONG: Long = 0
    /**
     *
     */
    private var EMPTY_INT = 0
    /**
     *
     */
    private var EMPTY_DOUBLE = 0.0

    fun EMPTY_STRING(EMPTY_STRING: String): Builder {
      this.EMPTY_STRING = EMPTY_STRING
      return this
    }

    fun EMPTY_BOOLEAN(EMPTY_BOOLEAN: Boolean): Builder {
      this.EMPTY_BOOLEAN = EMPTY_BOOLEAN
      return this
    }

    fun EMPTY_LONG(EMPTY_LONG: Long): Builder {
      this.EMPTY_LONG = EMPTY_LONG
      return this
    }

    fun EMPTY_INT(EMPTY_INT: Int): Builder {
      this.EMPTY_INT = EMPTY_INT
      return this
    }

    fun EMPTY_DOUBLE(EMPTY_DOUBLE: Double): Builder {
      this.EMPTY_DOUBLE = EMPTY_DOUBLE
      return this
    }

    /**
     * @return
     */
    fun build(): Preferences {
      val preferences = Preferences()
      preferences.EMPTY_BOOLEAN = EMPTY_BOOLEAN
      preferences.EMPTY_STRING = EMPTY_STRING
      preferences.EMPTY_DOUBLE = EMPTY_DOUBLE
      preferences.EMPTY_INT = EMPTY_INT
      preferences.EMPTY_LONG = EMPTY_LONG
      return preferences
    }

    /**
     * @param name
     * @return
     */
    fun build(name: String): Preferences {
      val preferences = Preferences(name)
      preferences.EMPTY_BOOLEAN = EMPTY_BOOLEAN
      preferences.EMPTY_STRING = EMPTY_STRING
      preferences.EMPTY_DOUBLE = EMPTY_DOUBLE
      preferences.EMPTY_INT = EMPTY_INT
      preferences.EMPTY_LONG = EMPTY_LONG
      return preferences
    }
  }

  companion object {
    private val TAG = LogUtil.makeTag(Preferences::class.java)
  }

  /**
   *
   */
  private var EMPTY_STRING: String = ""
  /**
   *
   */
  private var EMPTY_BOOLEAN = false
  /**
   *
   */
  private var EMPTY_LONG: Long = 0
  /**
   *
   */
  private var EMPTY_INT = 0
  /**
   *
   */
  private var EMPTY_DOUBLE = 0.0
}