package com.iticbcn.kollect.objectscreen

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.iticbcn.kollect.R
import com.iticbcn.kollect.activities.ObjectActivity
import com.iticbcn.kollect.collection.CollectionDetailFragment
import com.iticbcn.kollect.data.objectConditions
import com.iticbcn.kollect.data.ObjectItem
import java.util.UUID

class AddObjectFragment : Fragment(R.layout.fragment_add_object) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val collectionId = requireArguments()
            .getString(ObjectActivity.ARG_COLLECTION_ID)
            .orEmpty()
        val nameInput = view.findViewById<TextInputEditText>(R.id.objectNameInput)
        val descriptionInput = view.findViewById<TextInputEditText>(R.id.objectDescriptionInput)
        val conditionInput = view.findViewById<AutoCompleteTextView>(R.id.objectConditionInput)
        val releaseYearInput = view.findViewById<TextInputEditText>(R.id.objectReleaseYearInput)
        val acquisitionYearInput = view.findViewById<TextInputEditText>(R.id.objectAcquisitionYearInput)
        val priceInput = view.findViewById<TextInputEditText>(R.id.objectPriceInput)

        setupConditionDropdown(conditionInput)

        view.findViewById<MaterialButton>(R.id.saveObjectButton).setOnClickListener {
            val name = nameInput.text?.toString()?.trim().orEmpty()
            val description = descriptionInput.text?.toString()?.trim().orEmpty()
            if (name.isEmpty()) {
                nameInput.error = "Escriu un nom"
                return@setOnClickListener
            }
            val condition = conditionInput.text?.toString()?.trim().orEmpty()
                .ifEmpty { "Molt bo" }

            CollectionDetailFragment.objects.add(
                ObjectItem(
                    UUID.randomUUID().toString(),
                    collectionId,
                    name,
                    description.ifEmpty { "Sense descripció" },
                    condition,
                    releaseYearInput.text?.toString()?.trim().orEmpty(),
                    acquisitionYearInput.text?.toString()?.trim().orEmpty(),
                    priceInput.text?.toString()?.trim().orEmpty()
                )
            )
            Toast.makeText(requireContext(), "Objecte afegit", Toast.LENGTH_SHORT).show()
            requireActivity().finish()
        }
    }

    private fun setupConditionDropdown(field: AutoCompleteTextView) {
        field.setAdapter(ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            objectConditions
        ))
    }
}
