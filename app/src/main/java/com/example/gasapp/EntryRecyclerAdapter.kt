package com.example.gasapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EntryRecyclerAdapter(private val context: Context?, private var entries: ArrayList<Entry>): RecyclerView.Adapter<EntryRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.entry_card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.entryTypeInput.text = entries[position].type
        holder.entryDateInput.text = entries[position].date
        holder.entryPriceInput.text = entries[position].price.toString()
        holder.entryAmountInput.text = entries[position].amount.toString()
        if (entries[position].type != "Gas") {
            holder.entryAmountInput.visibility = View.INVISIBLE
            holder.entryAmountText.visibility = View.INVISIBLE
        } else {
            holder.entryAmountInput.visibility = View.VISIBLE
            holder.entryAmountText.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var entryTypeInput: TextView
        var entryDateInput: TextView
        var entryPriceInput: TextView
        var entryAmountInput: TextView
        var entryAmountText: TextView

        init {
            entryTypeInput = itemView.findViewById(R.id.entryTypeInput)
            entryDateInput = itemView.findViewById(R.id.entryDateInput)
            entryPriceInput = itemView.findViewById(R.id.entryPriceInput)
            entryAmountInput = itemView.findViewById(R.id.entryAmountInput)
            entryAmountText = itemView.findViewById(R.id.entryAmountText)
        }
    }
}