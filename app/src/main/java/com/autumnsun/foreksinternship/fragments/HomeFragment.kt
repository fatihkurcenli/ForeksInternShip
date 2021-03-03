package com.autumnsun.foreksinternship.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.autumnsun.foreksinternship.R
import com.autumnsun.foreksinternship.adapter.DataAdapter
import com.autumnsun.foreksinternship.databinding.FragmentHomeBinding
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
import java.nio.channels.Selector
import java.util.ArrayList


class HomeFragment(context: Context) : Fragment() {
    private lateinit var perStateCode: Map<String, List<MoneyVariable>>
    private val apiService by lazy { ApiClient.getApiService() }
    private lateinit var binding: FragmentHomeBinding
    private val model: HomeFragmentViewModel by activityViewModels()
    private lateinit var adapter: DataAdapter
    private var selectedNumberFromMenu: Int = 0
    private var isOnGame: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        //inflater.inflate(R.layout.fragment_home, container, false)
        binding.recyclerviewHome.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewHome.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.progressBar.visible()
        getMoneyData(selectedNumberFromMenu)
        adapter = DataAdapter(mutableListOf(), 0) { textView ->
            onUserClickListener(
                textView
            )
        }

        binding.recyclerviewHome.adapter = adapter
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(context, "No  changed", Toast.LENGTH_LONG).show()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                when (position) {
                    0 -> selectedNumberFromMenu = 0
                    1 -> selectedNumberFromMenu = 1
                    2 -> selectedNumberFromMenu = 2
                    3 -> selectedNumberFromMenu = 3
                    4 -> selectedNumberFromMenu = 4
                }
            }

        }
    }

    override fun onStart() {
        super.onStart()
        isOnGame = true
        GlobalScope.launch {
            while (isOnGame) {
                getMoneyData(selectedNumberFromMenu)
                delay(2000L)
            }
        }
    }

    override fun onStop() {
        isOnGame = false
        super.onStop()
    }

    private fun getMoneyData(selectedNumberFromMenu: Int) {
        apiService.getData().enqueue(object : Callback<MoneyData> {
            override fun onFailure(call: Call<MoneyData>, t: Throwable) {
                Log.e("mainactivity", t.message.toString())
            }

            override fun onResponse(call: Call<MoneyData>, response: Response<MoneyData>) {
                Log.d("MainActivity", response.body()?.moneyVariableList?.size.toString())
                if (response.isSuccessful && response.body() !== null) {
                    response.body()?.let { body ->
                        adapter.moneyData = body.moneyVariableList
                        adapter.selectedNumberFromMenu = selectedNumberFromMenu
                        perStateCode = body.moneyVariableList.groupBy {
                            it.codKod
                        }
                        Log.d("MainActivity", perStateCode.toString())
                        if (perStateCode.containsKey("AAVEUSD")) {
                            Log.d("MainActivity", perStateCode.get("AAVEUSD").toString())
                        }
                        //adapter.onUserClickListener = this::onUserClickListener
                        adapter.notifyDataSetChanged()
                        binding.progressBar.gone()
                        binding.recyclerviewHome.visible()
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


    private fun onUserClickListener(sharedStringCode: String) {
        Log.d("recycler_verisi", sharedStringCode)
        model.setMsgCommunicator(sharedStringCode)
        val detailFragment = DetailFragment(this)
        fragmentManager?.beginTransaction()
            ?.replace(
                R.id.fl_main, detailFragment
            )
            ?.addToBackStack(null)
            ?.commit()


        /* val intent = Intent(this, DetailActivity::class.java)
         intent.putExtra(EXTRA_RESULT_ITEM, moneyData)
         val option = ActivityOptionsCompat.makeSceneTransitionAnimation(
             this,
             sharedTextView,
             ViewCompat.getTransitionName(sharedTextView)!!
         )
         intent.putExtra(EXTRA_RESULT_TRANSITION_NAME, ViewCompat.getTransitionName(sharedTextView))
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
             startActivity(intent, option.toBundle())
         } else {
             startActivity(intent)
         }*/
    }

}