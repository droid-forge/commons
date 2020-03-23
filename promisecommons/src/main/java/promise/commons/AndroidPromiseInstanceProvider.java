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

package promise.commons;

class AndroidPromiseInstanceProvider implements InstanceProvider<AndroidPromise> {

    private static AndroidPromiseInstanceProvider instance;

    private final ApplicationInstanceProvider applicationInstanceProvider;

    private int numOfThreads = 1;

    private boolean enableDebug = false;


    private AndroidPromiseInstanceProvider(ApplicationInstanceProvider applicationInstanceProvider, boolean enableDebug) {
        this.applicationInstanceProvider = applicationInstanceProvider;
        this.enableDebug = enableDebug;
    }

    private AndroidPromiseInstanceProvider(ApplicationInstanceProvider applicationInstanceProvider, int numOfThreads, boolean enableDebug) {
        this.applicationInstanceProvider = applicationInstanceProvider;
        this.numOfThreads = numOfThreads;
        this.enableDebug = enableDebug;
    }

    static AndroidPromiseInstanceProvider create(ApplicationInstanceProvider applicationInstanceProvider, boolean enableDebug) {
        if (instance == null) instance = new AndroidPromiseInstanceProvider(applicationInstanceProvider, enableDebug);
        return instance;
    }

    static AndroidPromiseInstanceProvider create(ApplicationInstanceProvider applicationInstanceProvider, int numOfThreads, boolean enableDebug) {
        if (instance == null)
            instance = new AndroidPromiseInstanceProvider(applicationInstanceProvider, numOfThreads, enableDebug);
        return instance;
    }

    static AndroidPromiseInstanceProvider instance() throws IllegalAccessException {
        if (instance == null) throw new IllegalAccessException("Initialize promise first");
        return instance;
    }

    @Override
    public AndroidPromise get() {
        AndroidPromise instance = new AndroidPromise(applicationInstanceProvider.get());
        instance.enableDebug = enableDebug;
        if (numOfThreads > 1) return instance.threads(numOfThreads);
        return instance;
    }
}
