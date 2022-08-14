package com.mominulcse7.githubclone.sessions

import android.content.Context
import android.content.SharedPreferences
import com.mominulcse7.githubclone.utils.ConstantKeys.SORT_BY
import com.mominulcse7.githubclone.utils.ConstantKeys.SORT_ORDER
import com.mominulcse7.githubclone.utils.ConstantKeys.S_P_TEMP
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TempSharePref @Inject constructor(@ApplicationContext context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(S_P_TEMP, Context.MODE_PRIVATE)

    fun saveSortBy(input: String) {
        val editor = prefs.edit()
        editor.putString(SORT_BY, input)
        editor.apply()
    }

    fun getSortBy(): String? {
        return prefs.getString(SORT_BY, "stars")
    }

    fun saveOrderBy(input: String) {
        val editor = prefs.edit()
        editor.putString(SORT_ORDER, input)
        editor.apply()
    }

    fun getOrderBy(): String? {
        return prefs.getString(SORT_ORDER, "desc")
    }
}