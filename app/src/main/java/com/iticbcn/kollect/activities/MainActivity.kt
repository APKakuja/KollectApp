package com.iticbcn.kollect.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.iticbcn.kollect.R
import com.iticbcn.kollect.auth.LoginFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        val splashStartedAt = System.currentTimeMillis()
        splashScreen.setKeepOnScreenCondition {
            System.currentTimeMillis() - splashStartedAt < 900
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        applySystemInsets(R.id.authNavHost)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.authNavHost, LoginFragment())
                .commit()
        }
    }

    private fun applySystemInsets(rootId: Int) {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(rootId)) { view, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
            )
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}