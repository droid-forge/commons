/*
 *
 *  * Copyright 2017, Peter Vincent
 *  * Licensed under the Apache License, Version 2.0, Promise.
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  * Unless required by applicable law or agreed to in writing,
 *  * software distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package promise.commons.model;

import promise.commons.data.log.LogUtil;

/**
 * result callback
 *
 * @param <T> result type
 * @param <E> error type
 */
public class Result<T, E extends Throwable> {
    private final String TAG = LogUtil.makeTag(Result.class);
    /**
     * response callback
     *
     * @see Response
     */
    private Response<? super T, ? extends E> response;
    /**
     * error callback
     *
     * @see Error
     */
    private Error<? super E> error;

    /**
     * register response callback
     *
     * @param response callback for response
     * @return result with response callback
     */
    public Result<T, E> withCallBack(Response<? super T, ? extends E> response) {
        this.response = response;
        return this;
    }

    public Result<T, E> withUICallback(Response<? super T, ? extends E> response,
                                       Error<? super E> error) {
        this.response = new UIResponse<T, E>(response, error);
        return this;
    }

    /**
     * register error callback
     *
     * @param error callback for error
     * @return result with error callback
     */
    public Result<T, E> withErrorCallBack(Error<? super E> error) {
        this.error = error;
        return this;
    }

    /**
     * return the response back to the caller via the response callback
     *
     * @param t   response
     * @param <R> type of response
     */
    public <R extends T> void response(R t) {
        if (response != null) {
            try {
                response.onResponse(t);
            } catch (Throwable e) {
                LogUtil.e(TAG, e);
                error((E) e);
            }
        } else LogUtil.e(TAG,
                new IllegalStateException("Could not pass data: " + t +
                    " , response not provided"));
    }

    /**
     * return the error back to the caller
     *
     * @param e   error response
     * @param <R> type of error response
     */
    public <R extends E> void error(R e) {
        if (error != null) error.onError(e);
        else LogUtil.e(TAG,
                new IllegalStateException("Could not process error: " + e +
                    " , error not provided"));
    }

    /**
     * response callback
     *
     * @param <T> type of response
     * @param <E> type of error
     */
    public interface Response<T, E extends Throwable> {
        void onResponse(T t) throws E;
    }

    /**
     * error callback
     *
     * @param <E> type of error
     */
    public interface Error<E> {
        void onError(E e);
    }
}
