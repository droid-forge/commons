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

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.annotation.Nullable;

import promise.commons.AndroidPromise;
import promise.commons.model.List;

/**
 * Execution of synchronous functions
 *
 * @param <RETURN>   return type of execution
 * @param <PROGRESS> progress of the execution
 * @param <ARGUMENT> argument for each execution
 */
public abstract class Transaction<RETURN, PROGRESS, ARGUMENT> implements Future {
    /**
     * action executor
     * {@link Task}
     */
    private Task task;
    /**
     * wait millis for each execution
     */
    private long millis = 0;
    /**
     * flag for when execution is complete
     */
    private boolean completed = false;
    /**
     * execution callback function
     * {@link CallBackExecutor}
     */

    private CallBackExecutor<? extends RETURN, ? super ARGUMENT> callBackExecutor;
    /**
     * progress notifier
     * {@link Progress}
     */
    private Progress<? super RETURN, PROGRESS> progress;
    /**
     * completion notifier
     * {@link Complete}
     */
    private List<Complete<RETURN>> complete;

    /**
     * initializes the callback, progress and complete listeners
     */
    public Transaction() {
        callBackExecutor = getCallBackExecutor();
        progress = getProgress();
        complete = new List<>();
    }

    /**
     * executes with the given arguments
     *
     * @param params given arguments
     */
    public void execute(@Nullable ARGUMENT[] params) {
        try {
            checkCallBacks();
            if (task != null) return;
            task = new Task();
            if (params != null) task.executeOnExecutor(AndroidPromise.instance().executor(), params);
            else task.executeOnExecutor(AndroidPromise.instance().executor(), (ARGUMENT) null);
        } catch (NoCallBacksError error) {
            error.show();
        }
    }

    /**
     * executes the given arguments and for each execution given wait time in between
     *
     * @param params execution arguments
     * @param millis wait time interval between each execution
     */
    public void execute(@Nullable ARGUMENT[] params, long millis) {
        this.millis = millis;
        execute(params);
    }

    /**
     * cancels the execution of the task
     */
    @Override
    public void cancel() {
        if (task != null) {
            task.cancel(true);
            task = null;
        }
    }

    /**
     * checks if the task is complete
     *
     * @return if the task is null
     */
    @Override
    public boolean isCancelled() {
        return task == null;
    }

    /**
     * checks if the task is done
     *
     * @return if the task has completed execution
     */
    @Override
    public boolean isDone() {
        return completed;
    }

    /**
     * if there's more than one params to execute on
     * this provided a callback object to notify on progress of
     * each consecutive result
     *
     * @return the progress callback
     */
    public abstract Progress<? super RETURN, PROGRESS> getProgress();

    /**
     * checks for complete callbacks
     *
     * @throws NoCallBacksError if no callbacks are defines
     */
    private void checkCallBacks() throws NoCallBacksError {
        if (callBackExecutor == null) throw new NoCallBacksError();
    }

    /**
     * finalises the execution of the task
     *
     * @param RETURN the list of all the results
     */
    private void finalize(List<RETURN> RETURN) {
        if (complete != null && !complete.isEmpty())
            for (Complete<RETURN> complete1 : complete) complete1.onComplete(RETURN);
    }

    /**
     * gets the callback methods used for executing the transaction
     *
     * @return a callbacks object
     */
    public abstract CallBackExecutor<? extends RETURN, ? super ARGUMENT> getCallBackExecutor();

    /**
     * registers completion callback
     *
     * @param complete callback when task completes
     *                 {@link Complete}   *
     * @return an executioner with the callback
     */
    public Transaction complete(Complete<RETURN> complete) {
        if (this.complete == null)
            this.complete = new List<>();
        this.complete.add(complete);
        return this;
    }

    /**
     * execution callback
     *
     * @param <RETURN>   return type of execution
     * @param <ARGUMENT> argument type passed to execution step
     */
    public interface CallBackExecutor<RETURN, ARGUMENT> {
        /**
         * This is called in a background thread
         *
         * @param args arg passed for execution
         * @return the result of execution of args
         */
        RETURN onExecute(ARGUMENT args);

    }

    /**
     * @param <RETURN>
     */
    public interface Complete<RETURN> {
        /**
         * returns the result of execution
         *
         * @param t returned result
         */
        void onComplete(List<RETURN> t);
    }

    /**
     * @param <RETURN>
     * @param <PROGRESS>
     */
    public interface Progress<RETURN, PROGRESS> {
        /**
         * calculates the progress value for the current result
         * in a background thread
         *
         * @param t current result
         * @return a progress of the result
         */
        PROGRESS onCalculateProgress(RETURN t);

        /**
         * returns the progress of the current result on the main thread
         *
         * @param x current executed progress {@link #onCalculateProgress(Object)}
         */
        void onProgress(PROGRESS x);
    }

    /**
     * main task executioner
     * {@link AsyncTask}
     */
    @SuppressLint("StaticFieldLeak")
    protected class Task extends AsyncTask<ARGUMENT, PROGRESS, List<RETURN>> {
        /**
         * executes the given arguments to give back a list of results
         *
         * @param params args to execute
         * @return a list of results
         */
        @SafeVarargs
        @Override
        protected final List<RETURN> doInBackground(ARGUMENT... params) {
            if (params != null && params.length > 0) {
                List<RETURN> returns = new List<>();
                for (ARGUMENT param : params) {
                    if (millis > 0) try {
                        Thread.sleep(millis);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    RETURN val = callBackExecutor.onExecute(param);
                    if (progress != null) publishProgress(progress.onCalculateProgress(val));
                    returns.add(val);
                }
                completed = true;
                return returns;
            } else {
                List<RETURN> returns = new List<>();
                RETURN val = callBackExecutor.onExecute(null);
                if (progress != null) publishProgress(progress.onCalculateProgress(val));
                returns.add(val);
                completed = true;
                return returns;
            }
        }

        /**
         * pass progress for each executed result
         *
         * @param values progress of execution
         */
        @SafeVarargs
        @Override
        protected final void onProgressUpdate(PROGRESS... values) {
            super.onProgressUpdate(values);
            progress.onProgress(values[0]);
        }

        /**
         * notifies completion of task
         *
         * @param RETURNS list of results
         */
        @Override
        protected void onPostExecute(List<RETURN> RETURNS) {
            super.onPostExecute(RETURNS);
            Transaction.this.finalize(RETURNS);
        }
    }
}
