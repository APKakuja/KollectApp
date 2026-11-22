package com.iticbcn.kollect.objectscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iticbcn.kollect.R
import com.iticbcn.kollect.data.ObjectItem

/**
 * Adapter de la llista vertical d'objectes.
 *
 * @param items Objectes mostrats.
 * @param onObjectClick Acció en seleccionar un objecte.
 */
class ObjectAdapter(
    private var items: MutableList<ObjectItem>,
    private val onObjectClick: (ObjectItem) -> Unit
) : RecyclerView.Adapter<ObjectAdapter.Holder>() {

    /** Holder que identifica les vistes d'una fila d'objecte. */
    class Holder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_object, parent, false)
    ) {
        val name: TextView = itemView.findViewById(R.id.objectName)
        val details: TextView = itemView.findViewById(R.id.objectDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder(parent)

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = items[position]
        holder.name.text = item.name
        holder.details.text = item.details
        holder.itemView.setOnClickListener { onObjectClick(item) }
    }

    override fun getItemCount() = items.size

    fun update(newItems: List<ObjectItem>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }
}
