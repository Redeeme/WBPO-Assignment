package sk.doxxbet.doxxbetui.wpbo_assignment.client

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val SHARED_PREFERENCES_NAME = "MyPrefs"
        private const val REGISTERED_KEY = "registered"
    }

    fun setRegistered(data: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(REGISTERED_KEY, data)
        editor.apply()
    }

    fun getRegistered(): Boolean {
        return sharedPreferences.getBoolean(REGISTERED_KEY, false)
    }
}