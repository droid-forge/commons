/*
 * Copyright 2017, Peter Vincent
 *  Licensed under the Apache License, Version 2.0, Android Promise.
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package promise.commons.model;

import promise.commons.AndroidPromise;
import promise.commons.data.log.LogUtil;

class UIResponse<T, E extends Throwable> implements Result.Response<T, E> {

  private static final String TAG = LogUtil.makeTag(UIResponse.class);

  private Result.Response<? super T, ? extends E> response;
  private Result.Error<? super E> error;

  UIResponse(Result.Response<? super T, ? extends E> response,
             Result.Error<? super E> error) {
    this.response = response;
    this.error = error;
  }

  @Override
  public void onResponse(T t) throws E {
    AndroidPromise.instance().executeOnUi(() -> {
      try {
        this.response.onResponse(t);
      } catch (Throwable e) {
        if (this.error != null) this.error.onError((E) e);
        else LogUtil.e(TAG,
            new IllegalStateException("Could not process error: " + e + " ," +
                " error not provided"));
      }
    });
  }
}
