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

package promise.commons.data.log;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import promise.commons.AndroidPromise;

import static promise.commons.data.log.LogAdapter.ASSERT;
import static promise.commons.data.log.LogAdapter.DEBUG;
import static promise.commons.data.log.LogAdapter.ERROR;
import static promise.commons.data.log.LogAdapter.INFO;
import static promise.commons.data.log.LogAdapter.VERBOSE;
import static promise.commons.data.log.LogAdapter.WARN;
import static promise.commons.util.Conditions.checkNotNull;

public class LogUtil {

  protected static final AndroidPromise promise = AndroidPromise.instance();
  private static final String LOG_PREFIX = "_";
  private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
  private static final int MAX_LOG_TAG_LENGTH = 20;
  @NonNull
  private static Printer printer = new LoggerPrinter();

  private LogUtil() {
    //no instance
  }

  public static String makeTag(String str) {
    if (str.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH)
      return LOG_PREFIX + str.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 1);
    return LOG_PREFIX + str;
  }

  public static String makeTag(Class cls) {
    return makeTag(cls.getSimpleName());
  }

  public static void i(String tag,
                       Object... messages) {
    log(tag, Log.INFO, null, messages);
  }

  public static void d(String tag,
                       Object... messages) {
    log(tag, Log.DEBUG, null, messages);
  }

  public static void w(String tag,
                       Object... messages) {
    log(tag, Log.WARN, null, messages);
  }

  public static void w(String tag,
                       Throwable t,
                       Object... messages) {
    log(tag, Log.WARN, t, messages);
  }

  public static void e(String tag,
                       Object... messages) {
    log(tag, Log.ERROR, null, messages);
  }

  public static void e(String tag,
                       Throwable t,
                       Object... messages) {
    log(tag, Log.ERROR, t, messages);
  }

    private static void log(String tag,
                            int level,
                            Throwable t,
                            Object... messages) {
        if (Log.isLoggable(tag, level)) {
            String message;
            if (t == null && messages != null && messages.length == 1)
                message = messages[0].toString();
            else {
                StringBuilder sb = new StringBuilder();
                if (messages != null) for (Object m : messages) sb.append(m);
                if (t != null) sb.append("\n").append(Log.getStackTraceString(t));
                message = sb.toString();
            }
            android.util.Log.println(level, tag, message);
        }
    }

  static String logLevel(int value) {
    switch (value) {
      case VERBOSE:
        return "VERBOSE";
      case DEBUG:
        return "DEBUG";
      case INFO:
        return "INFO";
      case WARN:
        return "WARN";
      case ERROR:
        return "ERROR";
      case ASSERT:
        return "ASSERT";
      default:
        return "UNKNOWN";
    }
  }

     public static void printer(@NonNull Printer printer) {
        printer = checkNotNull(printer);
    }

  public static void addLogAdapter(@NonNull LogAdapter adapter) {
    printer.addAdapter(checkNotNull(adapter));
  }

  public static void clearLogAdapters() {
    printer.clearLogAdapters();
  }

  /**
   * Given tag will be used as tag only once for this method call regardless of the tag that's been
   * set during initialization. After this invocation, the general tag that's been set will
   * be used for the subsequent log calls
   */
  public static Printer t(@Nullable String tag) {
    return printer.t(tag);
  }

    /**
     * General log function that accepts all configurations as parameter
     */
    public static void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        printer.log(priority, tag, message, throwable);
    }

    public static void printDebug(@NonNull String message, @Nullable Object... args) {
        printer.d(message, args);
    }

    public static void printDebugd(@Nullable Object object) {
        printer.d(object);
    }

    public static void printError(@NonNull String message, @Nullable Object... args) {
        printer.e(null, message, args);
    }

    public static void printError(@Nullable Throwable throwable, @NonNull String message, @Nullable Object... args) {
        printer.e(throwable, message, args);
    }

    public static void printInfo(@NonNull String message, @Nullable Object... args) {
        printer.i(message, args);
    }

    public static void printVerbose(@NonNull String message, @Nullable Object... args) {
        printer.v(message, args);
    }

    public static void printWarning(@NonNull String message, @Nullable Object... args) {
        printer.w(message, args);
    }

    /**
     * Tip: Use this for exceptional situations to log
     * ie: Unexpected errors etc
     */
    public static void printWTF(@NonNull String message, @Nullable Object... args) {
        printer.wtf(message, args);
    }

    /**
     * Formats the given json content and print it
     */
    public static void printJson(@Nullable String json) {
        printer.json(json);
    }

    /**
     * Formats the given xml content and print it
     */
    public static void printXml(@Nullable String xml) {
        printer.xml(xml);
    }
}
