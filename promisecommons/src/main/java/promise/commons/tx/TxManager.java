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
import androidx.core.util.Pair;

import java.util.LinkedList;

import promise.commons.SingletonInstanceProvider;
import promise.commons.model.List;

/**
 * manages all execution of tx instances
 */
public class TxManager {
  /**
   * pool of all {@link Tx instances}
   */
  private LinkedList<Pair<Tx, Pair<Object[], Long>>> queue;
  /**
   * flag for when manger is executing tx instances
   */
  private boolean running = false;
  /**
   * gets the current running tx manager
   * {@link SingletonInstanceProvider for more info on singletons}
   *
   * @return tx manager
   */
  public static TxManager instance() {
    try {
      return SingletonInstanceProvider.provider(TxManagerProvider.instance()).get();
    } catch (IllegalAccessException e) {
      TxManagerProvider.create();
      return instance();
    }
  }

  /**
   * initializes the queue pool
   */
  TxManager() {
    queue = new LinkedList<>();
  }

  /**
   * schedules a {@link Tx instance} on the pool and executes it
   * @param pairs pairs of transactions with their arguments
   */
  @SafeVarargs
  public final void executeTasks(Pair<Tx, Pair<Object[], Long>>... pairs) {
    List<? extends Pair<Tx, Pair<Object[], Long>>> pairs1 = List.fromArray(pairs);
    if (pairs1.anyMatch(pair -> pair.first instanceof TimedTx))
      throw new RuntimeException("TxManager doesn't support execution of timed operations yet");
    if (queue == null) return;
    queue.addAll(pairs1);
    if (running) return;
    cycle();
  }

  public final void execute(Tx tx, Pair<Object[], Long> pair) {
    if (tx instanceof TimedTx) throw new RuntimeException("TxManager doesn't support execution of timed operations yet");
    if (queue == null) return;
    queue.add(new Pair<>(tx, pair));
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
    Tx tx = queue.peekFirst().first;
    if (args == null) {
      tx.execute(null);
    } else if (args.second != null) tx.execute(args.first, args.second);
    else {
      tx.execute(args.first);
    }
  }
}
