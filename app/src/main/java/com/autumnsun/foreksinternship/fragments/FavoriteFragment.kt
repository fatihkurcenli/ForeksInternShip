package com.autumnsun.foreksinternship.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.autumnsun.foreksinternship.adapter.FavoriteAdapter
import com.autumnsun.foreksinternship.databinding.FragmentFavoriteBinding
import com.autumnsun.foreksinternship.db.FavoriteModel
import com.autumnsun.foreksinternship.db.FavoriteRepository
import com.autumnsun.foreksinternship.model.MoneyData
import com.autumnsun.foreksinternship.model.MoneyVariable
import com.autumnsun.foreksinternship.service.ApiClient
import com.autumnsun.foreksinternship.utils.gone
import com.autumnsun.foreksinternship.utils.visible
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FavoriteFragment : Fragment() {
    private lateinit var perStateCode: Map<String, List<MoneyVariable>>
    private val apiService by lazy { ApiClient.getApiService() }
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteRepository: FavoriteRepository
    private lateinit var favoriteList: ArrayList<FavoriteModel>
    private lateinit var adapter: FavoriteAdapter
    private lateinit var selectedData: List<MoneyVariable>
    private var selectedNumberFavorite: Int = 0
    private var isOnFavorite: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        //inflater.inflate(R.layout.fragment_favorite, container, false)
        favoriteRepository = FavoriteRepository(binding.root.context)
        favoriteList = favoriteRepository.getAllFavorite()
        if (favoriteList.isNotEmpty()) {
            binding.noFavoriteList.gone()
        }
        binding.recyclerViewFavorite.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewFavorite.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewFavorite.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        selectedData = mutableListOf()
        //binding.progressBarFavorite.visible()
        adapter = FavoriteAdapter(mutableListOf(), selectedNumberFavorite) {
        }
        /* { textView ->
                onUserClickListener(
                    textView
                )*/

        getFavoriteData(selectedNumberFavorite)
        binding.recyclerViewFavorite.adapter = adapter
        return binding.root
    }


    private fun getFavoriteData(selectedNumberFavorite: Int) {
        apiService.getData().enqueue(object : Callback<MoneyData> {
            override fun onFailure(call: Call<MoneyData>, t: Throwable) {
                Log.e("mainactivity", t.message.toString())
            }

            override fun onResponse(call: Call<MoneyData>, response: Response<MoneyData>) {
                Log.d("favoriteResponse", response.body()?.moneyVariableList?.size.toString())
                if (response.isSuccessful && response.body() !== null) {
                    response.body()?.let { body ->
                        adapter.favoriteData = body.moneyVariableList
                        adapter.selectedNumberFromMenu = selectedNumberFavorite
                        perStateCode = body.moneyVariableList.groupBy {
                            it.codKod
                        }
                        var list = ArrayList<MoneyVariable>()
                        for (i in 0 until favoriteList.size) {
                            if (perStateCode.keys.contains(favoriteList[i].favorite)) {
                                perStateCode[favoriteList[i].favorite]?.get(0)?.let { list.add(it) }
                                Log.d("cikti", list.toString())
                                /*perStateCode[favoriteList[i].favorite]?.get(i).let {
                                    it?.let { it1 -> listeninde.add(it1) }

                                }*/
                            }
                        }
                        adapter.favoriteData = list
                        adapter.notifyDataSetChanged()
                        //adapter.onUserClickListener = this::onUserClickListener
                        adapter.notifyDataSetChanged()
                        binding.recyclerViewFavorite.visible()
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Something went Wrong. Try again !! ",
                        Toast.LENGTH_LONG
                    )
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        isOnFavorite = true
        GlobalScope.launch {
            while (isOnFavorite) {
                getFavoriteData(selectedNumberFavorite)
                delay(2000L)
            }
        }
    }

    override fun onStop() {
        isOnFavorite = false
        super.onStop()

    }

    override fun onResume() {
        super.onResume()
        binding.spinnerFavorite.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Toast.makeText(context, "Seçim edilmedi", Toast.LENGTH_SHORT).show()
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> selectedNumberFavorite = 0
                        1 -> selectedNumberFavorite = 1
                        2 -> selectedNumberFavorite = 2
                        3 -> selectedNumberFavorite = 3
                        4 -> selectedNumberFavorite = 4
                    }
                }

            }
    }


}