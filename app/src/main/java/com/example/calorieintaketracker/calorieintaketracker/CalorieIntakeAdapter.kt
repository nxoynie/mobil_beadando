package com.example.calorieintaketracker.calorieintaketracker


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.calorieintaketracker.R
import com.example.calorieintaketracker.database.CalorieIntake
import com.example.calorieintaketracker.databinding.ListItemCalorieIntakeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1


class CalorieIntakeAdapter(private val onClickListener: OnCalorieIntakeListener):
    ListAdapter<DataItem, RecyclerView.ViewHolder>(CalorieIntakeDiffCallback()) {
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val calorieIntakeItem = getItem(position) as DataItem.CalorieIntakeItem
                holder.bind(calorieIntakeItem.calorieIntake)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    fun addHeaderSubmitList(list: List<CalorieIntake>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map {
                    DataItem.CalorieIntakeItem(it) }
                }
                withContext(Dispatchers.Main)
                {
                    submitList(items)
                }
            }
        }
        override fun getItemViewType(position: Int): Int {
            return when (getItem(position)) {
                is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
                is DataItem.CalorieIntakeItem -> ITEM_VIEW_TYPE_ITEM
            }
        }



    class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            companion object {
                fun from(parent: ViewGroup): TextViewHolder {
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val view = layoutInflater.inflate(R.layout.header, parent, false)
                    return TextViewHolder(view)
                }
            }
        }

        class ViewHolder private constructor(val binding: ListItemCalorieIntakeBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(item: CalorieIntake) {
                binding.calorieIntake = item
                binding.executePendingBindings()
            }

            companion object {
                fun from(parent: ViewGroup): ViewHolder {
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val binding =
                        ListItemCalorieIntakeBinding.inflate(layoutInflater, parent, false)
                    return ViewHolder(binding)
                }
            }
        }
}


    class CalorieIntakeDiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }

    sealed class DataItem {
        data class CalorieIntakeItem(val calorieIntake: CalorieIntake) : DataItem() {
            override val id = calorieIntake.foodID
        }

        object Header : DataItem() {
            override val id = Long.MIN_VALUE
        }

        abstract val id: Long
}