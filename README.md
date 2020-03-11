# Android Promise Commons

[![Release](https://jitpack.io/v/android-promise/commons.svg)](https://jitpack.io/#android-promise/commons)

The base promise library

### Setup
- 
##### build.gradle
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

android {
    ...
    compileOptions {
            sourceCompatibility JavaVersion.VERSION_1_8
            targetCompatibility JavaVersion.VERSION_1_8
    }
    ...
}

dependencies {
     ...
     implementation 'io.reactivex.rxjava2:rxjava:2.2.12'
     implementation 'androidx.appcompat:appcompat:1.0.2'
     implementation 'com.github.android-promise:commons:TAG'
}

```

### Initialization
Initialize Promise in your main application file, entry point

##### App.java
```java
  @Override
  public void onCreate() {
    super.onCreate();
    Promise.init(this);
  }
```

### Table of Contents
**[Logging](###Logging)**<br>
**[Files](###Files)**<br>
**[Collections](###Collections)**<br>
**[Preferences](###Preferences)**<br>
**[Caching](###Caching)**<br>
**[Tasks](###Tasks)**<br>

### Logging



# Developed By
* Peter Vincent - <dev4vin@gmail.com>

### Pull requests / Issues / Improvement requests
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


