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

import android.app.Application;

class ApplicationProvider implements InstanceProvider<Application> {

    private static ApplicationProvider instance;

    private final Application applicationInstanceProvider;

    private ApplicationProvider(Application applicationInstanceProvider) {
        this.applicationInstanceProvider = applicationInstanceProvider;
    }

    static ApplicationProvider create(Application applicationInstanceProvider) {
        if (instance == null) instance = new ApplicationProvider(applicationInstanceProvider);
        return instance;
    }

    static Application instance() {
        if (instance == null) throw new RuntimeException("Provider was not created");
        return instance.get();
    }

    @Override
    public Application get() {
        return applicationInstanceProvider;
    }

}
