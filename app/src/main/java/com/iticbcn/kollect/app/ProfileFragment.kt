package com.iticbcn.kollect.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.iticbcn.kollect.CollectionActivity
import com.iticbcn.kollect.R

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<View>(R.id.addCollectionCard).setOnClickListener {
            startActivity(Intent(requireContext(), CollectionActivity::class.java).apply {
                putExtra(CollectionActivity.EXTRA_CREATE, true)
            })
        }
        view.findViewById<View>(R.id.vintageCardsCollection).setOnClickListener {
            startActivity(Intent(requireContext(), CollectionActivity::class.java).apply {
                putExtra(CollectionActivity.EXTRA_COLLECTION_ID, VINTAGE_CARDS_ID)
            })
        }
    }

    companion object {
        private const val VINTAGE_CARDS_ID = "vintage_cards"
    }
}
