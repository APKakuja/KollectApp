package com.iticbcn.kollect.collection

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.iticbcn.kollect.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.iticbcn.kollect.data.CollectionItem
import com.iticbcn.kollect.app.ProfileFragment
import com.iticbcn.kollect.data.collectionCategories
import java.util.UUID

class CreateCollectionFragment : Fragment(R.layout.fragment_create_collection) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val nameInput = view.findViewById<TextInputEditText>(R.id.collectionNameInput)
        val descriptionInput = view.findViewById<TextInputEditText>(R.id.collectionDescriptionInput)
        val categoryInput = view.findViewById<AutoCompleteTextView>(R.id.collectionCategoryInput)
        setupCategoryDropdown(categoryInput)
        view.findViewById<MaterialButton>(R.id.createCollectionButton).setOnClickListener {
            val name = nameInput.text?.toString()?.trim().orEmpty()
            val description = descriptionInput.text?.toString()?.trim().orEmpty()
            val category = categoryInput.text?.toString()?.trim().orEmpty()
            if (name.isEmpty()) {
                nameInput.error = "Escriu un nom"
                return@setOnClickListener
            }

            ProfileFragment.collections.add(
                CollectionItem(UUID.randomUUID().toString(), name, description, category)
            )
            Toast.makeText(requireContext(), "Col·lecció creada", Toast.LENGTH_SHORT).show()
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
