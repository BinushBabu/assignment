package in.museon.assignment.data.preferences;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import in.museon.assignment.app.App;


/**
 * The Class PreferenceWrapper for managing preference values.
 * inserting and retrieving values from preference.
 *
 */
class PreferenceWrapper {

    /**
     * The application preference.
     */
    private final SharedPreferences applicationPreference;

    /**
     * The application preference editor.
     */
    private final SharedPreferences.Editor editor;

    /**
     * Instantiates a new application preference.
     */
    PreferenceWrapper() {
        super();
        // TODO Auto-generated constructor stub
        applicationPreference = PreferenceManager
                .getDefaultSharedPreferences(App.getContext());
        editor = applicationPreference.edit();
    }

    /**
     * Put the string value in preference.
     *
     * @param key   the key
     * @param value the string value
     */
    void putStringPref(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Gets the string preference value.
     *
     * @param key      the key
     * @param defValue the default string value
     * @return the string value from preference
     */
    String getStringPref(String key, String defValue) {
        return applicationPreference.getString(key, defValue);

    }

    /**
     * Put the integer value in preference.
     *
     * @param key   the key
     * @param value the integer value
     */
    void putIntegerPref(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Gets the integer preference value.
     *
     * @param key      the key
     * @param defValue the default integer value
     * @return the integer value from preference
     */
    int getIntegerPref(String key, int defValue) {
        return applicationPreference.getInt(key, defValue);
    }

    /**
     * Put the boolean value in preference.
     *
     * @param key   the key
     * @param value the boolean value
     */
    void putBooleanPref(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Gets the boolean preference value.
     *
     * @param key      the key
     * @param defValue the default boolean value
     * @return the boolean value from preference
     */
    boolean getBooleanPref(String key, boolean defValue) {
        return applicationPreference.getBoolean(key, defValue);
    }

    /**
     * Put the long value in preference.
     *
     * @param key   the key
     * @param value the long value
     */
    void putLongPref(String key, long value) {
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * Gets the long preference value.
     *
     * @param key      the key
     * @param defValue the default long value
     * @return the long value from preference
     */
    long getLongPref(String key, long defValue) {
        return applicationPreference.getLong(key, defValue);
    }

    /**
     * Put the float value in preference.
     *
     * @param key   the key
     * @param value the float value
     */
    void putFloatPref(String key, float value) {
        editor.putFloat(key, value);
        editor.apply();
    }

    /**
     * Gets the float preference value.
     *
     * @param key      the key
     * @param defValue the default float value
     * @return the float value from preference
     */
    float getFloatPref(String key, float defValue) {
        return applicationPreference.getFloat(key, defValue);
    }




    private static Gson GSON = new Gson();

    /**
     * Put object.
     *
     * @param key    the key
     * @param object the object
     */
    public void putObject(String key, Object object) {

        if (object == null) {
            throw new IllegalArgumentException("Object is null");
        }
        if (key.equals("") || key == null) {
            throw new IllegalArgumentException("Key is empty or null");
        }
        applicationPreference.edit().putString(key, GSON.toJson(object)).apply();
    }

    /**
     * Gets object.
     *
     * @param <T>        the type parameter
     * @param key        the key
     * @param class_name the class name
     * @return the object
     */
    public <T> T getObject(String key, Class<T> class_name) {

        String gson = applicationPreference.getString(key, null);
        if (gson == null) {
            return null;
        } else {
            try {
                return GSON.fromJson(gson, class_name);
            } catch (Exception e) {
                throw new IllegalArgumentException("Object stored with key "
                        + key + " is instance of other class");
            }
        }
    }


    /**
     * Clear all preference.
     */
    void clearAllPreference() {
        editor.clear();
    }
}
