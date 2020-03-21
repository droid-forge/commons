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

import androidx.core.util.Pair;

import java.util.LinkedList;

import promise.commons.SingletonInstanceProvider;
import promise.commons.model.List;

/**
 * manages all execution of tx instances
 */
public class TransactionManager {
    /**
     * pool of all {@link Transaction instances}
     */
    private LinkedList<Pair<Transaction, Pair<Object[], Long>>> queue;
    /**
     * flag for when manger is executing tx instances
     */
    private boolean running = false;

    /**
     * initializes the queue pool
     */
    TransactionManager() {
        queue = new LinkedList<>();
    }

    /**
     * gets the current running tx manager
     * {@link SingletonInstanceProvider for more info on singletons}
     *
     * @return tx manager
     */
    public static TransactionManager instance() {
        try {
            return SingletonInstanceProvider.provider(TransactionManagerInstanceProvider.instance()).get();
        } catch (IllegalAccessException e) {
            TransactionManagerInstanceProvider.create();
            return instance();
        }
    }

    /**
     * schedules a {@link Transaction instance} on the pool and executes it
     *
     * @param pairs pairs of transactions with their arguments
     */
    @SafeVarargs
    public final void executeTasks(Pair<Transaction, Pair<Object[], Long>>... pairs) {
        List<? extends Pair<Transaction, Pair<Object[], Long>>> pairs1 = List.fromArray(pairs);
        if (pairs1.anyMatch(pair -> pair.first instanceof TimedTransaction))
            throw new RuntimeException("TxManager doesn't support execution of timed operations yet");
        if (queue == null) return;
        queue.addAll(pairs1);
        if (running) return;
        cycle();
    }

    public final void execute(Transaction transaction, Pair<Object[], Long> pair) {
        if (transaction instanceof TimedTransaction)
            throw new RuntimeException("TxManager doesn't support execution of timed operations yet");
        if (queue == null) return;
        queue.add(new Pair<>(transaction, pair));
        if (running) return;
        cycle();
    }

    /**
     * cycle through the pool and execute any pending tx instances
     */
    private void cycle() {
        if (queue.isEmpty()) {
            running = false;
            return;
        }
        running = true;
        queue.peekFirst().first.complete(o -> {
            queue.removeFirst();
            if (!queue.isEmpty()) cycle();
        });
        Pair<Object[], Long> args = queue.peekFirst().second;
        Transaction transaction = queue.peekFirst().first;
        if (args == null) transaction.execute(null);
        else if (args.second != null) transaction.execute(args.first, args.second);
        else transaction.execute(args.first);
    }
}
