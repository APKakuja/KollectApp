package com.iticbcn.kollect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.iticbcn.kollect.app.ProfileFragment
import com.iticbcn.kollect.app.SearchFragment
import com.iticbcn.kollect.app.SettingsFragment
import com.iticbcn.kollect.databinding.ActivityAppBinding

class AppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAppBinding

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
        if (savedInstanceState == null) {
            showMainFragment(ProfileFragment(), "Perfil")
        }
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.searchFragment -> showMainFragment(SearchFragment(), "Buscar")
                R.id.profileFragment -> showMainFragment(ProfileFragment(), "Perfil")
                R.id.settingsFragment -> showMainFragment(SettingsFragment(), "Ajustes")
                else -> return@setOnItemSelectedListener false
            }
            true
        }
        binding.bottomNavigation.selectedItemId = R.id.profileFragment
    }

    private fun showMainFragment(fragment: Fragment, title: String) {
        binding.topAppBar.title = title
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.appNavHost, fragment)
            .commit()
    }
}
