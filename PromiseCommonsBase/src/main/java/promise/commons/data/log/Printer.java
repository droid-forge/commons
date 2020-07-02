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


import org.jetbrains.annotations.Nullable;

/**
 * A proxy interface to enable additional operations.
 * Contains all possible Log message usages.
 */
public interface Printer {

  void addAdapter(LogAdapter adapter);

  Printer t(@Nullable String tag);

  void d(String message, @Nullable Object... args);

  void d(@Nullable Object object);

  void e(String message, @Nullable Object... args);

  void e(@Nullable Throwable throwable, String message, @Nullable Object... args);

  void w(String message, @Nullable Object... args);

  void i(String message, @Nullable Object... args);

  void v(String message, @Nullable Object... args);

  void wtf(String message, @Nullable Object... args);

  /**
   * Formats the given json content and print it
   */
  void json(@Nullable String json);

  /**
   * Formats the given xml content and print it
   */
  void xml(@Nullable String xml);

  void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable);

  void clearLogAdapters();
}