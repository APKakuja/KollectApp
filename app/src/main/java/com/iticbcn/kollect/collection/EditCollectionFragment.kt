package com.iticbcn.kollect.collection

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.iticbcn.kollect.R
import com.iticbcn.kollect.activities.CollectionActivity
import com.iticbcn.kollect.app.ProfileFragment
import com.iticbcn.kollect.data.collectionCategories

class EditCollectionFragment : Fragment(R.layout.fragment_edit_collection) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val collectionId = requireArguments()
            .getString(CollectionActivity.ARG_COLLECTION_ID)
            .orEmpty()
        val collection = ProfileFragment.collections.find { it.id == collectionId }

        view.findViewById<View>(R.id.collectionNameInput)
            .let { it as TextInputEditText }
            .setText(collection?.name)
        view.findViewById<View>(R.id.collectionDescriptionInput)
            .let { it as TextInputEditText }
            .setText(collection?.description)
        val categoryInput = view.findViewById<AutoCompleteTextView>(R.id.collectionCategoryInput)
        setupCategoryDropdown(categoryInput)
        categoryInput.setText(collection?.category ?: "Carta", false)
        view.findViewById<MaterialButton>(R.id.createCollectionButton)
            .setText(R.string.save_collection_changes)

        view.findViewById<MaterialButton>(R.id.createCollectionButton).setOnClickListener {
            val nameInput = view.findViewById<TextInputEditText>(R.id.collectionNameInput)
            val descriptionInput = view.findViewById<TextInputEditText>(R.id.collectionDescriptionInput)
            val name = nameInput.text?.toString()?.trim().orEmpty()
            val category = categoryInput.text?.toString()?.trim().orEmpty()
            if (name.isEmpty()) {
                nameInput.error = "Escriu un nom"
                return@setOnClickListener
            }

            val index = ProfileFragment.collections.indexOfFirst { it.id == collectionId }
            if (index >= 0) {
                ProfileFragment.collections[index] = ProfileFragment.collections[index].copy(
                    name = name,
                    description = descriptionInput.text?.toString()?.trim().orEmpty(),
                    category = category.ifEmpty { "Carta" }
                )
            }
            Toast.makeText(requireContext(), "Col·lecció modificada", Toast.LENGTH_SHORT).show()
            requireActivity().finish()
        }
    }

    private fun setupCategoryDropdown(field: AutoCompleteTextView) {
        field.setAdapter(ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            collectionCategories
        ))
    }
}
