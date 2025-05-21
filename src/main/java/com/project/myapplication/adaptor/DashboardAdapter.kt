package com.project.myapplication.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.myapplication.R
import com.project.myapplication.models.Entity
import javax.inject.Inject

class DashboardAdapter @Inject constructor(
    private val items: List<Entity>,
    private val onItemClick: (Entity) -> Unit
) : RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.name)
        private val culture: TextView = view.findViewById(R.id.culture)
        private val domain: TextView = view.findViewById(R.id.domain)
        private val symbol: TextView = view.findViewById(R.id.symbol)
        private val parentage: TextView = view.findViewById(R.id.parentage)
        private val romanEquivalent: TextView = view.findViewById(R.id.romanEquivalent)

        // Bind data
        fun bind(item: Entity) {
            name.text = item.name
            culture.text = item.culture
            domain.text = item.domain
            symbol.text = item.symbol
            parentage.text = item.parentage
            romanEquivalent.text = item.romanEquivalent

// add click event
            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
