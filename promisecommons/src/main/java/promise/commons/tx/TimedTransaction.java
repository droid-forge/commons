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

package promise.commons.tx;

import androidx.annotation.Nullable;

import promise.commons.AndroidPromise;

/**
 * executes the given callback after wait millis
 * {@link Transaction}
 */
public abstract class TimedTransaction<RETURN, PROGRESS, ARGUMENT> extends Transaction<RETURN, PROGRESS, ARGUMENT> {
    /**
     * should not be called on this instance since the execution must be scheduled for the future
     *
     * @param params given arguments
     */
    @Override
    public void execute(@Nullable ARGUMENT[] params) {
        throw new RuntimeException("This method stub is not allowed on TimedTX");
    }

    /**
     * execution will always happen on a background thread no matter which thread invokes it
     *
     * @param params execution arguments
     * @param millis wait time interval between each execution
     */
    @Override
    public void execute(@Nullable ARGUMENT[] params, long millis) {
        if (millis < 1)
            throw new IllegalArgumentException("wait millis time must be more than zero");
        AndroidPromise.instance().execute(() -> {
            try {
                Thread.sleep(millis);
                super.execute(params);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
