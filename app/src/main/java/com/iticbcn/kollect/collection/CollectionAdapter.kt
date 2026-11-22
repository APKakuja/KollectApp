package com.iticbcn.kollect.collection

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.iticbcn.kollect.R
import com.iticbcn.kollect.data.CollectionItem

/**
 * Adapter de la graella de col·leccions.
 *
 * Manté els elements de la llista i crea els holders corresponents.
 *
 * @param items Col·leccions mostrades.
 * @param onCollectionClick Acció en seleccionar una col·lecció.
 */
class CollectionAdapter(
    private var items: MutableList<CollectionItem>,
    private val onCollectionClick: (CollectionItem) -> Unit,
    private val onCollectionLongClick: (CollectionItem, View) -> Unit
) : RecyclerView.Adapter<CollectionAdapter.Holder>() {

    /** Identifica les vistes d'una targeta de colecció. */
    class Holder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_collection, parent, false)
    ) {
        val name: TextView = itemView.findViewById(R.id.collectionName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder(parent)

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = items[position]
        holder.name.text = item.name
        holder.itemView.setOnClickListener { onCollectionClick(item) }
        holder.itemView.setOnLongClickListener {
            onCollectionLongClick(item, it)
            true
        }
    }

    override fun getItemCount() = items.size

    fun update(newItems: List<CollectionItem>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }
}
