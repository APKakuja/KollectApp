package com.iticbcn.kollect.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.iticbcn.kollect.AppActivity
import com.iticbcn.kollect.R

class LoginFragment : Fragment(R.layout.fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<View>(R.id.registerButton).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.authNavHost, RegisterFragment())
                .addToBackStack(null)
                .commit()
        }
        view.findViewById<View>(R.id.loginButton).setOnClickListener(::openApp)
    }

    private fun openApp(@Suppress("UNUSED_PARAMETER") view: View) {
        startActivity(Intent(requireContext(), AppActivity::class.java))
        requireActivity().finish()
    }
}
