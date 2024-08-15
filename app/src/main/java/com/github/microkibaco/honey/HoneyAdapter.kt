package com.github.microkibaco.honey

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
/**
 * @see: S1032118
 * @author: YangZhengYou
 */
class HoneyAdapter(
    private val brands: List<Brand>,
    val onItemClicked: (Brand) -> Unit
) : RecyclerView.Adapter<HoneyAdapter.HoneyViewHolder>() {

    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoneyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_honey, parent, false)
        return HoneyViewHolder(view)
    }

    override fun onBindViewHolder(holder: HoneyViewHolder, position: Int) {
        val brand = brands[position]
        holder.bind(brand, position == selectedPosition)
        holder.itemView.setOnClickListener {
            onItemClicked(brand)
            val previousPosition = selectedPosition
            selectedPosition = holder.adapterPosition
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
        }
    }

    override fun getItemCount(): Int = brands.size

    inner class HoneyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.brand_name)

        fun bind(brand: Brand, isSelected: Boolean) {
            textView.text = brand.Country
            itemView.isSelected = isSelected
            itemView.setBackgroundResource(
                if (isSelected) R.drawable.selected_background else R.drawable.default_background
            )
        }
    }
}
