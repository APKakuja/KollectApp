package com.iticbcn.kollect.objectscreen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.iticbcn.kollect.activities.ObjectActivity
import com.iticbcn.kollect.R
import com.iticbcn.kollect.collection.CollectionDetailFragment
import com.iticbcn.kollect.app.ProfileFragment
import android.widget.TextView

class ObjectDetailFragment : Fragment(R.layout.fragment_object_detail) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val objectId = requireArguments()
            .getString(ObjectActivity.ARG_OBJECT_ID)
            .orEmpty()
        val collectionId = requireArguments()
            .getString(ObjectActivity.ARG_COLLECTION_ID)
            .orEmpty()
        val item = CollectionDetailFragment.objects.find { it.id == objectId }
        val collection = ProfileFragment.collections.find { it.id == collectionId }

        view.findViewById<TextView>(R.id.detailObjectName).text =
            item?.name ?: getString(R.string.sample_object_name)
        view.findViewById<TextView>(R.id.detailObjectCategory).text =
            collection?.category ?: getString(R.string.object_category_card)
        view.findViewById<TextView>(R.id.detailReleaseYear).text =
            item?.releaseYear?.takeIf { it.isNotEmpty() } ?: "-"
        view.findViewById<TextView>(R.id.detailAcquisitionYear).text =
            item?.acquisitionYear?.takeIf { it.isNotEmpty() } ?: "-"
        view.findViewById<TextView>(R.id.detailObjectCondition).text =
            item?.condition ?: getString(R.string.object_condition_good)
        view.findViewById<TextView>(R.id.detailObjectDescription).text =
            item?.details ?: getString(R.string.sample_object_description)

        view.findViewById<MaterialButton>(R.id.editObjectButton).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.objectNavHost, EditObjectFragment().apply {
                    arguments = Bundle().apply {
                        putString(ObjectActivity.ARG_OBJECT_ID, objectId)
                        putString(ObjectActivity.ARG_COLLECTION_ID, collectionId)
                    }
                })
                .addToBackStack(null)
                .commit()
        }

        view.findViewById<MaterialButton>(R.id.deleteObjectButton).setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Eliminar objecte")
                .setMessage("Vols eliminar aquest objecte?")
                .setPositiveButton("Eliminar") { _, _ ->
                    val removed = CollectionDetailFragment.objects.removeAll { it.id == objectId }
                    if (removed) {
                        Toast.makeText(requireContext(), "Objecte eliminat", Toast.LENGTH_SHORT).show()
                        requireActivity().finish()
                    }
                }
                .setNegativeButton("Cancel·lar", null)
                .show()
        }
    }
}
