package com.iticbcn.kollect.collection

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.iticbcn.kollect.ObjectActivity
import com.iticbcn.kollect.R

class CollectionDetailFragment : Fragment(R.layout.fragment_collection_detail) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val collectionId = requireArguments()
            .getString("collectionId")
            .orEmpty()
        view.findViewById<View>(R.id.addObjectCard).setOnClickListener {
            startActivity(Intent(requireContext(), ObjectActivity::class.java).apply {
                putExtra(ObjectActivity.EXTRA_CREATE, true)
                putExtra(ObjectActivity.EXTRA_COLLECTION_ID, collectionId)
            })
        }
        view.findViewById<View>(R.id.sampleObjectCard).setOnClickListener {
            startActivity(Intent(requireContext(), ObjectActivity::class.java).apply {
                putExtra(ObjectActivity.EXTRA_OBJECT_ID, SAMPLE_OBJECT_ID)
                putExtra(ObjectActivity.EXTRA_COLLECTION_ID, collectionId)
            })
        }
    }

    companion object {
        private const val SAMPLE_OBJECT_ID = "vintage_card_sample"
    }
}
