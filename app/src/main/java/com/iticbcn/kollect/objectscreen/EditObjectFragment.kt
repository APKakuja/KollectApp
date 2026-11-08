package com.iticbcn.kollect.objectscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.iticbcn.kollect.R

class EditObjectFragment : Fragment(R.layout.fragment_edit_object) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<android.widget.TextView>(R.id.objectFormTitle)
            .setText(R.string.edit_object_title)
        view.findViewById<com.google.android.material.button.MaterialButton>(R.id.saveObjectButton)
            .setText(R.string.save_object_changes)
        view.findViewById<TextInputEditText>(R.id.objectNameInput)
            .setText(R.string.sample_object_name)
        view.findViewById<TextInputEditText>(R.id.objectReleaseYearInput)
            .setText("2005")
        view.findViewById<TextInputEditText>(R.id.objectAcquisitionYearInput)
            .setText("2026")
        view.findViewById<TextInputEditText>(R.id.objectPriceInput)
            .setText("25")
        view.findViewById<TextInputEditText>(R.id.objectDescriptionInput)
            .setText(R.string.sample_object_description)
        view.findViewById<MaterialButton>(R.id.saveObjectButton).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}
