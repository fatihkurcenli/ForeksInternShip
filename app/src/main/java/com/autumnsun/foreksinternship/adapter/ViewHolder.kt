package com.autumnsun.foreksinternship.adapter

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.autumnsun.foreksinternship.R
import com.autumnsun.foreksinternship.databinding.DataItemBinding
import com.autumnsun.foreksinternship.model.MoneyVariable

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private lateinit var binding: DataItemBinding
    fun bindItem(item: MoneyVariable) {

    }

    // var dede=itemView.find
    val cod = itemView.findViewById<TextView>(R.id.item_cod)
    val def = itemView.findViewById<TextView>(R.id.item_def)
    val pdd = itemView.findViewById<TextView>(R.id.item_pdd)
    val las = itemView.findViewById<TextView>(R.id.item_las)
}


