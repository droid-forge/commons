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

import promise.commons.util.Conditions;

/**
 * Android terminal log output implementation for {@link LogAdapter}.
 * <p>
 * Prints output to LogCat with pretty borders.
 *
 * <pre>
 *  ┌──────────────────────────
 *  │ Method stack history
 *  ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
 *  │ Log message
 *  └──────────────────────────
 * </pre>
 */
public class CommonLogAdapter implements LogAdapter {

  @NonNull
  private final FormatStrategy formatStrategy;

  public CommonLogAdapter() {
    this.formatStrategy = PromiseFormatStrategy.newBuilder().build();
  }

  public CommonLogAdapter(@NonNull FormatStrategy formatStrategy) {
    this.formatStrategy = Conditions.checkNotNull(formatStrategy);
  }

  @Override
  public boolean isLoggable(int priority, @Nullable String tag) {
    return true;
  }

  @Override
  public void log(int priority, @Nullable String tag, @NonNull String message) {
    formatStrategy.log(priority, tag, message);
  }

}
