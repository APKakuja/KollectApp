package com.iticbcn.kollect.objectscreen

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.iticbcn.kollect.R
import com.iticbcn.kollect.activities.ObjectActivity
import com.iticbcn.kollect.collection.CollectionDetailFragment
import com.iticbcn.kollect.data.objectConditions

class EditObjectFragment : Fragment(R.layout.fragment_edit_object) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val objectId = requireArguments()
            .getString(ObjectActivity.ARG_OBJECT_ID)
            .orEmpty()
        val item = CollectionDetailFragment.objects.find { it.id == objectId }

        val conditionInput = view.findViewById<AutoCompleteTextView>(R.id.objectConditionInput)
        setupConditionDropdown(conditionInput)
        conditionInput.setText(item?.condition ?: "Molt bo", false)

        view.findViewById<android.widget.TextView>(R.id.objectFormTitle)
            .setText(R.string.edit_object_title)
        view.findViewById<com.google.android.material.button.MaterialButton>(R.id.saveObjectButton)
            .setText(R.string.save_object_changes)
        view.findViewById<TextInputEditText>(R.id.objectNameInput)
            .setText(item?.name ?: getString(R.string.sample_object_name))
        view.findViewById<TextInputEditText>(R.id.objectReleaseYearInput)
            .setText(item?.releaseYear)
        view.findViewById<TextInputEditText>(R.id.objectAcquisitionYearInput)
            .setText(item?.acquisitionYear)
        view.findViewById<TextInputEditText>(R.id.objectPriceInput)
            .setText(item?.price)
        view.findViewById<TextInputEditText>(R.id.objectDescriptionInput)
            .setText(item?.details ?: getString(R.string.sample_object_description))
        view.findViewById<MaterialButton>(R.id.saveObjectButton).setOnClickListener {
            val nameInput = view.findViewById<TextInputEditText>(R.id.objectNameInput)
            val descriptionInput = view.findViewById<TextInputEditText>(R.id.objectDescriptionInput)
            val releaseYearInput = view.findViewById<TextInputEditText>(R.id.objectReleaseYearInput)
            val acquisitionYearInput = view.findViewById<TextInputEditText>(R.id.objectAcquisitionYearInput)
            val priceInput = view.findViewById<TextInputEditText>(R.id.objectPriceInput)
            val name = nameInput.text?.toString()?.trim().orEmpty()
            val details = descriptionInput.text?.toString()?.trim().orEmpty()
            if (name.isEmpty()) {
                nameInput.error = "Escriu un nom"
                return@setOnClickListener
            }
            val condition = conditionInput.text?.toString()?.trim().orEmpty()
                .ifEmpty { "Molt bo" }

            val index = CollectionDetailFragment.objects.indexOfFirst { it.id == objectId }
            if (index >= 0) {
                val existing = CollectionDetailFragment.objects[index]
                CollectionDetailFragment.objects[index] = existing.copy(
                    name = name,
                    details = details.ifEmpty { "Sense descripció" },
                    condition = condition,
                    releaseYear = releaseYearInput.text?.toString()?.trim().orEmpty(),
                    acquisitionYear = acquisitionYearInput.text?.toString()?.trim().orEmpty(),
                    price = priceInput.text?.toString()?.trim().orEmpty()
                )
            }
            Toast.makeText(requireContext(), "Objecte modificat", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
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
