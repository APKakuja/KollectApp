package com.iticbcn.kollect.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.iticbcn.kollect.R
import com.iticbcn.kollect.app.ProfileFragment
import com.iticbcn.kollect.app.SearchFragment
import com.iticbcn.kollect.app.SettingsFragment
import com.iticbcn.kollect.databinding.ActivityAppBinding

/**
 * Activity principal de l'aplicació.
 *
 * Gestiona el menú inferior i substitueix el fragment visible segons l'opció
 * seleccionada. Conserva l'opció actual quan l'Activity es recrea.
 *
 */
class AppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAppBinding
    private var selectedMenuItemId = R.id.profileFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
            )
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        selectedMenuItemId = savedInstanceState?.getInt(STATE_SELECTED_MENU_ITEM)
            ?: R.id.profileFragment

        if (savedInstanceState == null) showMainFragment(selectedMenuItemId)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            if (item.itemId == selectedMenuItemId) false
            else showMainFragment(item.itemId)
        }

        binding.bottomNavigation.selectedItemId = selectedMenuItemId
        binding.topAppBar.title = when (selectedMenuItemId) {
            R.id.searchFragment -> getString(R.string.buscar)
            R.id.profileFragment -> getString(R.string.perfil)
            R.id.settingsFragment -> getString(R.string.settings)
            else -> getString(R.string.perfil)
        }
    }

    /**
     * Mostra el fragment associat a una opció del menú inferior i actualitza
     * el títol de l'AppBar.
     *
     * @param itemId Identificador seleccionat al menú inferior.
     * @return true si l'opció és vàlida i s'ha mostrat, false si no.
     */
    private fun showMainFragment(itemId: Int): Boolean {
        val fragmentAndTitle = when (itemId) {
            R.id.searchFragment -> SearchFragment() to getString(R.string.buscar)
            R.id.profileFragment -> ProfileFragment() to getString(R.string.perfil)
            R.id.settingsFragment -> SettingsFragment() to getString(R.string.settings)
            else -> return false
        }

        selectedMenuItemId = itemId
        binding.topAppBar.title = fragmentAndTitle.second
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.appNavHost, fragmentAndTitle.first)
            .commit()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(STATE_SELECTED_MENU_ITEM, selectedMenuItemId)
        super.onSaveInstanceState(outState)
    }

    companion object {
        private const val STATE_SELECTED_MENU_ITEM = "selected_menu_item"
    }
}