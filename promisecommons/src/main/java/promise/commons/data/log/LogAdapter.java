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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Provides a common interface to emits logs through. This is a required contract for Logger.
 *
 * @see CommonLogAdapter
 * @see DiskLogAdapter
 */
public interface LogAdapter {

  /**
   * Used to determine whether log should be printed out or not.
   *
   * @param priority is the log level e.g. DEBUG, WARNING
   * @param tag      is the given tag for the log message
   * @return is used to determine if log should printed.
   * If it is true, it will be printed, otherwise it'll be ignored.
   */
  boolean isLoggable(int priority, @Nullable String tag);

  /**
   * Each log will use this pipeline
   *
   * @param priority is the log level e.g. DEBUG, WARNING
   * @param tag      is the given tag for the log message.
   * @param message  is the given message for the log message.
   */
  void log(int priority, @Nullable String tag, @NonNull String message);
}