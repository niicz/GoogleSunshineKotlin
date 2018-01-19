package com.niicz.sunshinekotlin.forecast

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.preference.PreferenceManager
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.niicz.sunshinekotlin.R
import com.niicz.sunshinekotlin.detail.DetailActivity


class ForecastFragment : Fragment(), ForecastContract.View {

    override lateinit var presenter: ForecastContract.Presenter
    private var forecastAdapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.forecastfragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        return if (id == R.id.action_refresh) {
            refreshWeather()
            return true
        } else super.onOptionsItemSelected(item)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater!!.inflate(R.layout.fragment_forecast, container, false)

        forecastAdapter = ArrayAdapter(
            activity,
            R.layout.list_item_forecast,
            R.id.list_item_forecast_textview,
            mutableListOf()
        )

        val listView = rootView.findViewById(R.id.listview_forecast) as ListView
        listView.adapter = forecastAdapter

        listView.onItemClickListener =
                AdapterView.OnItemClickListener { adapterView, view, position, l ->
                    val forecast = forecastAdapter!!.getItem(position)
                    val intent = Intent(activity, DetailActivity::class.java)
                        .putExtra(Intent.EXTRA_TEXT, forecast)
                    startActivity(intent)
                }

        return rootView
    }

    override fun refreshWeather() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(activity)
        val location = prefs.getString(
            getString(R.string.pref_location_key),
            getString(R.string.pref_location_default)
        )
        presenter.fetchWeather(location)
    }

    override fun addToAdapter(dayForecastStrs: MutableList<String>) {
        forecastAdapter!!.clear()
        forecastAdapter!!.addAll(dayForecastStrs)
    }

    override fun onStart() {
        super.onStart()
        refreshWeather()
    }

    companion object {

        fun newInstance(): ForecastFragment {
            return ForecastFragment()
        }
    }

}
