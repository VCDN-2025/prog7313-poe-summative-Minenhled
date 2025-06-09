package vcmsa.projects.expensetracker.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)

    var isFirstLaunch: Boolean
        get() = sharedPreferences.getBoolean(Constants.PREF_FIRST_LAUNCH, true)
        set(value) = sharedPreferences.edit().putBoolean(Constants.PREF_FIRST_LAUNCH, value).apply()

    var currencySymbol: String
        get() = sharedPreferences.getString(Constants.PREF_CURRENCY_SYMBOL, "$") ?: "$"
        set(value) = sharedPreferences.edit().putString(Constants.PREF_CURRENCY_SYMBOL, value).apply()

    var defaultCategory: String
        get() = sharedPreferences.getString(Constants.PREF_DEFAULT_CATEGORY, "Other") ?: "Other"
        set(value) = sharedPreferences.edit().putString(Constants.PREF_DEFAULT_CATEGORY, value).apply()

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}