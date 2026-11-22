package com.iticbcn.kollect.app

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.materialswitch.MaterialSwitch
import com.iticbcn.kollect.R

/**
 * Pantalla de preferències de l'aplicació.
 *
 * Permet canviar entre el tema clar i el tema fosc i conserva la preferència
 * mitjançant SharedPreferences.
 */
class SettingsFragment : Fragment(R.layout.fragment_settings) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val preferences = requireContext().getSharedPreferences(PREFERENCES_NAME, 0)
        val themeSwitch = view.findViewById<MaterialSwitch>(R.id.themeSwitch)
        val themeModeLabel = view.findViewById<TextView>(R.id.themeModeLabel)

        val darkModeEnabled = preferences.getBoolean(DARK_MODE_KEY, false)
        themeSwitch.isChecked = darkModeEnabled
        updateThemeLabel(themeModeLabel, darkModeEnabled)

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            preferences.edit().putBoolean(DARK_MODE_KEY, isChecked).apply()
            updateThemeLabel(themeModeLabel, isChecked)
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }
    }

    private fun updateThemeLabel(label: TextView, darkModeEnabled: Boolean) {
        label.setText(if (darkModeEnabled) R.string.dark_theme else R.string.light_theme)
    }

    companion object {
        private const val PREFERENCES_NAME = "kollect_preferences"
        private const val DARK_MODE_KEY = "dark_mode"
    }
}
