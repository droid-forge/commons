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

package promise.commons;

class AndroidPromiseProvider implements InstanceProvider<AndroidPromise> {

    private static AndroidPromiseProvider instance;

    private final ApplicationProvider applicationInstanceProvider;

    private int numOfThreads = 1;

    private AndroidPromiseProvider(ApplicationProvider applicationInstanceProvider) {
        this.applicationInstanceProvider = applicationInstanceProvider;
    }

    private AndroidPromiseProvider(ApplicationProvider applicationInstanceProvider, int numOfThreads) {
        this.applicationInstanceProvider = applicationInstanceProvider;
        this.numOfThreads = numOfThreads;
    }

    static AndroidPromiseProvider create(ApplicationProvider applicationInstanceProvider) {
        if (instance == null) instance = new AndroidPromiseProvider(applicationInstanceProvider);
        return instance;
    }

    static AndroidPromiseProvider create(ApplicationProvider applicationInstanceProvider, int numOfThreads) {
        if (instance == null)
            instance = new AndroidPromiseProvider(applicationInstanceProvider, numOfThreads);
        return instance;
    }

    static AndroidPromiseProvider instance() throws IllegalAccessException {
        if (instance == null) throw new IllegalAccessException("Initialize promise first");
        return instance;
    }

    @Override
    public AndroidPromise get() {
        AndroidPromise instance = new AndroidPromise(applicationInstanceProvider.get());
        if (numOfThreads > 1) return instance.threads(numOfThreads);
        return instance;
    }
}
