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

package promise.commons.tx;

import promise.commons.InstanceProvider;

class TransactionManagerInstanceProvider implements InstanceProvider<TransactionManager> {

    private static TransactionManagerInstanceProvider instance;

    static void create() throws IllegalStateException {
        if (instance == null) instance = new TransactionManagerInstanceProvider();
        else throw new IllegalStateException("manager already initialized");
    }

    static TransactionManagerInstanceProvider instance() throws IllegalAccessException {
        if (instance == null) throw new IllegalAccessException("Initialize manager first");
        return instance;
    }

    @Override
    public TransactionManager get() {
        return new TransactionManager();
    }
}
