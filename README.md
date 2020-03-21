# Android Promise Commons

[![Release](https://jitpack.io/v/android-promise/commons.svg)](https://jitpack.io/#android-promise/commons)

The base promise library


### Table of Contents
**[Setup](##Setup)**<br>
**[Initialization](##Initialization)**<br>
**[Logging](##Logging)**<br>
**[Promises and Callbacks](##Promises-and-Callbacks)**<br>
**[Preferences](##Preferences)**<br>
**[Transactions](##Transactions)**<br>
**[Message and Message Bus](##Message-and-Message-Bus)**<br>
**[Next Steps, Credits, Feedback, License](#next-steps)**<br>

## Setup
- 
#### build.gradle
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

android {
    compileOptions {
            sourceCompatibility JavaVersion.VERSION_1_8
            targetCompatibility JavaVersion.VERSION_1_8
        }
}

dependencies {
     implementation 'io.reactivex.rxjava2:rxjava:2.2.12'
     implementation 'androidx.appcompat:appcompat:1.0.2'
     implementation 'com.github.android-promise:commons:TAG'
}

```
# Initialization
Initialize Promise in your main application file, entry point

#### App.java
```java
public class App extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    // 10 is the number of threads allowed to run in the background
    AndroidPromise.init(this, 10, BuildConfig.DEBUG);
  }

  @Override
  public void onTerminate() {
    super.onTerminate();
    AndroidPromise.instance().terminate();
  }
}

```

## Logging

```kotlin
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
```

## Promises and Callbacks
This callback helps achieve promise like javascript functionality

```kotlin
object FakeRepository {
	// declare a method that returns a promise callback
  fun getItems(): PromiseCallback<List<Any>> = PromiseCallback { resolve, _ ->
    resolve(promise.commons.model.List.generate(3) { Any() })
  }

  // declare a method that takes in a callback
  fun getItems(result: PromiseResult<List<Any>, Throwable>) {
    result.response(promise.commons.model.List.generate(3) { Any() })
  }

  // always executes on a background thread
  fun getItems2(): Promise<List<Any>> = Promise.resolve(promise.commons.model.List.generate(3) { Any() })


  fun getItems3(): Promise<List<Any>> = Promise(object: CallbackWithResolver<Any, List<Any>> {
    override fun call(arg: Any, resolver: Resolver<List<Any>>) {
      resolver.resolve(promise.commons.model.List.generate(3) { Any() }, null)
    }
  })
}

class UsingCallbacksActivity : AppCompatActivity() {

  ...

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)

    // get data from the promise
    // this will execute on this thread by default 
    FakeRepository.getItems().then {
      /// log items
      it
    }.execute()

    // log items on withCallback called
    FakeRepository.getItems(PromiseResult<List<Any>, Throwable> ()
        .withCallback {
          /// log items
    })
    // you could chain multiple then callbacks
    FakeRepository.getItems2().then(object: Callback2<List<Any>, Unit> {
      override fun call(arg: List<Any>) {
        /// log items
      }
    })

    // join promises together
    Promise.all<Unit, List<Any>>(FakeRepository.getItems2(), FakeRepository.getItems3())
        .then(object: Callback2<List<List<Any>>, Unit> {
          override fun call(arg: List<List<Any>>) {
            /// log items, collected together
        }
    })
  }

  ...
}
```

## Preferences

```kotlin

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
```

## Transactions

```kotlin
class TransactionsActivity : AppCompatActivity() {

  private val transaction = object : Transaction<String, String, String>() {
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
            args != null -> args
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
        // calculate progress value based on current result
        return t!!
      }

      /**
       * returns the progress of the current result
       * @param x current executed progress [.onCalculateProgress]
       */
      override fun onProgress(x: String?) {
        // publish the calculated progress
        progress_textview.text = x
      }
    }
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
      // assume the background work needs two args to do their task
    TransactionManager.instance().execute(transaction.complete {
      preferences_textview.text = it.reverse().toString()
    }, Pair(arrayOf("arg1", "arg2"), 1000))
  }

}
```

## Messaging and Message Bus
```kotlin
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
  	// unsubscribe listening
    AndroidPromise.instance().stopListening(notificationListenId)
    super.finish()
  }

  private fun initiateListen() {
    // call from anywhere in the app
    AndroidPromise.instance().send(Message("sender", "message here"))
  }
}
```

## New features on the way
watch this repo to stay updated

# Developed By
* Peter Vincent - <dev4vin@gmail.com>
# Donations
If you'd like to support this library development, you could buy me coffee here:
* [![Become a Patreon]("https://c6.patreon.com/becomePatronButton.bundle.js")](https://www.patreon.com/bePatron?u=31932751)

Thank you very much in advance!

#### Pull requests / Issues / Improvement requests
Feel free to contribute and ask!<br/>

# License

    Copyright 2018 Peter Vincent

    Licensed under the Apache License, Version 2.0 Android Promise;
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


