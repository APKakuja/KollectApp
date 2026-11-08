package com.iticbcn.kollect.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.iticbcn.kollect.R

class RegisterFragment : Fragment(R.layout.fragment_register) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<View>(R.id.registerBackButton).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        view.findViewById<View>(R.id.createAccountButton).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}
