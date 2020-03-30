package com.example.weatherapp.home.mvvm.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ItemForecastBinding
import com.example.weatherapp.databinding.ItemForecastTodayBinding
import com.example.weatherapp.home.data.models.WeatherLocalData

class WeatherAdapter(private val weatherList: List<WeatherLocalData>, private val listener: WeatherItemClickListener)
    : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return when(position){ 0 -> WeatherViewHolder.VIEW_TYPE_TODAY else -> WeatherViewHolder.VIEW_TYPE_FUTURE_DAY}
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return when(viewType){
            WeatherViewHolder.VIEW_TYPE_TODAY -> WeatherViewHolder.createItemToadayFrom(parent)
            else -> WeatherViewHolder.createItemFrom(parent)
        }
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val item = weatherList[position]
        holder.bind(item, listener)
    }



    class WeatherViewHolder : RecyclerView.ViewHolder{
        var binding: ItemForecastBinding? = null
        var bindingToday: ItemForecastTodayBinding? = null
        constructor(binding: ItemForecastBinding) : super(binding.root) {
            this.binding = binding
        }

        constructor(binding: ItemForecastTodayBinding): super(binding.root){
            this.bindingToday = binding
        }

        fun bind(weather: WeatherLocalData, listener: WeatherItemClickListener){
            binding?.let { it.weather = weather
                it.clickListener = listener
                it.executePendingBindings()
            }
            bindingToday?.let { it.weather = weather
                it.clickListener = listener
                it.executePendingBindings()
            }
        }

        companion object {

            val VIEW_TYPE_TODAY = 0
            val VIEW_TYPE_FUTURE_DAY = 1

            fun createItemFrom(parent: ViewGroup): WeatherViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                return WeatherViewHolder(ItemForecastBinding.inflate(layoutInflater, parent, false))
            }

            fun createItemToadayFrom(parent: ViewGroup): WeatherViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                return WeatherViewHolder(ItemForecastTodayBinding.inflate(layoutInflater, parent, false))
            }
        }
    }

    public class WeatherItemClickListener(val clickListener: (weather: WeatherLocalData) -> Unit){
        fun onItemClick(weatherItem: WeatherLocalData) = clickListener(weatherItem)
    }
}