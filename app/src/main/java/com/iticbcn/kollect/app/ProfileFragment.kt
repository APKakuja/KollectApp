package com.iticbcn.kollect.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iticbcn.kollect.activities.CollectionActivity
import com.iticbcn.kollect.R
import com.iticbcn.kollect.collection.CollectionAdapter
import com.iticbcn.kollect.collection.CollectionDetailFragment
import com.iticbcn.kollect.data.CollectionItem

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var collectionAdapter: CollectionAdapter? = null
    private var guideDismissed = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.collectionsRecyclerView)
        collectionAdapter = CollectionAdapter(
            collections.toMutableList(),
            onCollectionClick = { collection ->
                Toast.makeText(requireContext(), "Obrint ${collection.name}", Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), CollectionActivity::class.java).apply {
                    putExtra(CollectionActivity.EXTRA_COLLECTION_ID, collection.id)
                })
            },
            onCollectionLongClick = { collection, anchor ->
                showCollectionActions(anchor, collection)
            }
        )
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = collectionAdapter

        view.findViewById<View>(R.id.addCollectionCard).setOnClickListener {
            Toast.makeText(requireContext(), "Afegir col·lecció", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(), CollectionActivity::class.java).apply {
                putExtra(CollectionActivity.EXTRA_CREATE, true)
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
        collectionAdapter?.update(collections)
        view?.let(::updateEmptyGuide)
    }

    private fun updateEmptyGuide(root: View) {
        val guide = root.findViewById<View>(R.id.emptyCollectionGuide)
        guide.visibility =
            if (collections.isEmpty() && !guideDismissed) View.VISIBLE
            else View.GONE
    }

    private fun showCollectionActions(anchor: View, collection: com.iticbcn.kollect.data.CollectionItem) {
        val popupView = LayoutInflater.from(requireContext())
            .inflate(com.iticbcn.kollect.R.layout.popup_collection_actions, null)
        val popup = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )
        popupView.findViewById<View>(com.iticbcn.kollect.R.id.editCollectionAction)
            .setOnClickListener {
                popup.dismiss()
                val intent = Intent(requireContext(), CollectionActivity::class.java).apply {
                    putExtra(CollectionActivity.EXTRA_COLLECTION_ID, collection.id)
                    putExtra(CollectionActivity.EXTRA_EDIT, true)
                }
                startActivity(intent)
            }
        popupView.findViewById<View>(com.iticbcn.kollect.R.id.deleteCollectionAction)
            .setOnClickListener {
                popup.dismiss()
                AlertDialog.Builder(requireContext())
                    .setTitle("Eliminar col·lecció")
                    .setMessage("Vols eliminar «${collection.name}» i els seus objectes?")
                    .setPositiveButton("Eliminar") { _, _ ->
                        collections.removeAll { it.id == collection.id }
                        CollectionDetailFragment.objects.removeAll { it.collectionId == collection.id }
                        collectionAdapter?.update(collections)
                        updateEmptyGuide(requireView())
                        Toast.makeText(requireContext(), "Col·lecció eliminada", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Cancel·lar", null)
                    .show()
            }
        popup.showAsDropDown(anchor, 0, 8, Gravity.CENTER_HORIZONTAL)
    }

    companion object {
        val collections = mutableListOf(
            CollectionItem(
                "vintage_cards",
                "Cartes vintage",
                "Col·lecció de prova",
                "Carta"
            )
        )
    }
}
