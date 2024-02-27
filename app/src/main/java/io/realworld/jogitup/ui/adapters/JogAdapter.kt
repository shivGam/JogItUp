package io.realworld.jogitup.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.realworld.jogitup.R
import io.realworld.jogitup.database.RunStats
import io.realworld.jogitup.extra.Utility
import kotlinx.android.synthetic.main.item_run.view.*
import java.text.SimpleDateFormat
import java.util.*

class JogAdapter : RecyclerView.Adapter<JogAdapter.JogViewHolder>() {

    inner class JogViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    val diffCallback = object : DiffUtil.ItemCallback<RunStats>(){
        override fun areItemsTheSame(oldItem: RunStats, newItem: RunStats): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RunStats, newItem: RunStats): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    val differ = AsyncListDiffer(this,diffCallback)
    fun submitList(list : List<RunStats>) =  differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JogViewHolder {
        return JogViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_run,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: JogViewHolder, position: Int) {
        val run = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(run.map).into(ivRunImage)
            val calendar = Calendar.getInstance().apply {
                timeInMillis = run.timeStamp
            }
            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            tvDate.text = dateFormat.format(calendar.time)
            val avgSpeed = "${run.avgSpeed}Km/h"
            tvAvgSpeed.text = avgSpeed
            val distance = "${run.distance/1000f}km"
            tvDistance.text = distance

            tvTime.text = Utility.formatTime(run.time)

            val caloriesBurned = "${run.calories}Kcal"
            tvCalories.text = caloriesBurned

        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}