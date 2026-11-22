package com.iticbcn.kollect.collection

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iticbcn.kollect.activities.ObjectActivity
import com.iticbcn.kollect.R
import com.iticbcn.kollect.data.ObjectItem
import com.iticbcn.kollect.objectscreen.ObjectAdapter

class CollectionDetailFragment : Fragment(R.layout.fragment_collection_detail) {
    private var collectionId = ""
    private var objectAdapter: ObjectAdapter? = null
    private var guideDismissed = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        collectionId = requireArguments()
            .getString("collectionId")
            .orEmpty()
        val recyclerView = view.findViewById<RecyclerView>(R.id.objectsRecyclerView)
        objectAdapter = ObjectAdapter(
            objects.filter { it.collectionId == collectionId }.toMutableList()
        ) { objectItem ->
            Toast.makeText(requireContext(), "Obrint ${objectItem.name}", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(), ObjectActivity::class.java).apply {
                putExtra(ObjectActivity.EXTRA_OBJECT_ID, objectItem.id)
                putExtra(ObjectActivity.EXTRA_COLLECTION_ID, collectionId)
            })
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = objectAdapter

        view.findViewById<View>(R.id.addObjectCard).setOnClickListener {
            Toast.makeText(requireContext(), "Afegir objecte", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(), ObjectActivity::class.java).apply {
                putExtra(ObjectActivity.EXTRA_CREATE, true)
                putExtra(ObjectActivity.EXTRA_COLLECTION_ID, collectionId)
            })
        }

        view.findViewById<View>(R.id.closeEmptyCollectionGuide).setOnClickListener {
            guideDismissed = true
            view.findViewById<View>(R.id.emptyCollectionGuide).visibility = View.GONE
        }

        updateEmptyGuide(view)
    }

    override fun onResume() {
        super.onResume()
        val collectionObjects = objects.filter { it.collectionId == collectionId }
        objectAdapter?.update(collectionObjects)
        view?.let(::updateEmptyGuide)
    }

    private fun updateEmptyGuide(root: View) {
        val hasObjects = objects.any { it.collectionId == collectionId }
        val guide = root.findViewById<View>(R.id.emptyCollectionGuide)
        guide.visibility = if (!hasObjects && !guideDismissed) View.VISIBLE else View.GONE
    }

    companion object {
        val objects = mutableListOf(
            ObjectItem(
                "vintage_card_sample",
                "vintage_cards",
                "Carta especial",
                "Carta vintage de la col·lecció",
                "Molt bo",
                "2005",
                "2026",
                "25"
            )
        )
    }
}
