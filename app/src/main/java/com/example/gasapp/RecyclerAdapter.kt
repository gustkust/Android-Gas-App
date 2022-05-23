package com.example.gasapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val context: Context?, private var users: ArrayList<UserBasicInfo>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.userNameText.text = users[position].name
        holder.lastEntryValue.text = users[position].last_entry_date
        holder.userAverageGasPriceValue.text = users[position].average_fuel_consumption.toString()
        // holder.itemImage.setImageResource(context.resources.getIdentifier(images[position], "drawable", context.packageName))
    }

    override fun getItemCount(): Int {
        return users.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var userPicture: ImageView
        var userNameText: TextView
        var lastEntryValue: TextView
        var userAverageGasPriceValue: TextView

        init {
            userPicture = itemView.findViewById(R.id.userPicture)
            userNameText = itemView.findViewById(R.id.userNameText)
            lastEntryValue = itemView.findViewById(R.id.lastEntryValue)
            userAverageGasPriceValue = itemView.findViewById(R.id.userAverageGasPriceValue)
        }
    }
}