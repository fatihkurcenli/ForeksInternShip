package com.autumnsun.foreksinternship.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.autumnsun.foreksinternship.R
import com.autumnsun.foreksinternship.databinding.FragmentDetailBinding
import com.autumnsun.foreksinternship.model.DetailModel
import com.autumnsun.foreksinternship.model.GraphModelItem
import com.autumnsun.foreksinternship.service.ApiClient
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailFragment(context: HomeFragment) : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val apiService by lazy { ApiClient.getApiService() }
    private var gettingCode: String? = null
    private val model: HomeFragmentViewModel by activityViewModels()
    private var isDetailGame: Boolean = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater)
        //return inflater.inflate(R.layout.fragment_detail, container, false)

        binding.itemName.text = gettingCode.toString()
        return binding.root
    }


    private fun setLineChartData(graphItem: List<GraphModelItem>) {
        val linevalues = ArrayList<Entry>()
        for (i in graphItem.indices) {
            linevalues.add(Entry(graphItem[i].d.toFloat(), graphItem[i].c.toFloat()))
        }

        val linedataset = LineDataSet(linevalues, "deneme")
        //We add features to our chart
        linedataset.color = resources.getColor(R.color.purple_200)

        //  linedataset.circleRadius = 10f
        linedataset.setDrawFilled(true)
        linedataset.valueTextSize = 10F
        linedataset.fillColor = resources.getColor(R.color.green)
        linedataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        //We connect our data to the UI Screen
        val data = LineData(linedataset)
        binding.getTheGraph.data = data
        binding.getTheGraph.setBackgroundColor(resources.getColor(R.color.white))
        binding.getTheGraph.animateXY(2000, 2000, Easing.EaseInCubic)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.message.observe(
            viewLifecycleOwner,
            Observer { item ->
                binding.itemName.text = item.toString()
                getGraphDataFromApi(item as String)
                getDetailApi(item as String)

            })
    }

    override fun onStart() {
        isDetailGame = true
        model.message.observe(viewLifecycleOwner, Observer {
            started-> GlobalScope.launch {
            while (isDetailGame) {
                getDetailApi(started as String)
                delay(2000L)
            }
        }

        })

        super.onStart()
    }

    override fun onStop() {
        isDetailGame = false
        super.onStop()
    }

    private fun getGraphDataFromApi(item: String) {
        apiService.graphData(item)
            .enqueue(object : Callback<List<GraphModelItem>> {
                override fun onFailure(call: Call<List<GraphModelItem>>, t: Throwable) {
                    Log.d("DetailActivity", t.toString())
                }

                override fun onResponse(
                    call: Call<List<GraphModelItem>>,
                    response: Response<List<GraphModelItem>>
                ) {
                    Log.d("DetailActivity", response.body()?.size.toString())
                    Log.d("DetailActivity", model.message.toString())
                    if (response.isSuccessful && response.body() !== null) {
                        response.body()?.let { body ->
                            var graphItem = body
                            setLineChartData(graphItem)
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


    private fun getDetailApi(item: String) {
        apiService.getDetail(item).enqueue(object : Callback<DetailModel> {
            override fun onFailure(call: Call<DetailModel>, t: Throwable) {
                Log.d("detailApi", "detail api error ${t.toString()}")
            }

            override fun onResponse(call: Call<DetailModel>, response: Response<DetailModel>) {
                if (response.isSuccessful && response.body() !== null) {
                    Log.d("gelenveri", response.body()?.d?.size.toString())
                    Log.d("gelenveri", item)
                    response.body()?.let { body ->
                        var detailBody = body
                        binding.lasSonDetailValue.text = detailBody.d[0].fields.las
                        binding.buyAlDetailValue.text = detailBody.d[0].fields.buy
                        binding.lowDDetailValue.text = detailBody.d[0].fields.low
                        binding.sellSatDetailValue.text = detailBody.d[0].fields.sel
                        binding.highYDetailValue.text = detailBody.d[0].fields.hig
                        binding.ddiFarkDetailValue.text = detailBody.d[0].fields.ddi
                        binding.ppdYFarkDetailValue.text = "% ${detailBody.d[0].fields.pdd}"
                        binding.detailClock.text = detailBody.d[0].clo
                        binding.canliVeri.text = detailBody.d[0].desc
                        binding.itemNameDetail.text = detailBody.def
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

}