package com.autumnsun.foreksinternship.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.autumnsun.foreksinternship.R
import com.autumnsun.foreksinternship.databinding.FragmentDetailBinding
import com.autumnsun.foreksinternship.db.FavoriteModel
import com.autumnsun.foreksinternship.db.FavoriteRepository
import com.autumnsun.foreksinternship.model.DetailModel
import com.autumnsun.foreksinternship.model.GraphModelItem
import com.autumnsun.foreksinternship.service.ApiClient
import com.autumnsun.foreksinternship.utils.MyValueFormatter
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.math.sign


class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var mLineChart: LineChart
    private var entries = java.util.ArrayList<Entry>()
    private val apiService by lazy { ApiClient.getApiService() }
    private var gettingCode: String? = null
    private val model: HomeFragmentViewModel by activityViewModels()
    private var isDetailGame: Boolean = true
    private lateinit var favoriteRepository: FavoriteRepository
    private lateinit var codingLifeCode: String
    private var graphJustOne: Boolean = true
    private var rowId: Int = -1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater)
        //return inflater.inflate(R.layout.fragment_detail, container, false)
        favoriteRepository = FavoriteRepository(binding.root.context)
        codingLifeCode = ""
        binding.itemName.text = gettingCode.toString()
        return binding.root
    }


    private fun setLineChartData(graphItem: List<GraphModelItem>) {
        for (i in graphItem.indices) {
            entries.add(Entry(graphItem[i].d.toFloat(), graphItem[i].c.toFloat()))
        }
        val set1 = LineDataSet(entries, codingLifeCode)
        //set1.fillAlpha = 110
        set1.lineWidth = 1.5f
        set1.setDrawCircles(false)
        mLineChart = binding.getTheGraph
        set1.setDrawFilled(true)
        set1.valueTextSize = 10F
        set1.fillColor = resources.getColor(R.color.graphItem)
        var dataSet = java.util.ArrayList<ILineDataSet>()
        dataSet.add(set1)
        val lineData = LineData(dataSet)
        mLineChart.data = lineData
        set1.color = resources.getColor(R.color.purple_200)
        set1.mode = LineDataSet.Mode.CUBIC_BEZIER
        mLineChart.legend.isEnabled = false
        mLineChart.invalidate()
        mLineChart.description.text = codingLifeCode
        mLineChart.axisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        mLineChart.axisRight.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        mLineChart.setBackgroundColor(resources.getColor(R.color.background2))
        mLineChart.animateXY(2000, 2000, Easing.EaseInCubic)
        val xAxis = mLineChart.xAxis
        val legend = mLineChart.legend
        legend.textSize = 20f
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.setLabelCount(4)
        val xValsDateLabel = ArrayList<String>()
        for (i in graphItem.indices) {
            val sdf = java.text.SimpleDateFormat("MM/dd/yyyy")
            val date = java.util.Date(graphItem[i].d)
            val stringDate: String = sdf.format(date)
            xValsDateLabel.add(stringDate)
        }
        xAxis.setValueFormatter(MyValueFormatter(graphItem, xValsDateLabel))
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.message.observe(
            viewLifecycleOwner,
            Observer { item ->
                binding.itemName.text = item.toString()
                getGraphDataFromApi(item as String)
                getDetailApi(item)
                codingLifeCode = item
            })


        binding.imageButton.setOnClickListener() {
            var isCorrectList = favoriteRepository.getAllFavorite()
            var ismetAdalariVerdiVerdi = false
            for (i in 0 until isCorrectList.size) {
                if (isCorrectList[i].favorite.equals(codingLifeCode)) {
                    ismetAdalariVerdiVerdi = true
                    break
                }
            }
            if (ismetAdalariVerdiVerdi) {
                Toast.makeText(context, "Bu öğe zaten eklidir", Toast.LENGTH_LONG).show()
            } else {
                rowId = favoriteRepository.insterFavorite(
                    FavoriteModel(favorite = codingLifeCode)
                )
            }

            if (rowId > -1) {
                Toast.makeText(context, "Eklendi", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Ekleme başarısız", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onStart() {
        isDetailGame = true
        model.message.observe(viewLifecycleOwner, Observer { started ->
            GlobalScope.launch {
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
                            if (graphJustOne) {
                                graphJustOne = false
                                setLineChartData(graphItem)
                            }
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
                Toast.makeText(context, "Serviste bir hata oluştu", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<DetailModel>, response: Response<DetailModel>) {
                if (response.isSuccessful && response.body() !== null) {
                    //Log.d("gelenveri", response.body()?.d?.size.toString())
                    //Log.d("gelenveri", item)
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