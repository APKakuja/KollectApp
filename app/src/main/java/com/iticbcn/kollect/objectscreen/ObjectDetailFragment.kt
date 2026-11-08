package com.iticbcn.kollect.objectscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.iticbcn.kollect.ObjectActivity
import com.iticbcn.kollect.R

class ObjectDetailFragment : Fragment(R.layout.fragment_object_detail) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val objectId = requireArguments()
            .getString(ObjectActivity.ARG_OBJECT_ID)
            .orEmpty()
        val collectionId = requireArguments()
            .getString(ObjectActivity.ARG_COLLECTION_ID)
            .orEmpty()

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
    }
}
