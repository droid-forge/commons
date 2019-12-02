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

package promise.commons.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import promise.commons.Promise;
import promise.commons.data.log.LogUtil;

/**
 *
 */
public class Preferences {
    private static final String TAG = LogUtil.makeTag(Preferences.class);
    /**
     *
     */
    private String EMPTY_STRING = "";
    /**
     *
     */
    private boolean EMPTY_BOOLEAN = false;
    /**
     *
     */
    private long EMPTY_LONG = 0;
    /**
     *
     */
    private int EMPTY_INT = 0;
    /**
     *
     */
    private double EMPTY_DOUBLE = 0;
    /**
     *
     */
    private SharedPreferences preferences;

    /**
     *
     */
    public Preferences() {
        preferences = PreferenceManager
                .getDefaultSharedPreferences(Promise.instance().context());
    }

    /**
     * @param name
     */
    public Preferences(String name) {
        preferences = Promise.instance().context().getSharedPreferences(name, Context.MODE_PRIVATE);

    }

    /**
     * @param preferenceChange
     * @return
     */
    public Preferences preferenceChange(final PreferenceChange preferenceChange) {
        preferences.registerOnSharedPreferenceChangeListener(
                (sharedPreferences, key) -> {
                    if (preferenceChange != null) {
                        preferenceChange.onChange(sharedPreferences, key);
                    }
                });
        return this;
    }

    /**
     * @return
     */
    public Map<String, ?> getAll() {
        return preferences.getAll();
    }

    /**
     * @param key
     * @return
     */
    public String getString(String key) {
        Pref<String> pref = new Pref<>(key, EMPTY_STRING);
        try {
            return getPreference(pref);
        } catch (InvalidPrefType invalidPrefType) {
            invalidPrefType.printStackTrace();
            LogUtil.e(TAG, invalidPrefType);
        }
        return EMPTY_STRING;
    }

    /**
     * @param key
     * @return
     */
    public boolean getBoolean(String key) {
        Pref<Boolean> pref = new Pref<>(key, EMPTY_BOOLEAN);
        try {
            return getPreference(pref);
        } catch (InvalidPrefType invalidPrefType) {
            invalidPrefType.printStackTrace();
            LogUtil.e(TAG, invalidPrefType);
        }
        return EMPTY_BOOLEAN;
    }

    /**
     * @param key
     * @return
     */
    public long getLong(String key) {
        Pref<Long> pref = new Pref<>(key, EMPTY_LONG);
        try {
            return getPreference(pref);
        } catch (InvalidPrefType invalidPrefType) {
            invalidPrefType.printStackTrace();
            LogUtil.e(TAG, invalidPrefType);
        }
        return EMPTY_LONG;
    }

    /**
     * @param key
     * @return
     */
    public int getInt(String key) {
        Pref<Integer> pref = new Pref<>(key, EMPTY_INT);
        try {
            return getPreference(pref);
        } catch (InvalidPrefType invalidPrefType) {
            invalidPrefType.printStackTrace();
            LogUtil.e(TAG, invalidPrefType);
        }
        return EMPTY_INT;
    }

    /**
     * @param key
     * @return
     */
    public double getDouble(String key) {
        Pref<Double> pref = new Pref<>(key, EMPTY_DOUBLE);
        try {
            return getPreference(pref);
        } catch (InvalidPrefType invalidPrefType) {
            invalidPrefType.printStackTrace();
            LogUtil.e(TAG, invalidPrefType);
        }
        return EMPTY_DOUBLE;
    }

    /**
     * @param key
     * @param param
     * @return
     */
    public boolean save(String key, String param) {
        if (TextUtils.isEmpty(param)) param = EMPTY_STRING;
        Pref<String> pref = new Pref<>(key, param);
        try {
            savePreference(pref);
            return true;
        } catch (InvalidPref invalidPref) {
            invalidPref.printStackTrace();
            LogUtil.e(TAG, invalidPref);
            return false;
        }
    }

    /**
     * @param key
     * @param param
     * @return
     */
    public boolean save(String key, boolean param) {
        Pref<Boolean> pref = new Pref<>(key, param);
        try {
            savePreference(pref);
            return true;
        } catch (InvalidPref invalidPref) {
            invalidPref.printStackTrace();
            LogUtil.e(TAG, invalidPref);
            return false;
        }
    }

    /**
     * @param key
     * @param param
     * @return
     */
    public boolean save(String key, long param) {
        Pref<Long> pref = new Pref<>(key, param);
        try {
            savePreference(pref);
            return true;
        } catch (InvalidPref invalidPref) {
            invalidPref.printStackTrace();
            LogUtil.e(TAG, invalidPref);
            return false;
        }
    }

    /**
     * @param key
     * @param param
     * @return
     */
    public boolean save(String key, int param) {
        Pref<Integer> pref = new Pref<>(key, param);
        try {
            savePreference(pref);
            return true;
        } catch (InvalidPref invalidPref) {
            invalidPref.printStackTrace();
            LogUtil.e(TAG, invalidPref);
            return false;
        }
    }

    /**
     * @param key
     * @param param
     * @return
     */
    public boolean save(String key, double param) {
        Pref<Double> pref = new Pref<>(key, param);
        try {
            savePreference(pref);
            return true;
        } catch (InvalidPref invalidPref) {
            invalidPref.printStackTrace();
            LogUtil.e(TAG, invalidPref);
            return false;
        }
    }

    /**
     * @param params
     * @return
     */
    public boolean save(Map<String, Object> params) {
        List<Pref<Object>> prefs = new ArrayList<>();
        for (Map.Entry<String, Object> entry : params.entrySet())
            prefs.add(new Pref<>(entry.getKey(), entry.getValue()));
        try {
            savePreference(prefs.toArray(new Pref[prefs.size()]));
            return true;
        } catch (InvalidPref invalidPref) {
            LogUtil.e(TAG, invalidPref);
            return false;
        }
    }

    /**
     * @param pref
     * @throws InvalidPref
     */
    private void savePreference(Pref pref) throws InvalidPref {
        SharedPreferences.Editor editor = preferences.edit();
        if (pref.get() instanceof String) editor.putString(pref.getName(), (String) pref.get());
        else if (pref.get() instanceof Integer) editor.putInt(pref.getName(), (Integer) pref.get());
        else if (pref.get() instanceof Boolean)
            editor.putBoolean(pref.getName(), (Boolean) pref.get());
        else if (pref.get() instanceof Long) editor.putLong(pref.getName(), (Long) pref.get());
        else if (pref.get() instanceof Float) editor.putFloat(pref.getName(), (Float) pref.get());
        else throw new InvalidPref(pref);
        editor.apply();
    }

    /**
     * @param prefs
     * @throws InvalidPref
     */
    private void savePreference(Pref... prefs) throws InvalidPref {
        SharedPreferences.Editor editor = preferences.edit();
        for (Pref pref : prefs) {
            if (pref.get() instanceof String) editor.putString(pref.getName(), (String) pref.get());
            else if (pref.get() instanceof Integer)
                editor.putInt(pref.getName(), (Integer) pref.get());
            else if (pref.get() instanceof Boolean)
                editor.putBoolean(pref.getName(), (Boolean) pref.get());
            else if (pref.get() instanceof Long) editor.putLong(pref.getName(), (Long) pref.get());
            else if (pref.get() instanceof Float)
                editor.putFloat(pref.getName(), (Float) pref.get());
            else throw new InvalidPref(pref);
        }
        editor.apply();
    }

    /**
     *
     */
    public void clearAll() {
        preferences.edit().clear().apply();
    }

    /**
     * @param key
     */
    public void clear(String key) {
        preferences.edit().remove(key).apply();
    }

    /**
     * @param pref
     * @param <T>
     * @return
     * @throws InvalidPrefType
     */
    private <T> T getPreference(Pref<T> pref) throws InvalidPrefType {
        Object data;
        if (pref.get() instanceof String) data = preferences.getString(pref.getName(),
                (String) pref.get());
        else if (pref.get() instanceof Integer) data = preferences.getInt(pref.getName(),
                (Integer) pref.get());
        else if (pref.get() instanceof Boolean) data = preferences.getBoolean(pref.getName(),
                (Boolean) pref.get());
        else if (pref.get() instanceof Long) data = preferences.getLong(pref.getName(),
                (Long) pref.get());
        else if (pref.get() instanceof Float) data = preferences.getFloat(pref.getName(),
                (Float) pref.get());
        else throw new InvalidPrefType(pref.get());
        return (T) data;
    }

    /**
     *
     */
    public interface PreferenceChange {
        /**
         * @param preferences
         * @param key
         */
        void onChange(SharedPreferences preferences, String key);
    }

    /**
     * @param <T>
     */
    public static class Pref<T> {
        /**
         *
         */
        private String name;
        /**
         *
         */
        private T data;

        /**
         * @param key
         * @param data
         */
        Pref(String key, T data) {
            this.name = key;
            this.data = data;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public T get() {
            return data;
        }

        public void set(T data) {
            this.data = data;
        }
    }

    /**
     *
     */
    public static class Builder {
        /**
         *
         */
        private String EMPTY_STRING = "";
        /**
         *
         */
        private boolean EMPTY_BOOLEAN = false;
        /**
         *
         */
        private long EMPTY_LONG = 0;
        /**
         *
         */
        private int EMPTY_INT = 0;
        /**
         *
         */
        private double EMPTY_DOUBLE = 0;

        public Builder EMPTY_STRING(String EMPTY_STRING) {
            this.EMPTY_STRING = EMPTY_STRING;
            return this;
        }

        public Builder EMPTY_BOOLEAN(boolean EMPTY_BOOLEAN) {
            this.EMPTY_BOOLEAN = EMPTY_BOOLEAN;
            return this;
        }

        public Builder EMPTY_LONG(long EMPTY_LONG) {
            this.EMPTY_LONG = EMPTY_LONG;
            return this;
        }

        public Builder EMPTY_INT(int EMPTY_INT) {
            this.EMPTY_INT = EMPTY_INT;
            return this;
        }

        public Builder EMPTY_DOUBLE(double EMPTY_DOUBLE) {
            this.EMPTY_DOUBLE = EMPTY_DOUBLE;
            return this;
        }

        /**
         * @return
         */
        public Preferences build() {
            Preferences preferences = new Preferences();
            preferences.EMPTY_BOOLEAN = EMPTY_BOOLEAN;
            preferences.EMPTY_STRING = EMPTY_STRING;
            preferences.EMPTY_DOUBLE = EMPTY_DOUBLE;
            preferences.EMPTY_INT = EMPTY_INT;
            preferences.EMPTY_LONG = EMPTY_LONG;
            return preferences;
        }

        /**
         * @param name
         * @return
         */
        public Preferences build(String name) {
            Preferences preferences = new Preferences(name);
            preferences.EMPTY_BOOLEAN = EMPTY_BOOLEAN;
            preferences.EMPTY_STRING = EMPTY_STRING;
            preferences.EMPTY_DOUBLE = EMPTY_DOUBLE;
            preferences.EMPTY_INT = EMPTY_INT;
            preferences.EMPTY_LONG = EMPTY_LONG;
            return preferences;
        }
    }
}
