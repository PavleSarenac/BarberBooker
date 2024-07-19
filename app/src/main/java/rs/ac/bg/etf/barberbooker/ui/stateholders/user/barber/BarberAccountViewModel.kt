package rs.ac.bg.etf.barberbooker.ui.stateholders.user.barber

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BarberAccountViewModel @Inject constructor() : ViewModel() {
    fun logOut(context: Context) = viewModelScope.launch(Dispatchers.IO) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("login_data", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("is_logged_in", false)
            putString("user_email", "")
            putString("user_type", "")
            apply()
        }
    }
}