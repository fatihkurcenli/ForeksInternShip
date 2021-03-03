package com.autumnsun.foreksinternship.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.autumnsun.foreksinternship.R
import com.autumnsun.foreksinternship.model.MoneyVariable
import androidx.core.view.ViewCompat
import com.autumnsun.foreksinternship.db.FavoriteModel
import kotlin.collections.ArrayList


class FavoriteAdapter(
    var favoriteData: List<MoneyVariable>,
    var selectedNumberFromMenu: Int,
    var onUserClickListener: (String) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {


    private lateinit var onFavoriteListener: OnFavoriteListener
    private val searchList = ArrayList<MoneyVariable>(favoriteData)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.data_item, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = favoriteData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         holder.bindItem(favoriteData[position])
         //holder.checkedBox.isChecked = false


         if (selectedNumberFromMenu.equals(0)) {
             holder.cod.text = favoriteData[position].codKod
             holder.def.text = favoriteData[position].defAciklama
             holder.pdd.text = "Saat = ${favoriteData[position].clockClo}"
             holder.las.text = "Son satış değeri = ${favoriteData[position].lasSon}"
         }
         if (selectedNumberFromMenu.equals(1)) {
             holder.cod.text = favoriteData[position].codKod
             holder.def.text = favoriteData[position].defAciklama
             holder.pdd.text = "Saat = ${favoriteData[position].clockClo}"
             holder.las.text = "% değer ${favoriteData[position].pddYuzdeFark}"
         }
         if (selectedNumberFromMenu.equals(2)) {
             holder.cod.text = favoriteData[position].codKod
             holder.def.text = favoriteData[position].defAciklama
             holder.pdd.text = "Saat = ${favoriteData[position].clockClo}"
             holder.las.text = "fark = ${favoriteData[position].farkDdi}"
         }

         if (selectedNumberFromMenu.equals(3)) {
             holder.cod.text = favoriteData[position].codKod
             holder.def.text = favoriteData[position].defAciklama
             holder.pdd.text = "Saat = ${favoriteData[position].clockClo}"
             holder.las.text = favoriteData[position].lowGDusuk
         }

         if (selectedNumberFromMenu.equals(4)) {
             holder.cod.text = favoriteData[position].codKod
             holder.def.text = favoriteData[position].defAciklama
             holder.pdd.text = "Saat = ${favoriteData[position].clockClo}"
             holder.las.text = favoriteData[position].highYuksek
         }


         ViewCompat.setTransitionName(
             holder.cod,
             "${favoriteData[position].codKod} ${favoriteData[position].defAciklama}"
         )

         holder.itemView.setOnClickListener {
             onUserClickListener(favoriteData[position].codKod)
         }


         /*holder.itemView.setOnClickListener {
             onFavoriteListener(moneyData[position])
         }*/


    }

    fun setOnFavoriteCompleteListener(onFavoriteListener: OnFavoriteListener) {
        this.onFavoriteListener = onFavoriteListener
    }

    interface OnFavoriteListener {
        fun onFavoriteListener(favorite: String)
    }

    /* override fun getFilter(): Filter {
         return object : Filter() {
             override fun performFiltering(constraint: CharSequence): FilterResults {
                 var filteredList = ArrayList<MoneyVariable>()

                 if (constraint.isBlank() or constraint.isEmpty()) {
                     filteredList.addAll(searchList)
                 } else {
                     val filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim()

                     searchList.forEach {
                         if (it.codKod.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                             filteredList.add(it)
                         }
                     }
                 }
                 val result = FilterResults()
                 result.values = filteredList
                 return result
             }

             override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
             }

         }
     }*/
}




